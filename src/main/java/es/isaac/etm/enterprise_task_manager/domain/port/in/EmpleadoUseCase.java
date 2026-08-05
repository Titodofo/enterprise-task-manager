package es.isaac.etm.enterprise_task_manager.domain.port.in;

import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpleadoUseCase {

    Empleado save(Empleado empleado);

    Page<Empleado> findAll(Pageable pageable);

    Empleado findById(int id);

    Page<Empleado> findByNombre(String nombre, Pageable pageable);

    Empleado update(int id, Empleado empleado);

    void delete(int id);
}
