package es.isaac.etm.enterprise_task_manager.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
