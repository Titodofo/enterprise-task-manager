package es.isaac.etm.enterprise_task_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nombre;
    private String descripcion;
    private LocalDate inicio;
    private LocalDate fin;
    private List<Empleado> empleados = new ArrayList<>();


    public void addEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public void removeEmpleado(Empleado empleado) {
        empleados.remove(empleado);
    }
}
