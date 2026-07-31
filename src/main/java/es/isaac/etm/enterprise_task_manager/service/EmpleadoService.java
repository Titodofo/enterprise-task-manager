package es.isaac.etm.enterprise_task_manager.service;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.repository.EmpleadoRepository;
import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado findById(int id) {
        Empleado empleado = empleadoRepository.findById(id);
        if (empleado == null) {
            throw new EmpleadoNotFoundException("Empleado con id: "+ id + " no encontrado");
        }
        return empleado;
    }
}
