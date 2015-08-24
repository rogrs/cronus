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
@Table(name = "scripts")
@Getter
@Setter
public class Scripts  {
   
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

}
