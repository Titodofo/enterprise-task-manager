package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoAlreadyAssignedException;
import es.isaac.etm.enterprise_task_manager.exception.ProyectoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.repository.ProyectoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Proyecto> findAll(Pageable pageable) {
        return proyectoRepository.findAll(pageable);
    }

    public Proyecto findById(int id) {
        return proyectoRepository.findById(id).orElseThrow(() -> new ProyectoNotFoundException("Proyecto con id: " + id + " no encontrado"));
    }

    public Page<Proyecto> findByNombre(String nombre, Pageable pageable) {
        return proyectoRepository.findByNombreContaining(nombre, pageable);
    }

    public Proyecto update(int id, Proyecto proyecto) {

        findById(id);
        proyecto.setId(id);

        return proyectoRepository.save(proyecto);
    }

    public void delete(int id) {
        Proyecto proyecto = findById(id);
        proyectoRepository.delete(proyecto);
    }

    @Transactional
    public void addEmpleado(Integer idProyecto, Integer idEmpleado) {
        Proyecto proyecto = findById(idProyecto);
        Empleado empleado = empleadoService.findById(idEmpleado);
        if (proyecto.getEmpleados().contains(empleado)) {
            throw new EmpleadoAlreadyAssignedException("Empleado con id: " + idEmpleado + " ya forma parte del proyecto");
        }

        proyecto.addEmpleado(empleado);
    }

    @Transactional
    public void deleteEmpleado(Integer idProyecto, Integer idEmpleado) {
        Proyecto proyecto = findById(idProyecto);
        proyecto.removeEmpleado(empleadoService.findById(idEmpleado));
    }
}