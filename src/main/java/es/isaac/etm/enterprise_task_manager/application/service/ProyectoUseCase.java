package es.isaac.etm.enterprise_task_manager.application.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoAlreadyAssignedException;
import es.isaac.etm.enterprise_task_manager.exception.ProyectoNotFoundException;
import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.infrastructure.persistence.repository.ProyectoRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProyectoUseCase implements es.isaac.etm.enterprise_task_manager.domain.port.in.ProyectoUseCase {

    private final ProyectoRepositoryPort proyectoRepositoryPort;
    private final EmpleadoUseCase empleadoUseCase;

    public ProyectoUseCase(ProyectoRepositoryPort proyectoRepositoryPort, EmpleadoUseCase empleadoUseCase) {
        this.proyectoRepositoryPort = proyectoRepositoryPort;
        this.empleadoUseCase = empleadoUseCase;
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepositoryPort.save(proyecto);
    }

    public Page<Proyecto> findAll(Pageable pageable) {
        return proyectoRepositoryPort.findAll(pageable);
    }

    public Proyecto findById(int id) {
        return proyectoRepositoryPort.findById(id).orElseThrow(() -> new ProyectoNotFoundException("Proyecto con id: " + id + " no encontrado"));
    }

    public Page<Proyecto> findByNombre(String nombre, Pageable pageable) {
        return proyectoRepositoryPort.findByNombreContaining(nombre, pageable);
    }

    public Proyecto update(int id, Proyecto proyecto) {

        findById(id);
        proyecto.setId(id);

        return proyectoRepositoryPort.save(proyecto);
    }

    public void delete(int id) {
        Proyecto proyecto = findById(id);
        proyectoRepositoryPort.delete(proyecto);
    }

    @Transactional
    public void addEmpleado(Integer idProyecto, Integer idEmpleado) {
        Proyecto proyecto = findById(idProyecto);
        Empleado empleado = empleadoUseCase.findById(idEmpleado);
        if (proyecto.getEmpleados().contains(empleado)) {
            throw new EmpleadoAlreadyAssignedException("Empleado con id: " + idEmpleado + " ya forma parte del proyecto");
        }

        proyecto.addEmpleado(empleado);
    }

    @Transactional
    public void deleteEmpleado(Integer idProyecto, Integer idEmpleado) {
        Proyecto proyecto = findById(idProyecto);
        proyecto.removeEmpleado(empleadoUseCase.findById(idEmpleado));
    }
}