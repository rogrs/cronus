package br.com.autobot.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "atividades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividades.findAll", query = "SELECT a FROM Atividades a"),
    @NamedQuery(name = "Atividades.findById", query = "SELECT a FROM Atividades a WHERE a.id = :id"),
    @NamedQuery(name = "Atividades.findByIdplano", query = "SELECT a FROM Atividades a WHERE a.idplano = :idplano"),
    @NamedQuery(name = "Atividades.findByIdscript", query = "SELECT a FROM Atividades a WHERE a.idscript = :idscript"),
    @NamedQuery(name = "Atividades.findByIdusuario", query = "SELECT a FROM Atividades a WHERE a.idusuario = :idusuario"),
    @NamedQuery(name = "Atividades.findByAtividade", query = "SELECT a FROM Atividades a WHERE a.atividade = :atividade"),
    @NamedQuery(name = "Atividades.findByDtcreate", query = "SELECT a FROM Atividades a WHERE a.dtcreate = :dtcreate"),
    @NamedQuery(name = "Atividades.findByEnable", query = "SELECT a FROM Atividades a WHERE a.enable = :enable")})
@Getter
@Setter
public class Atividades implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public Atividades(Long id, Long idplano, Long idscript, Long idusuario, String atividade, Date dtcreate, int enable) {
        this.id = id;
        this.idplano = idplano;
        this.idscript = idscript;
        this.idusuario = idusuario;
        this.atividade = atividade;
        this.dtcreate = dtcreate;
        this.enable = enable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atividades)) {
            return false;
        }
        Atividades other = (Atividades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  this.atividade;
    }
    
}
