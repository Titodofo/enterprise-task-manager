package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoAlreadyAssignedException;
import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.exception.ProyectoNotFoundException;
import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.infrastructure.persistence.repository.ProyectoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoUseCaseTest {

    @Mock
    private ProyectoRepositoryPort proyectoRepositoryPort;

    @Mock
    private EmpleadoUseCase empleadoUseCase;

    @InjectMocks
    private ProyectoUseCase proyectoUseCase;

    private Proyecto proyecto;
    private Empleado empleado;
    private Pageable pageable;

    @BeforeEach
    void setUp() {

        empleado = new Empleado(
                1,
                "Isaac",
                "Developer",
                new ArrayList<>()
        );

        proyecto = new Proyecto(
                1,
                "Enterprise Task Manager",
                "Proyecto de pruebas",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new ArrayList<>(Collections.singletonList(empleado))
        );

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void shouldSaveProyecto() {

        when(proyectoRepositoryPort.save(proyecto)).thenReturn(proyecto);

        Proyecto resultado = proyectoUseCase.save(proyecto);

        assertEquals(proyecto, resultado);
        verify(proyectoRepositoryPort).save(proyecto);
    }

    @Test
    void shouldReturnAllProyectos() {

        Page<Proyecto> page =
                new PageImpl<>(Collections.singletonList(proyecto));

        when(proyectoRepositoryPort.findAll(pageable)).thenReturn(page);

        Page<Proyecto> resultado = proyectoUseCase.findAll(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(proyecto, resultado.getContent().get(0));

        verify(proyectoRepositoryPort).findAll(pageable);
    }

    @Test
    void shouldReturnProyectoWhenExists() {

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        Proyecto resultado = proyectoUseCase.findById(1);

        assertEquals(proyecto, resultado);

        verify(proyectoRepositoryPort).findById(1);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenProyectoDoesNotExist() {

        when(proyectoRepositoryPort.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoUseCase.findById(99)
        );

        verify(proyectoRepositoryPort).findById(99);
    }

    @Test
    void shouldReturnProyectoPageFilteredByNombre() {

        Page<Proyecto> page =
                new PageImpl<>(Collections.singletonList(proyecto));

        when(proyectoRepositoryPort.findByNombreContaining(
                "Enterprise",
                pageable))
                .thenReturn(page);

        Page<Proyecto> resultado =
                proyectoUseCase.findByNombre("Enterprise", pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(proyecto, resultado.getContent().get(0));

        verify(proyectoRepositoryPort)
                .findByNombreContaining("Enterprise", pageable);
    }

    @Test
    void shouldUpdateProyectoWhenExists() {

        Proyecto actualizado = new Proyecto(
                null,
                "Nuevo nombre",
                "Nueva descripción",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new ArrayList<>()
        );

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(proyectoRepositoryPort.save(any(Proyecto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Proyecto resultado = proyectoUseCase.update(1, actualizado);

        assertEquals(1, resultado.getId());
        assertEquals("Nuevo nombre", resultado.getNombre());

        verify(proyectoRepositoryPort).findById(1);
        verify(proyectoRepositoryPort).save(actualizado);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenUpdatingNonExistingProyecto() {

        when(proyectoRepositoryPort.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoUseCase.update(99, proyecto)
        );

        verify(proyectoRepositoryPort).findById(99);
        verify(proyectoRepositoryPort, never()).save(any());
    }

    @Test
    void shouldDeleteProyectoWhenExists() {

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        proyectoUseCase.delete(1);

        verify(proyectoRepositoryPort).findById(1);
        verify(proyectoRepositoryPort).delete(proyecto);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenDeletingNonExistingProyecto() {

        when(proyectoRepositoryPort.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoUseCase.delete(99)
        );

        verify(proyectoRepositoryPort).findById(99);
        verify(proyectoRepositoryPort, never()).delete(any());
    }

    @Test
    void shouldAddEmpleadoToProyecto() {

        Empleado nuevoEmpleado = new Empleado(
                2,
                "Laura",
                "QA",
                new ArrayList<>()
        );

        proyecto.getEmpleados().clear();

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoUseCase.findById(2))
                .thenReturn(nuevoEmpleado);

        proyectoUseCase.addEmpleado(1, 2);

        assertTrue(proyecto.getEmpleados().contains(nuevoEmpleado));

        verify(proyectoRepositoryPort).findById(1);
        verify(empleadoUseCase).findById(2);
    }

    @Test
    void shouldThrowEmpleadoAlreadyAssignedExceptionWhenEmpleadoAlreadyBelongsToProyecto() {

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoUseCase.findById(1))
                .thenReturn(empleado);

        assertThrows(
                EmpleadoAlreadyAssignedException.class,
                () -> proyectoUseCase.addEmpleado(1, 1)
        );

        verify(proyectoRepositoryPort).findById(1);
        verify(empleadoUseCase).findById(1);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenAddingEmpleadoToNonExistingProyecto() {

        when(proyectoRepositoryPort.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoUseCase.addEmpleado(99, 1)
        );

        verify(proyectoRepositoryPort).findById(99);
        verifyNoInteractions(empleadoUseCase);
    }

    @Test
    void shouldThrowEmpleadoNotFoundExceptionWhenAddingNonExistingEmpleado() {

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoUseCase.findById(99))
                .thenThrow(new EmpleadoNotFoundException("No encontrado"));

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> proyectoUseCase.addEmpleado(1, 99)
        );

        verify(proyectoRepositoryPort).findById(1);
        verify(empleadoUseCase).findById(99);
    }

    @Test
    void shouldDeleteEmpleadoFromProyecto() {

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoUseCase.findById(1))
                .thenReturn(empleado);

        proyectoUseCase.deleteEmpleado(1, 1);

        assertFalse(proyecto.getEmpleados().contains(empleado));

        verify(proyectoRepositoryPort).findById(1);
        verify(empleadoUseCase).findById(1);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenDeletingEmpleadoFromNonExistingProyecto() {

        when(proyectoRepositoryPort.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoUseCase.deleteEmpleado(99, 1)
        );

        verify(proyectoRepositoryPort).findById(99);
        verifyNoInteractions(empleadoUseCase);
    }

    @Test
    void shouldThrowEmpleadoNotFoundExceptionWhenDeletingNonExistingEmpleado() {

        when(proyectoRepositoryPort.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoUseCase.findById(99))
                .thenThrow(new EmpleadoNotFoundException("No encontrado"));

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> proyectoUseCase.deleteEmpleado(1, 99)
        );

        verify(proyectoRepositoryPort).findById(1);
        verify(empleadoUseCase).findById(99);
    }

}