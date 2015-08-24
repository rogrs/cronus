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
@Table(name = "plugins")
@Getter
@Setter
public class Plugins {

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

}
