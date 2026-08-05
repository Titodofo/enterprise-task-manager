package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import es.isaac.etm.enterprise_task_manager.infrastructure.persistence.repository.EmpleadoRepositoryPort;
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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoUseCaseTest {

    @Mock
    private EmpleadoRepositoryPort empleadoRepositoryPort;

    @InjectMocks
    private EmpleadoUseCase empleadoUseCase;

    private Empleado empleado;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        empleado = new Empleado(1, "Isaac", "Developer", Collections.emptyList());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void shouldSaveEmpleado() {

        when(empleadoRepositoryPort.save(empleado)).thenReturn(empleado);

        Empleado resultado = empleadoUseCase.save(empleado);

        assertEquals(empleado, resultado);
        verify(empleadoRepositoryPort).save(empleado);
    }

    @Test
    void shouldReturnAllEmpleados() {

        Page<Empleado> page = new PageImpl<>(Collections.singletonList(empleado));

        when(empleadoRepositoryPort.findAll(pageable)).thenReturn(page);

        Page<Empleado> resultado = empleadoUseCase.findAll(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(empleado, resultado.getContent().get(0));
        verify(empleadoRepositoryPort).findAll(pageable);
    }

    @Test
    void shouldReturnEmpleadoWhenExists() {

        when(empleadoRepositoryPort.findById(1)).thenReturn(Optional.of(empleado));

        Empleado resultado = empleadoUseCase.findById(1);

        assertEquals(empleado, resultado);
        verify(empleadoRepositoryPort).findById(1);
    }

    @Test
    void shouldThrowEmpleadoNotFoundExceptionWhenEmpleadoDoesNotExist() {

        when(empleadoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> empleadoUseCase.findById(99)
        );

        verify(empleadoRepositoryPort).findById(99);
    }

    @Test
    void shouldReturnEmpleadoPageFilteredByNombre() {

        Page<Empleado> page = new PageImpl<>(Collections.singletonList(empleado));

        when(empleadoRepositoryPort.findByNombreContaining("Isaac", pageable))
                .thenReturn(page);

        Page<Empleado> resultado = empleadoUseCase.findByNombre("Isaac", pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(empleado, resultado.getContent().get(0));
        verify(empleadoRepositoryPort).findByNombreContaining("Isaac", pageable);
    }

    @Test
    void shouldUpdateEmpleadoWhenExists() {

        Empleado actualizado =
                new Empleado(null, "Isaac Actualizado", "Senior", Collections.emptyList());

        when(empleadoRepositoryPort.findById(1)).thenReturn(Optional.of(empleado));

        when(empleadoRepositoryPort.save(any(Empleado.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Empleado resultado = empleadoUseCase.update(1, actualizado);

        assertEquals(1, resultado.getId());
        assertEquals("Isaac Actualizado", resultado.getNombre());
        assertEquals("Senior", resultado.getRol());

        verify(empleadoRepositoryPort).findById(1);
        verify(empleadoRepositoryPort).save(actualizado);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingEmpleado() {

        when(empleadoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> empleadoUseCase.update(99, empleado)
        );

        verify(empleadoRepositoryPort).findById(99);
        verify(empleadoRepositoryPort, never()).save(any());
    }

    @Test
    void shouldDeleteEmpleadoWhenExists() {

        when(empleadoRepositoryPort.findById(1)).thenReturn(Optional.of(empleado));

        empleadoUseCase.delete(1);

        verify(empleadoRepositoryPort).findById(1);
        verify(empleadoRepositoryPort).delete(empleado);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingEmpleado() {

        when(empleadoRepositoryPort.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> empleadoUseCase.delete(99)
        );

        verify(empleadoRepositoryPort).findById(99);
        verify(empleadoRepositoryPort, never()).delete(any());
    }

}