package es.isaac.etm.enterprise_task_manager.dto.empleado;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmpleadoRequest {

    @NotBlank
    private String nombre;
    @NotBlank
    private String rol;
}
