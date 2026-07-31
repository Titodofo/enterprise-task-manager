package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.repository.EmpleadoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping("/empleados/{id}")
    public Empleado getEmpleadoById(@PathVariable int id) {
        return empleadoRepository.findById(id);
    }

    @PostMapping("/empleados")
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) {
        empleadoRepository.save(empleado);
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
    }

    @PutMapping ("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int id, @Valid @RequestBody Empleado empleado) {
        empleado.setId(id);
        empleadoRepository.update(empleado);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {
        empleadoRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
