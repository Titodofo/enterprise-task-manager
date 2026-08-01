package es.isaac.etm.enterprise_task_manager.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
