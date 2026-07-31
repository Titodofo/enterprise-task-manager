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
        Empleado empleado = empleadoRepository.findById(id);
        if (empleado == null) {
            throw new EmpleadoNotFoundException("Empleado con id: " + id + " no encontrado");
        }
        return empleado;
    }

    public void update(int id, Empleado empleado) {
        findById(id);
        empleado.setId(id);
        empleadoRepository.update(empleado);
    }

    public void delete(int id) {
        findById(id);
        empleadoRepository.delete(id);
    }
}
