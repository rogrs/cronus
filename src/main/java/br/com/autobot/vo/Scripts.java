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
@Table(name = "scripts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scripts.findAll", query = "SELECT s FROM Scripts s"),
    @NamedQuery(name = "Scripts.findById", query = "SELECT s FROM Scripts s WHERE s.id = :id"),
    @NamedQuery(name = "Scripts.findByIdplugin", query = "SELECT s FROM Scripts s WHERE s.idplugin = :idplugin"),
    @NamedQuery(name = "Scripts.findByScript", query = "SELECT s FROM Scripts s WHERE s.script = :script"),
    @NamedQuery(name = "Scripts.findByPath", query = "SELECT s FROM Scripts s WHERE s.path = :path"),
    @NamedQuery(name = "Scripts.findByDtcreate", query = "SELECT s FROM Scripts s WHERE s.dtcreate = :dtcreate"),
    @NamedQuery(name = "Scripts.findByEnable", query = "SELECT s FROM Scripts s WHERE s.enable = :enable")})
@Getter
@Setter
public class Scripts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "idplugin")
    private Long idplugin;
    @Basic(optional = false)
    @Column(name = "script")
    private String script;
    @Basic(optional = false)
    @Column(name = "path")
    private String path;
    @Basic(optional = false)
    @Column(name = "dtcreate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtcreate;
    @Basic(optional = false)
    @Column(name = "enable")
    private int enable;

    public Scripts() {
    }

    public Scripts(Long id) {
        this.id = id;
    }

    public Scripts(Long id, long idplugin, String script, String path, Date dtcreate, int enable) {
        this.id = id;
        this.idplugin = idplugin;
        this.script = script;
        this.path = path;
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
        if (!(object instanceof Scripts)) {
            return false;
        }
        Scripts other = (Scripts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.script;
    }
    
}
