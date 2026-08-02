package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoAlreadyAssignedException;
import es.isaac.etm.enterprise_task_manager.exception.ProyectoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final EmpleadoService empleadoService;

    public ProyectoService(ProyectoRepository proyectoRepository, EmpleadoService empleadoService) {
        this.proyectoRepository = proyectoRepository;
        this.empleadoService = empleadoService;
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    public Proyecto findById(int id) {
        return proyectoRepository.findById(id).orElseThrow(() -> new ProyectoNotFoundException("Proyecto con id: " + id + " no encontrado"));
    }

    public List<Proyecto> findByNombre(String nombre) {
        return proyectoRepository.findByNombre(nombre);
    }

    public void update(int id, Proyecto proyecto) {
        findById(id);
        proyecto.setId(id);
        proyectoRepository.save(proyecto);
    }

    public void delete(int id) {
        Proyecto proyecto = findById(id);
        proyectoRepository.delete(proyecto);
    }

    public void addEmpleado(Integer idProyecto, Integer idEmpleado) {
        Proyecto proyecto = findById(idProyecto);
        Empleado empleado = empleadoService.findById(idEmpleado);
        if (proyecto.getEmpleados().contains(empleado)) {
            throw new EmpleadoAlreadyAssignedException("Empleado con id: " + idEmpleado + " ya forma parte del proyecto");
        }
        proyecto.addEmpleado(empleado);
        update(idProyecto, proyecto);
    }

    public void deleteEmpleado(Integer idProyecto, Integer idEmpleado) {
        Proyecto proyecto = findById(idProyecto);
        proyecto.removeEmpleado(empleadoService.findById(idEmpleado));
        update(idProyecto, proyecto);
    }

}
