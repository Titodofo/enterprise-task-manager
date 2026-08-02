package es.isaac.etm.enterprise_task_manager.dto.empleado;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmpleadoRequest {

    private String nombre;
    private String rol;
}
