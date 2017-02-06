package br.com.rogrs.autobot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Script.
 */
@Entity
@Table(name = "script")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "script")
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @NotNull
    @Size(max = 500)
    @Column(name = "path", length = 500, nullable = false)
    private String path;

    @OneToMany(mappedBy = "scripts")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plano> planos = new HashSet<>();

    @ManyToOne
    private Plugin plugin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Script descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPath() {
        return path;
    }

    public Script path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Plano> getPlanos() {
        return planos;
    }

    public Script planos(Set<Plano> planos) {
        this.planos = planos;
        return this;
    }

    public Script addPlano(Plano plano) {
        planos.add(plano);
        plano.setScripts(this);
        return this;
    }

    public Script removePlano(Plano plano) {
        planos.remove(plano);
        plano.setScripts(null);
        return this;
    }

    public void setPlanos(Set<Plano> planos) {
        this.planos = planos;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Script plugin(Plugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Script script = (Script) o;
        if (script.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, script.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Script{" +
            "id=" + id +
            ", descricao='" + descricao + "'" +
            ", path='" + path + "'" +
            '}';
    }
}
