package es.isaac.etm.enterprise_task_manager.model;

import jakarta.persistence.*;
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
    private String nombre;
    private String descripcion;
    private LocalDate inicio;
    private LocalDate fin;
    @ManyToMany
    @JoinTable(
            name = "proyecto_empleado",
            joinColumns = @JoinColumn(name = "proyecto_id"),
            inverseJoinColumns = @JoinColumn(name = "empleado_id")
    )
    private List<Empleado> empleados = new ArrayList<>();

    public void addEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public void removeEmpleado(Empleado empleado) {
        empleados.remove(empleado);
    }
}
