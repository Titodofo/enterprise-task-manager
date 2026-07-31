package es.isaac.etm.enterprise_task_manager.exception;

public class EmpleadoNotFoundException extends RuntimeException {
    public EmpleadoNotFoundException(String message) {
        super(message);
    }
}
