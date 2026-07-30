package es.isaac.etm.enterprise_task_manager.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    private Integer id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String rol;

}
