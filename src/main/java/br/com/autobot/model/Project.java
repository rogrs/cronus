package br.com.autobot.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projetos")
@Getter
@Setter
public class Project extends EntityID {

    @Basic(optional = false)
    @Column(name = "projeto_name")
    private String projetoName;

    @Column(name = "projeto_description")
    private String projetoDescription;

    public Project() {

    }

    public Project(String project) {
        this.projetoName = project;

    }
}
