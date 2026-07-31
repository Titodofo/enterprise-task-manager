package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable int id) {
        Empleado empleado = empleadoService.findById(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping("/empleados")
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) {
        Empleado empleadoGuardado = empleadoService.save(empleado);
        return new ResponseEntity<>(empleadoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int id, @Valid @RequestBody Empleado empleado) {
        empleadoService.update(id, empleado);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {
        empleadoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
