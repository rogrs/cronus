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
@Table(name = "planos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planos.findAll", query = "SELECT p FROM Planos p"),
    @NamedQuery(name = "Planos.findById", query = "SELECT p FROM Planos p WHERE p.id = :id"),
    @NamedQuery(name = "Planos.findByIdprojeto", query = "SELECT p FROM Planos p WHERE p.idprojeto = :idprojeto"),
    @NamedQuery(name = "Planos.findByPlano", query = "SELECT p FROM Planos p WHERE p.plano = :plano"),
    @NamedQuery(name = "Planos.findByDtcreate", query = "SELECT p FROM Planos p WHERE p.dtcreate = :dtcreate"),
    @NamedQuery(name = "Planos.findByEnable", query = "SELECT p FROM Planos p WHERE p.enable = :enable")})
@Getter
@Setter
public class Planos implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public Planos(Long id, long idprojeto, String plano, Date dtcreate, int enable) {
        this.id = id;
        this.idprojeto = idprojeto;
        this.plano = plano;
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
        if (!(object instanceof Planos)) {
            return false;
        }
        Planos other = (Planos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  this.plano;
    }
    
}
