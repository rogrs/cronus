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
 * A ExecutarPlano.
 */
@Entity
@Table(name = "executar_plano")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "executarplano")
public class ExecutarPlano implements Serializable {

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

    @Column(name = "parar_na_falha")
    private Boolean pararNaFalha;

    @OneToMany(mappedBy = "execucao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogExecutarPlano> logExecutarPlanos = new HashSet<>();

    @ManyToOne
    private Plano plano;

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

    public ExecutarPlano descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public ExecutarPlano detalhes(String detalhes) {
        this.detalhes = detalhes;
        return this;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Boolean isPararNaFalha() {
        return pararNaFalha;
    }

    public ExecutarPlano pararNaFalha(Boolean pararNaFalha) {
        this.pararNaFalha = pararNaFalha;
        return this;
    }

    public void setPararNaFalha(Boolean pararNaFalha) {
        this.pararNaFalha = pararNaFalha;
    }

    public Set<LogExecutarPlano> getLogExecutarPlanos() {
        return logExecutarPlanos;
    }

    public ExecutarPlano logExecutarPlanos(Set<LogExecutarPlano> logExecutarPlanos) {
        this.logExecutarPlanos = logExecutarPlanos;
        return this;
    }

    public ExecutarPlano addLogExecutarPlano(LogExecutarPlano logExecutarPlano) {
        this.logExecutarPlanos.add(logExecutarPlano);
        logExecutarPlano.setExecucao(this);
        return this;
    }

    public ExecutarPlano removeLogExecutarPlano(LogExecutarPlano logExecutarPlano) {
        this.logExecutarPlanos.remove(logExecutarPlano);
        logExecutarPlano.setExecucao(null);
        return this;
    }

    public void setLogExecutarPlanos(Set<LogExecutarPlano> logExecutarPlanos) {
        this.logExecutarPlanos = logExecutarPlanos;
    }

    public Plano getPlano() {
        return plano;
    }

    public ExecutarPlano plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
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
        ExecutarPlano executarPlano = (ExecutarPlano) o;
        if (executarPlano.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), executarPlano.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExecutarPlano{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", detalhes='" + getDetalhes() + "'" +
            ", pararNaFalha='" + isPararNaFalha() + "'" +
            "}";
    }
}
