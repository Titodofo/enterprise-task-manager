package es.isaac.etm.enterprise_task_manager.domain.port.in;

import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProyectoUseCase {

    Proyecto save(Proyecto proyecto);

    Page<Proyecto> findAll(Pageable pageable);

    Proyecto findById(int id);

    Page<Proyecto> findByNombre(String nombre, Pageable pageable);

    Proyecto update(int id, Proyecto empleado);

    void delete(int id);

    void addEmpleado(Integer idProyecto, Integer idEmpleado);

    void deleteEmpleado(Integer idProyecto, Integer idEmpleado);
}