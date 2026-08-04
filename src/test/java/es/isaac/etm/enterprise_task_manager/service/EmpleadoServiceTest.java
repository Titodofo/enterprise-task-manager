package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.repository.EmpleadoRepository;
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
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    private Empleado empleado;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        empleado = new Empleado(1, "Isaac", "Developer", Collections.emptyList());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void shouldSaveEmpleado() {

        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado resultado = empleadoService.save(empleado);

        assertEquals(empleado, resultado);
        verify(empleadoRepository).save(empleado);
    }

    @Test
    void shouldReturnAllEmpleados() {

        Page<Empleado> page = new PageImpl<>(Collections.singletonList(empleado));

        when(empleadoRepository.findAll(pageable)).thenReturn(page);

        Page<Empleado> resultado = empleadoService.findAll(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(empleado, resultado.getContent().get(0));
        verify(empleadoRepository).findAll(pageable);
    }

    @Test
    void shouldReturnEmpleadoWhenExists() {

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        Empleado resultado = empleadoService.findById(1);

        assertEquals(empleado, resultado);
        verify(empleadoRepository).findById(1);
    }

    @Test
    void shouldThrowEmpleadoNotFoundExceptionWhenEmpleadoDoesNotExist() {

        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> empleadoService.findById(99)
        );

        verify(empleadoRepository).findById(99);
    }

    @Test
    void shouldReturnEmpleadoPageFilteredByNombre() {

        Page<Empleado> page = new PageImpl<>(Collections.singletonList(empleado));

        when(empleadoRepository.findByNombreContaining("Isaac", pageable))
                .thenReturn(page);

        Page<Empleado> resultado = empleadoService.findByNombre("Isaac", pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(empleado, resultado.getContent().get(0));
        verify(empleadoRepository).findByNombreContaining("Isaac", pageable);
    }

    @Test
    void shouldUpdateEmpleadoWhenExists() {

        Empleado actualizado =
                new Empleado(null, "Isaac Actualizado", "Senior", Collections.emptyList());

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        when(empleadoRepository.save(any(Empleado.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Empleado resultado = empleadoService.update(1, actualizado);

        assertEquals(1, resultado.getId());
        assertEquals("Isaac Actualizado", resultado.getNombre());
        assertEquals("Senior", resultado.getRol());

        verify(empleadoRepository).findById(1);
        verify(empleadoRepository).save(actualizado);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingEmpleado() {

        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> empleadoService.update(99, empleado)
        );

        verify(empleadoRepository).findById(99);
        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void shouldDeleteEmpleadoWhenExists() {

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        empleadoService.delete(1);

        verify(empleadoRepository).findById(1);
        verify(empleadoRepository).delete(empleado);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingEmpleado() {

        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                EmpleadoNotFoundException.class,
                () -> empleadoService.delete(99)
        );

        verify(empleadoRepository).findById(99);
        verify(empleadoRepository, never()).delete(any());
    }

}