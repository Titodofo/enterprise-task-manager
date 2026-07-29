package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.model.Empleado;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoController {

    public EmpleadoController() {}

    public Empleado getUserById()  {


        return new Empleado();
    }
}
