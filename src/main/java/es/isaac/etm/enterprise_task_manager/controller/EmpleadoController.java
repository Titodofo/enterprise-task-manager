package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpleadoController {

    @GetMapping("/empleados/{id}")
    public Empleado getEmpleadoById(@PathVariable int id) {
        return new Empleado(id, "Isaac", "Intern");
    }

    @PostMapping("/empleados")
    public Empleado createEmpleado(@Valid @RequestBody Empleado empleado) {
        return empleado;
    }

    @PutMapping ("/empleados/{id}")
    public Empleado updateEmpleado(@PathVariable int id, @Valid @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleado;
    }
}
