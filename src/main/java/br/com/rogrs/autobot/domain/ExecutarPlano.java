package br.com.rogrs.autobot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import br.com.rogrs.autobot.domain.enumeration.Status;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "descricao", length = 80, nullable = false)
    private String descricao;

    @Size(max = 80)
    @Column(name = "detalhes", length = 80)
    private String detalhes;

    @Size(max = 200)
    @Column(name = "mensagem", length = 200)
    private String mensagem;

    @Column(name = "parar_na_falha")
    private Boolean pararNaFalha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private Script scripts;

    @ManyToOne
    private Plano plano;

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

    public String getMensagem() {
        return mensagem;
    }

    public ExecutarPlano mensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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

    public Status getStatus() {
        return status;
    }

    public ExecutarPlano status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Script getScripts() {
        return scripts;
    }

    public ExecutarPlano scripts(Script script) {
        this.scripts = script;
        return this;
    }

    public void setScripts(Script script) {
        this.scripts = script;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExecutarPlano executarPlano = (ExecutarPlano) o;
        if (executarPlano.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, executarPlano.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExecutarPlano{" +
            "id=" + id +
            ", descricao='" + descricao + "'" +
            ", detalhes='" + detalhes + "'" +
            ", mensagem='" + mensagem + "'" +
            ", pararNaFalha='" + pararNaFalha + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
