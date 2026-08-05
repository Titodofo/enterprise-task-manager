package es.isaac.etm.enterprise_task_manager.application.service;

import es.isaac.etm.enterprise_task_manager.domain.port.out.EmpleadoRepositoryPort;
import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoUseCase implements es.isaac.etm.enterprise_task_manager.domain.port.in.EmpleadoUseCase {

    private final EmpleadoRepositoryPort empleadoRepositoryPort;

    public EmpleadoUseCase(EmpleadoRepositoryPort empleadoRepositoryPort) {
        this.empleadoRepositoryPort = empleadoRepositoryPort;

    }

    public Empleado save(Empleado empleado) {
        return empleadoRepositoryPort.save(empleado);
    }

    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoRepositoryPort.findAll(pageable);
    }

    public Empleado findById(int id) {
        return empleadoRepositoryPort.findById(id).orElseThrow(() -> new EmpleadoNotFoundException("Empleado con id: " + id + " no encontrado"));
    }

    public Page<Empleado> findByNombre(String nombre, Pageable pageable) {
        return empleadoRepositoryPort.findByNombreContaining(nombre, pageable);
    }

    public Empleado update(int id, Empleado empleado) {
        findById(id);
        empleado.setId(id);
        return empleadoRepositoryPort.save(empleado);
    }

    public void delete(int id) {
        Empleado empleado = findById(id);
        empleadoRepositoryPort.delete(empleado);
    }
}
