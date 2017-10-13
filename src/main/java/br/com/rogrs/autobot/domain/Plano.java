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

import br.com.rogrs.autobot.domain.enumeration.TipoPlano;

/**
 * A Plano.
 */
@Entity
@Table(name = "plano")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "plano")
public class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "descricao", length = 80, nullable = false)
    private String descricao;

    @Size(max = 80)
    @Column(name = "detalhes", length = 80)
    private String detalhes;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoPlano tipo;

    @OneToMany(mappedBy = "plano")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Atividade> atividades = new HashSet<>();

    @OneToMany(mappedBy = "plano")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExecutarPlano> executarPlanos = new HashSet<>();

    @ManyToOne
    private Projeto projetos;

    @ManyToOne
    private Script scripts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Plano descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public Plano detalhes(String detalhes) {
        this.detalhes = detalhes;
        return this;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public TipoPlano getTipo() {
        return tipo;
    }

    public Plano tipo(TipoPlano tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoPlano tipo) {
        this.tipo = tipo;
    }

    public Set<Atividade> getAtividades() {
        return atividades;
    }

    public Plano atividades(Set<Atividade> atividades) {
        this.atividades = atividades;
        return this;
    }

    public Plano addAtividade(Atividade atividade) {
        this.atividades.add(atividade);
        atividade.setPlano(this);
        return this;
    }

    public Plano removeAtividade(Atividade atividade) {
        this.atividades.remove(atividade);
        atividade.setPlano(null);
        return this;
    }

    public void setAtividades(Set<Atividade> atividades) {
        this.atividades = atividades;
    }

    public Set<ExecutarPlano> getExecutarPlanos() {
        return executarPlanos;
    }

    public Plano executarPlanos(Set<ExecutarPlano> executarPlanos) {
        this.executarPlanos = executarPlanos;
        return this;
    }

    public Plano addExecutarPlano(ExecutarPlano executarPlano) {
        this.executarPlanos.add(executarPlano);
        executarPlano.setPlano(this);
        return this;
    }

    public Plano removeExecutarPlano(ExecutarPlano executarPlano) {
        this.executarPlanos.remove(executarPlano);
        executarPlano.setPlano(null);
        return this;
    }

    public void setExecutarPlanos(Set<ExecutarPlano> executarPlanos) {
        this.executarPlanos = executarPlanos;
    }

    public Projeto getProjetos() {
        return projetos;
    }

    public Plano projetos(Projeto projeto) {
        this.projetos = projeto;
        return this;
    }

    public void setProjetos(Projeto projeto) {
        this.projetos = projeto;
    }

    public Script getScripts() {
        return scripts;
    }

    public Plano scripts(Script script) {
        this.scripts = script;
        return this;
    }

    public void setScripts(Script script) {
        this.scripts = script;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plano plano = (Plano) o;
        if (plano.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plano.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plano{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", detalhes='" + getDetalhes() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
