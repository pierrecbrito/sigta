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
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@NamedQueries({
			   @NamedQuery(name = "Tarefas.de", query = "select t from Tarefa t where t.responsavel = :responsavel"),
			   @NamedQuery(name = "QuantidadeTarefas.de", query = "SELECT COUNT(t) FROM Tarefa t WHERE t.responsavel = :responsavel AND t.arquivada = false"),
			   @NamedQuery(name = "QuantidadeTarefasFinalizadas.de", query = "SELECT COUNT(t) FROM Tarefa t WHERE t.responsavel = :responsavel AND t.status = :statusFinalizada AND t.arquivada = false"),
			   @NamedQuery(name = "TarefasEmAndamentoOrdenadas.de", query = "SELECT t FROM Tarefa t WHERE t.status = :statusEmAndamento AND t.responsavel = :responsavel  ORDER BY t.dataLimite ASC "),
			   @NamedQuery(name = "TarefasFinalizadasOrdenadas.de", query = "SELECT t FROM Tarefa t WHERE t.status = :statusFinalizada AND t.responsavel = :responsavel  ORDER BY t.dataLimite ASC ")
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
	private LocalDateTime dataLimite = LocalDateTime.now();
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime criadoEm;
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private boolean arquivada = false;
 
    private String gerarCodigoUnico() {
        String base = String.format("%s%s%d%s",
                titulo,
                descricao,
                responsavel.getId(),
                prioridade);

        int hash = Math.abs(base.hashCode());
        return String.format("%05d", hash % 100000); 
    }
    
    @PrePersist
    private void definirCodigo() {
        this.codigo = gerarCodigoUnico();
    }
	
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
	
	public LocalDateTime getDataLimite() {
		return dataLimite;
	}
	
	public void setDataLimite(LocalDateTime dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Date getDataLimiteComoDate() {
		return Date.from(dataLimite.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public String getPrioridadeDescricao() {
		return this.prioridade.name().substring(0, 1).toUpperCase() + this.prioridade.name().substring(1).toLowerCase();
	}
	
	public String getStatusDescricao() {
		return this.status.name().substring(0, 1).toUpperCase() + this.status.name().substring(1).toLowerCase();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	 public boolean isArquivada() {
	     return arquivada;
	 }

     public void setArquivada(boolean arquivada) {
        this.arquivada = arquivada;
     }

	@Override
	public String toString() {
		return "Tarefa [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", responsavel=" + responsavel
				+ ", prioridade=" + prioridade + ", status=" + status + ", dataLimite=" + dataLimite + ", criadoEm=" + criadoEm + ", estaArquivada = " + this.arquivada + "]";
	}
	
}
