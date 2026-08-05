package es.isaac.etm.enterprise_task_manager.infrastructure.web.controller;

import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoRequest;
import es.isaac.etm.enterprise_task_manager.dto.empleado.EmpleadoResponse;
import es.isaac.etm.enterprise_task_manager.mapper.EmpleadoMapper;
import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import es.isaac.etm.enterprise_task_manager.domain.port.in.EmpleadoUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpleadoController {

    private final EmpleadoUseCase empleadoUseCase;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoController(EmpleadoUseCase empleadoUseCase, EmpleadoMapper empleadoMapper) {
        this.empleadoUseCase = empleadoUseCase;
        this.empleadoMapper = empleadoMapper;
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoResponse> save(@Valid @RequestBody EmpleadoRequest request) {

        Empleado empleado = empleadoMapper.toEntity(request);
        Empleado empleadoGuardado = empleadoUseCase.save(empleado);

        return new ResponseEntity<>(empleadoMapper.toResponse(empleadoGuardado), HttpStatus.CREATED);
    }

    @GetMapping("/empleados")
    public ResponseEntity<Page<EmpleadoResponse>> getEmpleados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String nombre) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Empleado> empleados;

        if (nombre != null) {
            empleados = empleadoUseCase.findByNombre(nombre, pageable);
        } else {
            empleados = empleadoUseCase.findAll(pageable);
        }

        Page<EmpleadoResponse> response = empleados.map(empleadoMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoResponse> getEmpleadoById(@PathVariable int id) {

        Empleado empleado = empleadoUseCase.findById(id);

        return ResponseEntity.ok(empleadoMapper.toResponse(empleado));
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoResponse> updateEmpleado(@PathVariable int id, @Valid @RequestBody EmpleadoRequest request) {

        Empleado empleado = empleadoMapper.toEntity(request);
        Empleado empleadoActualizado = empleadoUseCase.update(id, empleado);

        return ResponseEntity.ok(empleadoMapper.toResponse(empleadoActualizado));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable int id) {

        empleadoUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }

}