package es.isaac.etm.enterprise_task_manager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Empleado {

    private Integer id;
    private String nombre;
    private String rol;

    public Empleado(Integer id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }
}
