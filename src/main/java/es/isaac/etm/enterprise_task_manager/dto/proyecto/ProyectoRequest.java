package es.isaac.etm.enterprise_task_manager.dto.proyecto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProyectoRequest {

    private String nombre;
    private String descripcion;
    private LocalDate inicio;
    private LocalDate fin;

}
