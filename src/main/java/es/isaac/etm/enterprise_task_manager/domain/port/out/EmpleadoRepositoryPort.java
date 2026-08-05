package es.isaac.etm.enterprise_task_manager.domain.port.out;

import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface EmpleadoRepositoryPort {

    Empleado save(Empleado empleado);

    Page<Empleado> findAll(Pageable pageable);

    Optional<Empleado> findById(int id);

    Page<Empleado> findByNombreContaining(String nombre, Pageable pageable);

    void delete(Empleado empleado);
}
