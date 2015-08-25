package br.com.autobot.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test_cases")
@Getter
@Setter
public class TestCases extends EntityID {

    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "comments")
    private String comments;

}
