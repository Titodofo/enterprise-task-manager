package es.isaac.etm.enterprise_task_manager.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {

    private int id;
    @NotBlank
    private String nombre;
    private String descripcion;
    private LocalDate inicio;
    private LocalDate fin;
}
