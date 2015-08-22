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
@Table(name = "atividades")
@Getter
@Setter
public class Atividades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "idplano")
    private Long idplano;
    @Basic(optional = false)
    @Column(name = "idscript")
    private Long idscript;
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Long idusuario;
    @Basic(optional = false)
    @Column(name = "atividade")
    private String atividade;
    @Basic(optional = false)
    @Column(name = "dtcreate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtcreate;
    @Basic(optional = false)
    @Column(name = "enable")
    private int enable;

    public Atividades() {
    }

    public Atividades(Long id) {
        this.id = id;
    }

    public Atividades(Long idplano, Long idscript, Long idusuario, String atividade) {
       
        this.idplano = idplano;
        this.idscript = idscript;
        this.idusuario = idusuario;
        this.atividade = atividade;
        this.dtcreate = new Date();
        this.enable = 1;
    }
    
}
