package es.isaac.etm.enterprise_task_manager.dto.proyecto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ProyectoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate inicio;
    private LocalDate fin;
}
