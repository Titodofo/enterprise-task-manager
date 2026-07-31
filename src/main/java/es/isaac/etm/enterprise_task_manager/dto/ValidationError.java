package es.isaac.etm.enterprise_task_manager.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationError {

    private String field;
    private String message;

}