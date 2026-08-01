package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    public Proyecto findById(int id) {
        Proyecto proyecto = proyectoRepository.findById(id);
        if (proyecto == null) {
            throw new EmpleadoNotFoundException("Proyecto con id: " + id + " no encontrado");
        }
        return proyectoRepository.findById(id);
    }

    public void update(int id, Proyecto proyecto) {
        findById(id);
        proyectoRepository.update(id, proyecto);
    }

    public void delete(int id) {
        findById(id);
        proyectoRepository.delete(id);
    }

}
