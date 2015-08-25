package br.com.autobot.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "plugins")
@Getter
@Setter
public class Plugins extends EntityID {

    @Basic(optional = false)
    @Column(name = "plugin")
    private String plugin;
    @Basic(optional = false)
    @Column(name = "command")
    private String command;

    public Plugins() {
    }

    public Plugins(Long id) {
        this.setId(id);

    }

    public Plugins(Long id, String plugin, String command, Date dtcreate, int enable) {
        this.setId(id);
        this.plugin = plugin;
        this.command = command;

    }

}
