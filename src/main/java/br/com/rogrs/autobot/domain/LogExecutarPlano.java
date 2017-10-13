package br.com.rogrs.autobot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.com.rogrs.autobot.domain.enumeration.Status;

/**
 * A LogExecutarPlano.
 */
@Entity
@Table(name = "log_executar_plano")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "logexecutarplano")
public class LogExecutarPlano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "criado", nullable = false)
    private LocalDate criado;

    @Column(name = "finalizado")
    private LocalDate finalizado;

    @Size(max = 8000)
    @Column(name = "mensagem", length = 8000)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private ExecutarPlano execucao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCriado() {
        return criado;
    }

    public LogExecutarPlano criado(LocalDate criado) {
        this.criado = criado;
        return this;
    }

    public void setCriado(LocalDate criado) {
        this.criado = criado;
    }

    public LocalDate getFinalizado() {
        return finalizado;
    }

    public LogExecutarPlano finalizado(LocalDate finalizado) {
        this.finalizado = finalizado;
        return this;
    }

    public void setFinalizado(LocalDate finalizado) {
        this.finalizado = finalizado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LogExecutarPlano mensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Status getStatus() {
        return status;
    }

    public LogExecutarPlano status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ExecutarPlano getExecucao() {
        return execucao;
    }

    public LogExecutarPlano execucao(ExecutarPlano executarPlano) {
        this.execucao = executarPlano;
        return this;
    }

    public void setExecucao(ExecutarPlano executarPlano) {
        this.execucao = executarPlano;
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
        LogExecutarPlano logExecutarPlano = (LogExecutarPlano) o;
        if (logExecutarPlano.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logExecutarPlano.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogExecutarPlano{" +
            "id=" + getId() +
            ", criado='" + getCriado() + "'" +
            ", finalizado='" + getFinalizado() + "'" +
            ", mensagem='" + getMensagem() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
