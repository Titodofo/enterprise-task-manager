package es.isaac.etm.enterprise_task_manager.infrastructure.web.controller;

import es.isaac.etm.enterprise_task_manager.dto.proyecto.ProyectoRequest;
import es.isaac.etm.enterprise_task_manager.dto.proyecto.ProyectoResponse;
import es.isaac.etm.enterprise_task_manager.mapper.ProyectoMapper;
import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.domain.port.in.ProyectoUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProyectoController {

    private final ProyectoUseCase proyectoUseCase;
    private final ProyectoMapper proyectoMapper;

    public ProyectoController(ProyectoUseCase proyectoUseCase, ProyectoMapper proyectoMapper) {
        this.proyectoUseCase = proyectoUseCase;
        this.proyectoMapper = proyectoMapper;
    }

    @PostMapping("/proyectos")
    public ResponseEntity<ProyectoResponse> save(@Valid @RequestBody ProyectoRequest request) {

        Proyecto proyecto = proyectoMapper.toEntity(request);

        Proyecto proyectoGuardado = proyectoUseCase.save(proyecto);

        return new ResponseEntity<>(proyectoMapper.toResponse(proyectoGuardado), HttpStatus.CREATED);
    }

    @GetMapping("/proyectos")
    public ResponseEntity<Page<ProyectoResponse>> getProyectos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String nombre) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Proyecto> proyectos;

        if (nombre != null) {
            proyectos = proyectoUseCase.findByNombre(nombre, pageable);
        } else {
            proyectos = proyectoUseCase.findAll(pageable);
        }

        Page<ProyectoResponse> response = proyectos.map(proyectoMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoResponse> getProyectoById(@PathVariable Integer id) {

        Proyecto proyecto = proyectoUseCase.findById(id);

        return ResponseEntity.ok(proyectoMapper.toResponse(proyecto));
    }

    @PutMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoResponse> update(@PathVariable Integer id, @Valid @RequestBody ProyectoRequest request) {

        Proyecto proyecto = proyectoMapper.toEntity(request);

        Proyecto proyectoActualizado = proyectoUseCase.update(id, proyecto);

        return ResponseEntity.ok(proyectoMapper.toResponse(proyectoActualizado));
    }

    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        proyectoUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/proyectos/{idProyecto}/empleados/{idEmpleado}")
    public ResponseEntity<Void> addEmpleado(@PathVariable Integer idProyecto, @PathVariable Integer idEmpleado) {

        proyectoUseCase.addEmpleado(idProyecto, idEmpleado);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/proyectos/{idProyecto}/empleados/{idEmpleado}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer idProyecto, @PathVariable Integer idEmpleado) {

        proyectoUseCase.deleteEmpleado(idProyecto, idEmpleado);

        return ResponseEntity.noContent().build();
    }
}