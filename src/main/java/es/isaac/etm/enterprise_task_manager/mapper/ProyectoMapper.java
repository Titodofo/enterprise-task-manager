package es.isaac.etm.enterprise_task_manager.mapper;

import es.isaac.etm.enterprise_task_manager.dto.proyecto.ProyectoRequest;
import es.isaac.etm.enterprise_task_manager.dto.proyecto.ProyectoResponse;
import es.isaac.etm.enterprise_task_manager.domain.model.Proyecto;
import org.springframework.stereotype.Component;

@Component
public class ProyectoMapper {

    public Proyecto toEntity(ProyectoRequest request) {

        Proyecto proyecto = new Proyecto();

        proyecto.setNombre(request.getNombre());
        proyecto.setDescripcion(request.getDescripcion());
        proyecto.setInicio(request.getInicio());
        proyecto.setFin(request.getFin());

        return proyecto;
    }

    public ProyectoResponse toResponse(Proyecto proyecto) {

        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getInicio(),
                proyecto.getFin()
        );
    }
}
