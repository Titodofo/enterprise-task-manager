package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoRequest;
import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoResponse;
import es.isaac.etm.enterprise_task_manager.mapper.EmpleadoMapper;
import es.isaac.etm.enterprise_task_manager.model.Empleado;
import es.isaac.etm.enterprise_task_manager.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<EmpleadoResponse>> getEmpleados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String nombre) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Empleado> empleados;

        if (nombre != null) {
            empleados = empleadoService.findByNombre(nombre, pageable);
        } else {
            empleados = empleadoService.findAll(pageable);
        }

        Page<EmpleadoResponse> response = empleados.map(empleadoMapper::toResponse);

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
        Empleado empleadoActualizado = empleadoService.update(id, empleado);

        return ResponseEntity.ok(empleadoMapper.toResponse(empleadoActualizado));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {

        empleadoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}