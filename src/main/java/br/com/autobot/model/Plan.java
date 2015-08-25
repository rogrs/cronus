package br.com.autobot.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "plans")
@Getter
@Setter
public class Plan extends EntityID {

    @Basic(optional = false)
    @Column(name = "idprojeto")
    private Long idprojeto;
    @Basic(optional = false)
    @Column(name = "plano")
    private String plano;

    public Plan() {
    }

    public Plan(Long id) {
        this.setId(id);

    }

    public Plan(String plano) {
        this.idprojeto = 1L;
        this.plano = plano;

    }

    public Plan(Long idprojeto, String plano) {
        this.idprojeto = idprojeto;
        this.plano = plano;

    }

}
