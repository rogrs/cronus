package br.com.autobot.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "planos")
@Getter
@Setter
public class Planos  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "idprojeto")
    private Long idprojeto;
    @Basic(optional = false)
    @Column(name = "plano")
    private String plano;
    @Basic(optional = false)
    @Column(name = "dtcreate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtcreate;
    @Basic(optional = false)
    @Column(name = "enable")
    private int enable;

    public Planos() {
    }

    public Planos(Long id) {
        this.id = id;
    }
    
    public Planos(String plano) {
        this.idprojeto = 1L;
        this.plano = plano;
        this.dtcreate = new Date();
        this.enable = 1;
    }

    public Planos(Long idprojeto, String plano) {
        this.idprojeto = idprojeto;
        this.plano = plano;
        this.dtcreate = new Date();
        this.enable = 1;
    }

}
