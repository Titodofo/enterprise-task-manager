package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    public Empleado findById(int id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new EmpleadoNotFoundException("Empleado con id: " + id + " no encontrado"));
    }

    public Page<Empleado> findByNombre(String nombre, Pageable pageable) {
        return empleadoRepository.findByNombreContaining(nombre, pageable);
    }

    public Empleado update(int id, Empleado empleado) {
        findById(id);
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    public void delete(int id) {
        Empleado empleado = findById(id);
        empleadoRepository.delete(empleado);
    }
}
