package es.isaac.etm.enterprise_task_manager.exception;

public class EmpleadoAlreadyAssignedException extends ConflictException {
    public EmpleadoAlreadyAssignedException(String message) {
        super(message);
    }
}
