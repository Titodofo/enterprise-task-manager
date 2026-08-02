package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoRequest;
import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoResponse;
import es.isaac.etm.enterprise_task_manager.mapper.EmpleadoMapper;
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
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoController(EmpleadoService empleadoService, EmpleadoMapper empleadoMapper) {
        this.empleadoService = empleadoService;
        this.empleadoMapper = empleadoMapper;
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoResponse> save(@Valid @RequestBody EmpleadoRequest request) {

        Empleado empleado = empleadoMapper.toEntity(request);
        Empleado empleadoGuardado = empleadoService.save(empleado);

        return new ResponseEntity<>(empleadoMapper.toResponse(empleadoGuardado), HttpStatus.CREATED);
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoResponse>> getEmpleados(@RequestParam(required = false) String nombre) {

        List<Empleado> empleados;

        if (nombre != null) {
            empleados = empleadoService.findByNombre(nombre);
        } else {
            empleados = empleadoService.findAll();
        }

        List<EmpleadoResponse> response = empleados.stream()
                .map(empleadoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoResponse> getEmpleadoById(@PathVariable int id) {

        Empleado empleado = empleadoService.findById(id);

        return ResponseEntity.ok(empleadoMapper.toResponse(empleado));
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoResponse> updateEmpleado(@PathVariable int id, @Valid @RequestBody EmpleadoRequest request) {

        Empleado empleado = empleadoMapper.toEntity(request);
        Empleado empleadoActualizado = empleadoService.save(empleado);

        return ResponseEntity.ok(empleadoMapper.toResponse(empleadoActualizado));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {

        empleadoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}