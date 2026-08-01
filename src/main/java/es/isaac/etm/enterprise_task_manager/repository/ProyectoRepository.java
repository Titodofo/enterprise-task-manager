package es.isaac.etm.enterprise_task_manager.repository;

import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ProyectoRepository {

    private final Map<Integer, Proyecto> proyectos;
    private int nextId = 1;

    public ProyectoRepository() {
        proyectos = new HashMap<>();
    }

    public Proyecto save(Proyecto proyecto) {
        proyecto.setId(nextId++);
        this.proyectos.put(proyecto.getId(), proyecto);
        return proyecto;
    }

    public List<Proyecto> findAll() {
        return new ArrayList<>(this.proyectos.values());
    }

    public Proyecto findById(Integer id) {
        return proyectos.get(id);
    }

    public void update(int id, Proyecto proyecto) {
        proyecto.setId(id);
        this.proyectos.put(id, proyecto);
    }

    public void delete(Integer id) {
        proyectos.remove(id);
    }
}
