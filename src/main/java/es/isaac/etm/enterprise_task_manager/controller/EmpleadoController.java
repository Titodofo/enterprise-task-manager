package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpleadoController {

    public EmpleadoController() {}

    @GetMapping("/empleados/{id}")
    public Empleado getEmpleadoById(@PathVariable int id) {
        return new Empleado(1, "Isaac", "Intern");
    }

    @PostMapping("/empleados")
    public Empleado createEmpleado(@RequestBody Empleado empleado) {
        return empleado;
    }
}
