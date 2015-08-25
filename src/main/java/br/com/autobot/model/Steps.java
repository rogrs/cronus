package br.com.autobot.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "steps")
@Getter
@Setter
public class Steps extends EntityID {

    @Column(name = "notes")
    private String notes;

    @Column(name = "stop_failure")
    private boolean stopFailure;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "idscript")
    private Scripts scripts;

}
