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
 * A Projeto.
 */
@Entity
@Table(name = "projeto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "projeto")
public class Projeto implements Serializable {

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

    @OneToMany(mappedBy = "projetos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plano> planos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Projeto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public Projeto detalhes(String detalhes) {
        this.detalhes = detalhes;
        return this;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Set<Plano> getPlanos() {
        return planos;
    }

    public Projeto planos(Set<Plano> planos) {
        this.planos = planos;
        return this;
    }

    public Projeto addPlano(Plano plano) {
        planos.add(plano);
        plano.setProjetos(this);
        return this;
    }

    public Projeto removePlano(Plano plano) {
        planos.remove(plano);
        plano.setProjetos(null);
        return this;
    }

    public void setPlanos(Set<Plano> planos) {
        this.planos = planos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Projeto projeto = (Projeto) o;
        if (projeto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Projeto{" +
            "id=" + id +
            ", descricao='" + descricao + "'" +
            ", detalhes='" + detalhes + "'" +
            '}';
    }
}
