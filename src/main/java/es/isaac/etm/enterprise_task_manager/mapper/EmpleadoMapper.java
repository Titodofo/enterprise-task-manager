package es.isaac.etm.enterprise_task_manager.mapper;

import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoRequest;
import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoResponse;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public Empleado toEntity(EmpleadoRequest request) {
        Empleado empleado = new Empleado();
        empleado.setNombre(request.getNombre());
        empleado.setRol(request.getRol());
        return empleado;
    }

    public EmpleadoResponse toResponse(Empleado empleado) {
        return new EmpleadoResponse(empleado.getId(), empleado.getNombre(), empleado.getRol());
    }

}
