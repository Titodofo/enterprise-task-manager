package es.isaac.etm.enterprise_task_manager.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }
}
