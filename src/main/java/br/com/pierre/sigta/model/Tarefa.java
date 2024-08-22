package br.com.pierre.sigta.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
			   @NamedQuery(name = "Tarefas.de", query = "select t from Tarefa t where t.responsavel = :responsavel"),
			   @NamedQuery(name = "QuantidadeTarefas.de", query = "SELECT COUNT(t) FROM Tarefa t WHERE t.responsavel = :responsavel"),
			   @NamedQuery(name = "QuantidadeTarefasFinalizadas.de", query = "SELECT COUNT(t) FROM Tarefa t WHERE t.responsavel = :responsavel AND t.status = :statusFinalizada"),
			   @NamedQuery(name = "TarefasEmAndamentoOrdenadas.de", query = "SELECT t FROM Tarefa t WHERE t.status = :statusEmAndamento AND t.responsavel = :responsavel  ORDER BY t.dataHora ASC "),
			   @NamedQuery(name = "TarefasFinalizadasOrdenadas.de", query = "SELECT t FROM Tarefa t WHERE t.status = :statusFinalizada AND t.responsavel = :responsavel  ORDER BY t.dataHora ASC ")
			   })
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String titulo;
	@Column(nullable = false)
	private String descricao;
	@ManyToOne
	@JoinColumn(name = "responsavel_id", nullable = false)
	private Usuario responsavel;
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private Prioridade prioridade;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.EXECUTANDO;
	@Column(nullable = false)
	private LocalDateTime dataHora;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Usuario getResponsavel() {
		return responsavel;
	}
	
	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}
	
	public Prioridade getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Date getDataFinal() {
		return Date.from(dataHora.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public String getPrioridadeDescricao() {
		return this.prioridade.name().substring(0, 1).toUpperCase() + this.prioridade.name().substring(1).toLowerCase();
	}
	
	public String getStatusDescricao() {
		return this.status.name().substring(0, 1).toUpperCase() + this.status.name().substring(1).toLowerCase();
	}

	@Override
	public String toString() {
		return "Tarefa [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", responsavel=" + responsavel
				+ ", prioridade=" + prioridade + ", status=" + status + ", dataHora=" + dataHora + "]";
	}
	
}
