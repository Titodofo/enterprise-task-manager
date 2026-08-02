package es.isaac.etm.enterprise_task_manager.repository;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    List<Empleado> findByNombre(String nombre);
}
