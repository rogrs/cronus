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
@Table(name = "plugins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plugins.findAll", query = "SELECT p FROM Plugins p"),
    @NamedQuery(name = "Plugins.findById", query = "SELECT p FROM Plugins p WHERE p.id = :id"),
    @NamedQuery(name = "Plugins.findByPlugin", query = "SELECT p FROM Plugins p WHERE p.plugin = :plugin"),
    @NamedQuery(name = "Plugins.findByCommand", query = "SELECT p FROM Plugins p WHERE p.command = :command"),
    @NamedQuery(name = "Plugins.findByDtcreate", query = "SELECT p FROM Plugins p WHERE p.dtcreate = :dtcreate"),
    @NamedQuery(name = "Plugins.findByEnable", query = "SELECT p FROM Plugins p WHERE p.enable = :enable")})
@Getter
@Setter
public class Plugins implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "plugin")
    private String plugin;
    @Basic(optional = false)
    @Column(name = "command")
    private String command;
    @Basic(optional = false)
    @Column(name = "dtcreate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtcreate;
    @Basic(optional = false)
    @Column(name = "enable")
    private int enable;

    public Plugins() {
    }

    public Plugins(Long id) {
        this.id = id;
    }

    public Plugins(Long id, String plugin, String command, Date dtcreate, int enable) {
        this.id = id;
        this.plugin = plugin;
        this.command = command;
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
        if (!(object instanceof Plugins)) {
            return false;
        }
        Plugins other = (Plugins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.plugin;
    }
    
}
