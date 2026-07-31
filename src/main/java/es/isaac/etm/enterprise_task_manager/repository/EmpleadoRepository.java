package es.isaac.etm.enterprise_task_manager.repository;

import es.isaac.etm.enterprise_task_manager.model.Empleado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpleadoRepository {

    private final Map<Integer, Empleado> empleados;
    private int nextId = 1;

    public EmpleadoRepository() {
        this.empleados = new HashMap<>();
    }

    public Empleado save(Empleado empleado) {
        empleado.setId(nextId++);
        this.empleados.put(empleado.getId(), empleado);
        return empleado;
    }

    public List<Empleado> findAll() {
        return new ArrayList<>(this.empleados.values());
    }

    public Empleado findById(int id) {
        return this.empleados.get(id);
    }

    public void update(Empleado empleado) {
        this.empleados.put(empleado.getId(), empleado);
    }

    public void delete(int id) {
        this.empleados.remove(id);
    }
}
