package es.isaac.etm.enterprise_task_manager.infrastructure.persistence.repository;

import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositoryPort extends JpaRepository<Proyecto, Integer> {

    Page<Proyecto> findByNombreContaining(String nombre, Pageable pageable);

}