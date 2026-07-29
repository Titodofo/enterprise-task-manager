package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoController {

    public EmpleadoController() {}

    @GetMapping("/empleados/{id}")
    public Empleado getEmpleadoById(@PathVariable int id) {
        return new Empleado(1, "Isaac", "Intern");
    }
}
