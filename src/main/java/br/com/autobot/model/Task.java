package br.com.autobot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_list")
@Getter
@Setter
public class Task extends EntityID {

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_priority")
    private String taskPriority;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "task_archived")
    private int taskArchived = 0;

}
