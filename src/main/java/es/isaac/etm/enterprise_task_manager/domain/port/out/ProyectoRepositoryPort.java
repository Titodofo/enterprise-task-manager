package es.isaac.etm.enterprise_task_manager.domain.port.out;

import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProyectoRepositoryPort {

    Proyecto save(Proyecto proyecto);

    Page<Proyecto> findAll(Pageable pageable);

    Optional<Proyecto> findById(int id);

    Page<Proyecto> findByNombreContaining(String nombre, Pageable pageable);

    void delete(Proyecto proyecto);

}
