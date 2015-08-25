package br.com.autobot.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scripts")
@Getter
@Setter
public class Scripts extends EntityID {

    @Basic(optional = false)
    @Column(name = "idplugin")
    private Long idplugin;
    @Basic(optional = false)
    @Column(name = "script")
    private String script;
    @Basic(optional = false)
    @Column(name = "path")
    private String path;

    public Scripts() {
    }

    public Scripts(Long id) {
        this.setId(id);

    }

    public Scripts(Long id, long idplugin, String script, String path) {
        this.setId(id);
        this.idplugin = idplugin;
        this.script = script;
        this.path = path;
;
    }

}
