package br.com.autobot.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "planos")
@Getter
@Setter
public class Plan extends ID {

	@Basic(optional = false)
	@Column(name = "idprojeto")
	private Long idprojeto;
	@Basic(optional = false)
	@Column(name = "plano")
	private String plano;
	@Basic(optional = false)
	@Column(name = "dtcreate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtcreate;
	@Basic(optional = false)
	@Column(name = "enable")
	private int enable;

	public Plan() {
	}

	public Plan(Long id) {
		this.setId(id);
		this.setDtcreate(new Date());
		this.setEnabled(true);
	}

	public Plan(String plano) {
		this.idprojeto = 1L;
		this.plano = plano;
		this.setDtcreate(new Date());
		this.setEnabled(true);
	}

	public Plan(Long idprojeto, String plano) {
		this.idprojeto = idprojeto;
		this.plano = plano;
		this.dtcreate = new Date();
		this.enable = 1;
	}

}
