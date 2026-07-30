package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpleadoController {

    @GetMapping("/empleados/{id}")
    public Empleado getEmpleadoById(@PathVariable int id) {
        return new Empleado(id, "Isaac", "Intern");
    }

    @PostMapping("/empleados")
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) {
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
    }

    @PutMapping ("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int id, @Valid @RequestBody Empleado empleado) {
        empleado.setId(id);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
