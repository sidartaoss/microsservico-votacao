/*
 * @(#) Sessao.java      1.00    28/05/2020
 * Copyrights (c) 2020 Sicredi.
 * Todos os direitos reservados.
 */
package com.sicredi.votacao.model;

import static com.sicredi.votacao.model.TipoStatus.EM_ANDAMENTO;
import static com.sicredi.votacao.model.TipoStatus.FINALIZADA;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Classe persistente que representa a entidade Sessao.
 * 
 * @author Sidarta Silva (semprebono@gmail.com)
 * @version $Revision$
 */
@Entity
@Table(name = "sessao")
public class Sessao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cd_sessao", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "duracao_minutos")
	private Integer duracaoEmMinutos;

	@Temporal(TIMESTAMP)
	@Column(name = "momento_inicio", nullable = false)
	private Date momentoInicio;

	@Temporal(TIMESTAMP)
	@Column(name = "momento_termino")
	private Date momentoTermino;

	@Transient
	private TipoStatus status;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "cd_pauta", nullable = false)
	private Pauta pauta;

	@Column(name = "total_votos_sim")
	private Long totalVotosSim;

	@Column(name = "total_votos_nao")
	private Long totalVotosNao;

	@Version
	@Column(name = "dt_modificacao")
	private Timestamp version;

	public Sessao() {
	}

	public Sessao(Long id, Integer duracaoEmMinutos, Pauta pauta) {
		this.id = id;
		this.duracaoEmMinutos = duracaoEmMinutos;
		this.pauta = pauta;
	}

	public Sessao(Long id, Integer duracaoEmMinutos, Pauta pauta, Date momentoInicio) {
		this.id = id;
		this.duracaoEmMinutos = duracaoEmMinutos;
		this.pauta = pauta;
		this.momentoInicio = momentoInicio;
	}

	public Sessao(Long id, Integer duracaoEmMinutos, Pauta pauta, Date momentoInicio, Date momentoTermino) {
		this.id = id;
		this.duracaoEmMinutos = duracaoEmMinutos;
		this.pauta = pauta;
		this.momentoInicio = momentoInicio;
		this.momentoTermino = momentoTermino;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDuracaoEmMinutos() {
		return duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
		this.duracaoEmMinutos = duracaoEmMinutos;
	}

	public Date getMomentoInicio() {
		return momentoInicio;
	}

	public void setMomentoInicio(Date momentoInicio) {
		this.momentoInicio = momentoInicio;
	}

	public Date getMomentoTermino() {
		return momentoTermino;
	}

	public void setMomentoTermino(Date momentoTermino) {
		this.momentoTermino = momentoTermino;
	}

	public TipoStatus getStatus() {
		return momentoInicio != null && momentoTermino != null ? FINALIZADA : EM_ANDAMENTO;
	}

	public void setStatus(TipoStatus status) {
		this.status = status;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public Long getTotalVotosSim() {
		return totalVotosSim;
	}

	public void setTotalVotosSim(Long totalVotosSim) {
		this.totalVotosSim = totalVotosSim;
	}

	public Long getTotalVotosNao() {
		return totalVotosNao;
	}

	public void setTotalVotosNao(Long totalVotosNao) {
		this.totalVotosNao = totalVotosNao;
	}

	@Override
	public String toString() {
		return "Sessao [id=" + id + ", duracaoEmMinutos=" + duracaoEmMinutos + ", momentoInicio=" + momentoInicio
				+ ", momentoTermino=" + momentoTermino + ", status=" + status + ", pauta=" + pauta + ", totalVotosSim="
				+ totalVotosSim + ", totalVotosNao=" + totalVotosNao + ", version=" + version + "]";
	}

}
