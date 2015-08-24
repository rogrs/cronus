package br.com.autobot.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projetos")
@Getter
@Setter
public class Projetos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "projeto_name")
    private String projetoName;

    @Column(name = "projeto_description")
    private String projetoDescription;

    public Projetos() {

    }

    public Projetos(String projeto) {
        this.projetoName = projeto;
    }
}
