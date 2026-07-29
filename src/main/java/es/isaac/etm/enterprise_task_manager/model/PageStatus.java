package es.isaac.etm.enterprise_task_manager.model;

import lombok.Getter;

@Getter
public class PageStatus {

    private String status;

    public PageStatus(String status) {
        this.status = status;
    }
}
