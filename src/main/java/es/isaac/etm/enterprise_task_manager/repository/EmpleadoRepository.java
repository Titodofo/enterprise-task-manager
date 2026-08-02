package es.isaac.etm.enterprise_task_manager.repository;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

}
