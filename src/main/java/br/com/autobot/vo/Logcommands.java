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
@Table(name = "logcommands")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logcommands.findAll", query = "SELECT l FROM Logcommands l"),
    @NamedQuery(name = "Logcommands.findById", query = "SELECT l FROM Logcommands l WHERE l.id = :id"),
    @NamedQuery(name = "Logcommands.findByIdusuario", query = "SELECT l FROM Logcommands l WHERE l.idusuario = :idusuario"),
    @NamedQuery(name = "Logcommands.findByDtcreate", query = "SELECT l FROM Logcommands l WHERE l.dtcreate = :dtcreate"),
    @NamedQuery(name = "Logcommands.findByCommand", query = "SELECT l FROM Logcommands l WHERE l.command = :command"),
    @NamedQuery(name = "Logcommands.findByInfo", query = "SELECT l FROM Logcommands l WHERE l.info = :info"),
    @NamedQuery(name = "Logcommands.findByStatus", query = "SELECT l FROM Logcommands l WHERE l.status = :status")})
@Getter
@Setter
public class Logcommands implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Long idusuario;
    @Basic(optional = false)
    @Column(name = "dtcreate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtcreate;
    @Basic(optional = false)
    @Column(name = "command")
    private String command;
    @Column(name = "info")
    private String info;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    public Logcommands() {
    }

    public Logcommands(Long id) {
        this.id = id;
    }

    public Logcommands(Long id, Long idusuario, Date dtcreate, String command, int status) {
        this.id = id;
        this.idusuario = idusuario;
        this.dtcreate = dtcreate;
        this.command = command;
        this.status = status;
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
        if (!(object instanceof Logcommands)) {
            return false;
        }
        Logcommands other = (Logcommands) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.command;
    }
    
}
