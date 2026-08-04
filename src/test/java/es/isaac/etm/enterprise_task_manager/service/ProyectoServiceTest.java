package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoAlreadyAssignedException;
import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.exception.ProyectoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.repository.ProyectoRepository;
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
class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private EmpleadoService empleadoService;

    @InjectMocks
    private ProyectoService proyectoService;

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

        when(proyectoRepository.save(proyecto)).thenReturn(proyecto);

        Proyecto resultado = proyectoService.save(proyecto);

        assertEquals(proyecto, resultado);
        verify(proyectoRepository).save(proyecto);
    }

    @Test
    void shouldReturnAllProyectos() {

        Page<Proyecto> page =
                new PageImpl<>(Collections.singletonList(proyecto));

        when(proyectoRepository.findAll(pageable)).thenReturn(page);

        Page<Proyecto> resultado = proyectoService.findAll(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(proyecto, resultado.getContent().get(0));

        verify(proyectoRepository).findAll(pageable);
    }

    @Test
    void shouldReturnProyectoWhenExists() {

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        Proyecto resultado = proyectoService.findById(1);

        assertEquals(proyecto, resultado);

        verify(proyectoRepository).findById(1);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenProyectoDoesNotExist() {

        when(proyectoRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoService.findById(99)
        );

        verify(proyectoRepository).findById(99);
    }

    @Test
    void shouldReturnProyectoPageFilteredByNombre() {

        Page<Proyecto> page =
                new PageImpl<>(Collections.singletonList(proyecto));

        when(proyectoRepository.findByNombreContaining(
                "Enterprise",
                pageable))
                .thenReturn(page);

        Page<Proyecto> resultado =
                proyectoService.findByNombre("Enterprise", pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(proyecto, resultado.getContent().get(0));

        verify(proyectoRepository)
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

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(proyectoRepository.save(any(Proyecto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Proyecto resultado = proyectoService.update(1, actualizado);

        assertEquals(1, resultado.getId());
        assertEquals("Nuevo nombre", resultado.getNombre());

        verify(proyectoRepository).findById(1);
        verify(proyectoRepository).save(actualizado);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenUpdatingNonExistingProyecto() {

        when(proyectoRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoService.update(99, proyecto)
        );

        verify(proyectoRepository).findById(99);
        verify(proyectoRepository, never()).save(any());
    }

    @Test
    void shouldDeleteProyectoWhenExists() {

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        proyectoService.delete(1);

        verify(proyectoRepository).findById(1);
        verify(proyectoRepository).delete(proyecto);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenDeletingNonExistingProyecto() {

        when(proyectoRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoService.delete(99)
        );

        verify(proyectoRepository).findById(99);
        verify(proyectoRepository, never()).delete(any());
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

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoService.findById(2))
                .thenReturn(nuevoEmpleado);

        proyectoService.addEmpleado(1, 2);

        assertTrue(proyecto.getEmpleados().contains(nuevoEmpleado));

        verify(proyectoRepository).findById(1);
        verify(empleadoService).findById(2);
    }

    @Test
    void shouldThrowEmpleadoAlreadyAssignedExceptionWhenEmpleadoAlreadyBelongsToProyecto() {

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoService.findById(1))
                .thenReturn(empleado);

        assertThrows(
                EmpleadoAlreadyAssignedException.class,
                () -> proyectoService.addEmpleado(1, 1)
        );

        verify(proyectoRepository).findById(1);
        verify(empleadoService).findById(1);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenAddingEmpleadoToNonExistingProyecto() {

        when(proyectoRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoService.addEmpleado(99, 1)
        );

        verify(proyectoRepository).findById(99);
        verifyNoInteractions(empleadoService);
    }

    @Test
    void shouldThrowEmpleadoNotFoundExceptionWhenAddingNonExistingEmpleado() {

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoService.findById(99))
                .thenThrow(new EmpleadoNotFoundException("No encontrado"));

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> proyectoService.addEmpleado(1, 99)
        );

        verify(proyectoRepository).findById(1);
        verify(empleadoService).findById(99);
    }

    @Test
    void shouldDeleteEmpleadoFromProyecto() {

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoService.findById(1))
                .thenReturn(empleado);

        proyectoService.deleteEmpleado(1, 1);

        assertFalse(proyecto.getEmpleados().contains(empleado));

        verify(proyectoRepository).findById(1);
        verify(empleadoService).findById(1);
    }

    @Test
    void shouldThrowProyectoNotFoundExceptionWhenDeletingEmpleadoFromNonExistingProyecto() {

        when(proyectoRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ProyectoNotFoundException.class,
                () -> proyectoService.deleteEmpleado(99, 1)
        );

        verify(proyectoRepository).findById(99);
        verifyNoInteractions(empleadoService);
    }

    @Test
    void shouldThrowEmpleadoNotFoundExceptionWhenDeletingNonExistingEmpleado() {

        when(proyectoRepository.findById(1))
                .thenReturn(Optional.of(proyecto));

        when(empleadoService.findById(99))
                .thenThrow(new EmpleadoNotFoundException("No encontrado"));

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> proyectoService.deleteEmpleado(1, 99)
        );

        verify(proyectoRepository).findById(1);
        verify(empleadoService).findById(99);
    }

}