package br.com.rogrs.autobot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Atividade.
 */
@Entity
@Table(name = "atividade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "atividade")
public class Atividade implements Serializable {

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

    @Column(name = "parar_na_falha")
    private Boolean pararNaFalha;

    @ManyToOne
    private Plano plano;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Atividade nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComando() {
        return comando;
    }

    public Atividade comando(String comando) {
        this.comando = comando;
        return this;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Boolean isPararNaFalha() {
        return pararNaFalha;
    }

    public Atividade pararNaFalha(Boolean pararNaFalha) {
        this.pararNaFalha = pararNaFalha;
        return this;
    }

    public void setPararNaFalha(Boolean pararNaFalha) {
        this.pararNaFalha = pararNaFalha;
    }

    public Plano getPlano() {
        return plano;
    }

    public Atividade plano(Plano plano) {
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
        Atividade atividade = (Atividade) o;
        if (atividade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, atividade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Atividade{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", comando='" + comando + "'" +
            ", pararNaFalha='" + pararNaFalha + "'" +
            '}';
    }
}
