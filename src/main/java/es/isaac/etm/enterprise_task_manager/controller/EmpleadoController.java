package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoRequest;
import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoResponse;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoResponse> save(@Valid @RequestBody EmpleadoRequest request) {
        Empleado empleado = new Empleado();
        empleado.setNombre(request.getNombre());
        empleado.setRol(request.getRol());
        Empleado empleadoGuardado = empleadoService.save(empleado);
        EmpleadoResponse empleadoResponse = new EmpleadoResponse(
                empleadoGuardado.getId(),
                empleado.getNombre(),
                empleado.getRol()
        );
        return new ResponseEntity<>(empleadoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> getEmpleados(@RequestParam(required = false) String nombre) {
        if (nombre != null) {
            return new ResponseEntity<>(empleadoService.findByNombre(nombre), HttpStatus.OK);
        }
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable int id) {
        Empleado empleado = empleadoService.findById(id);
        return ResponseEntity.ok(empleado);
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
