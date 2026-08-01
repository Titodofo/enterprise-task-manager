package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Proyecto;
import es.isaac.etm.enterprise_task_manager.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping("/proyectos")
    public ResponseEntity<Proyecto> save(@Valid @RequestBody Proyecto proyecto) {
        Proyecto newProyecto = proyectoService.save(proyecto);
        return new ResponseEntity<>(newProyecto, HttpStatus.CREATED);
    }

    @GetMapping("/proyectos")
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        return ResponseEntity.ok(proyectoService.findAll());
    }

    @GetMapping("/proyectos/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Integer id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    @PutMapping("/proyectos/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody Proyecto proyecto) {
        proyectoService.update(id, proyecto);
        return ResponseEntity.ok().build();
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
