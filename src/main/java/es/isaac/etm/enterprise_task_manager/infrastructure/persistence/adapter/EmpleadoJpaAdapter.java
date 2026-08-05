package es.isaac.etm.enterprise_task_manager.infrastructure.persistence.adapter;

import es.isaac.etm.enterprise_task_manager.domain.model.Empleado;
import es.isaac.etm.enterprise_task_manager.infrastructure.persistence.repository.EmpleadoRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EmpleadoJpaAdapter implements es.isaac.etm.enterprise_task_manager.domain.port.out.EmpleadoRepositoryPort {

    private final EmpleadoRepositoryPort empleadoRepositoryPort;

    public EmpleadoJpaAdapter(EmpleadoRepositoryPort empleadoRepositoryPort) {
        this.empleadoRepositoryPort = empleadoRepositoryPort;
    }

    @Override
    public Empleado save(Empleado empleado) {
        return empleadoRepositoryPort.save(empleado);
    }

    @Override
    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoRepositoryPort.findAll(pageable);
    }

    @Override
    public Optional<Empleado> findById(int id) {
        return empleadoRepositoryPort.findById(id);
    }

    @Override
    public Page<Empleado> findByNombreContaining(String nombre, Pageable pageable) {
        return empleadoRepositoryPort.findByNombreContaining(nombre, pageable);
    }

    @Override
    public void delete(Empleado empleado) {
        empleadoRepositoryPort.delete(empleado);
    }
}