package es.isaac.etm.enterprise_task_manager.infrastructure.persistence.adapter;

import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.infrastructure.persistence.repository.ProyectoRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProyectoJpaAdapter implements es.isaac.etm.enterprise_task_manager.domain.port.out.ProyectoRepositoryPort {

    private final ProyectoRepositoryPort proyectoRepositoryPort;

    public ProyectoJpaAdapter(ProyectoRepositoryPort proyectoRepositoryPort) {
        this.proyectoRepositoryPort = proyectoRepositoryPort;
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        return proyectoRepositoryPort.save(proyecto);
    }

    @Override
    public Page<Proyecto> findAll(Pageable pageable) {
        return proyectoRepositoryPort.findAll(pageable);
    }

    @Override
    public Optional<Proyecto> findById(int id) {
        return proyectoRepositoryPort.findById(id);
    }

    @Override
    public Page<Proyecto> findByNombreContaining(String nombre, Pageable pageable) {
        return proyectoRepositoryPort.findByNombreContaining(nombre, pageable);
    }

    @Override
    public void delete(Proyecto proyecto) {
        proyectoRepositoryPort.delete(proyecto);
    }
}
