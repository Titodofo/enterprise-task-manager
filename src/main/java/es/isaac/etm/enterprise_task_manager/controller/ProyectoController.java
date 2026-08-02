package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.dto.proyecto.ProyectoRequest;
import es.isaac.etm.enterprise_task_manager.dto.proyecto.ProyectoResponse;
import es.isaac.etm.enterprise_task_manager.mapper.ProyectoMapper;
import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final ProyectoMapper proyectoMapper;

    public ProyectoController(ProyectoService proyectoService, ProyectoMapper proyectoMapper) {
        this.proyectoService = proyectoService;
        this.proyectoMapper = proyectoMapper;
    }

    @PostMapping("/proyectos")
    public ResponseEntity<ProyectoResponse> save(@Valid @RequestBody ProyectoRequest request) {

        Proyecto proyecto = proyectoMapper.toEntity(request);

        Proyecto proyectoGuardado = proyectoService.save(proyecto);

        return new ResponseEntity<>(proyectoMapper.toResponse(proyectoGuardado), HttpStatus.CREATED);
    }

    @GetMapping("/proyectos")
    public ResponseEntity<Page<ProyectoResponse>> getProyectos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String nombre) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Proyecto> proyectos;

        if (nombre != null) {
            proyectos = proyectoService.findByNombre(nombre, pageable);
        } else {
            proyectos = proyectoService.findAll(pageable);
        }

        Page<ProyectoResponse> response = proyectos.map(proyectoMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoResponse> getProyectoById(@PathVariable Integer id) {

        Proyecto proyecto = proyectoService.findById(id);

        return ResponseEntity.ok(proyectoMapper.toResponse(proyecto));
    }

    @PutMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoResponse> update(@PathVariable Integer id, @Valid @RequestBody ProyectoRequest request) {

        Proyecto proyecto = proyectoMapper.toEntity(request);

        Proyecto proyectoActualizado = proyectoService.update(id, proyecto);

        return ResponseEntity.ok(proyectoMapper.toResponse(proyectoActualizado));
    }

    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        proyectoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/proyectos/{idProyecto}/empleados/{idEmpleado}")
    public ResponseEntity<Void> addEmpleado(@PathVariable Integer idProyecto, @PathVariable Integer idEmpleado) {

        proyectoService.addEmpleado(idProyecto, idEmpleado);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/proyectos/{idProyecto}/empleados/{idEmpleado}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer idProyecto, @PathVariable Integer idEmpleado) {

        proyectoService.deleteEmpleado(idProyecto, idEmpleado);

        return ResponseEntity.noContent().build();
    }
}