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
 * A Plugin.
 */
@Entity
@Table(name = "plugin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "plugin")
public class Plugin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 100)
    @Column(name = "comando", length = 100, nullable = false)
    private String comando;

    @OneToMany(mappedBy = "plugin")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Script> scripts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Plugin nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComando() {
        return comando;
    }

    public Plugin comando(String comando) {
        this.comando = comando;
        return this;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Set<Script> getScripts() {
        return scripts;
    }

    public Plugin scripts(Set<Script> scripts) {
        this.scripts = scripts;
        return this;
    }

    public Plugin addScript(Script script) {
        scripts.add(script);
        script.setPlugin(this);
        return this;
    }

    public Plugin removeScript(Script script) {
        scripts.remove(script);
        script.setPlugin(null);
        return this;
    }

    public void setScripts(Set<Script> scripts) {
        this.scripts = scripts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plugin plugin = (Plugin) o;
        if (plugin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, plugin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plugin{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", comando='" + comando + "'" +
            '}';
    }
}
