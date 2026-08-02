package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Empleado findById(int id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new EmpleadoNotFoundException("Empleado con id: " + id + " no encontrado"));
    }

    public List<Empleado> findByNombre(String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    public void update(int id, Empleado empleado) {
        findById(id);
        empleado.setId(id);
        empleadoRepository.save(empleado);
    }

    public void delete(int id) {
        Empleado empleado = findById(id);
        empleadoRepository.delete(empleado);
    }
}
