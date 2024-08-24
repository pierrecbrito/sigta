package br.com.pierre.sigta.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Observacao;
import br.com.pierre.sigta.model.Prioridade;
import br.com.pierre.sigta.model.Status;
import br.com.pierre.sigta.model.Tarefa;
import br.com.pierre.sigta.util.LoginUtil;

@ManagedBean(name="tarefasManagedBean")
@ViewScoped
public class TarefasManagedBean {
	private Tarefa tarefa = new Tarefa();
	private Tarefa tarefaEdit = new Tarefa();
	private DAOGeneric<Tarefa> daoTarefa = new DAOGeneric<Tarefa>();
	private List<Tarefa> tarefas;
	private List<Tarefa> tarefasArquivadas;
	private String novaObservacao = "";
	
	//Filtros
	private String tituloFiltro = "";
	private String descricaoFiltro = "";
	private Prioridade prioridadeFiltro = null;
	private Status statusFiltro = null;
	private LocalDateTime dataLimiteInicioFiltro = null;
	private LocalDateTime dataLimiteFimFiltro = null;
	private String codigoFiltro = "";
	//Filtros de tarefas arquivadas
	private String tituloFiltroArquivadas = "";
	private String descricaoFiltroArquivadas  = "";
	private Prioridade prioridadeFiltroArquivadas  = null;
	private Status statusFiltroArquivadas  = null;
	private LocalDateTime dataLimiteInicioFiltroArquivadas  = null;
	private LocalDateTime dataLimiteFimFiltroArquivadas  = null;
	private String codigoFiltroArquivadas  = "";
	
	@PostConstruct
    public void init() {
        carregarTarefas();
        carregarTarefasArquivadas();
    }
	
	public String cadastrar() {
		tarefa.setResponsavel(LoginUtil.getUsuario());
		tarefa.adicionarObservacao("A tarefa foi criada.");
		
		daoTarefa.salvar(this.tarefa);
		
		this.tarefa = new Tarefa();
		carregarTarefas();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa salva com sucesso!", null));
		return "";
	}
	
	public String atualizar() {
		daoTarefa.atualizar(this.tarefaEdit);
		carregarTarefas();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa atualizada com sucesso!", null));
		return "";
	}
	
	public String excluir() {
		daoTarefa.deletar(this.tarefaEdit);
		this.tarefaEdit = new Tarefa();
		carregarTarefas();
		carregarTarefasArquivadas();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa excluída com sucesso!", null));
		return "";
	}
	
	public String arquivar() {
		this.tarefaEdit.setStatus(Status.FINALIZADA);
		this.tarefaEdit.adicionarObservacao("A tarefa foi arquivada.");
		this.tarefaEdit.setArquivada(true);
		daoTarefa.atualizar(this.tarefaEdit);
		this.tarefaEdit = new Tarefa();
		carregarTarefasArquivadas();
		carregarTarefas();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa arquivada com sucesso!", null));
		return "";
	}
	
	public void selecionarTarefa(Tarefa tarefa) {
		this.tarefaEdit = tarefa;
	}
	
	public void limparFiltros() {
		this.codigoFiltro = "";
		this.tituloFiltro = "";
		this.descricaoFiltro = "";
		this.prioridadeFiltro = null;
		this.statusFiltro = null;
		this.dataLimiteInicioFiltro = null;
		this.dataLimiteFimFiltro = null;
		carregarTarefas();
	}
	
	public void limparFiltrosArquivadas() {
		this.codigoFiltroArquivadas = "";
		this.tituloFiltroArquivadas = "";
		this.descricaoFiltroArquivadas = "";
		this.prioridadeFiltroArquivadas = null;
		this.statusFiltroArquivadas = null;
		this.dataLimiteInicioFiltroArquivadas = null;
		this.dataLimiteFimFiltroArquivadas = null;
		carregarTarefasArquivadas();
	}
	
	public String adicionarObservacao() {
		Observacao observacao = new Observacao();
		observacao.setConteudo(novaObservacao);
		observacao.setTarefa(tarefaEdit);
		tarefaEdit.getObservacoes().add(observacao);
		daoTarefa.atualizar(tarefaEdit);
		this.novaObservacao = "";
		return "";
	}
	
	public String concluirTarefa(Tarefa tarefa) {
		this.tarefaEdit = tarefa;
		this.tarefaEdit.setStatus(Status.FINALIZADA);
		daoTarefa.atualizar(this.tarefaEdit);
		this.tarefaEdit = new Tarefa();
		carregarTarefas();
		return "";
	}
	
	public String filtrarArquivadas() {
		carregarTarefasArquivadas();
		return "";
	}
	
	public String filtrar() {
		carregarTarefas();
		return "";
	}
	
	public void carregarTarefas() {
		List<Tarefa> listaTarefas = new ArrayList<Tarefa>();
		listaTarefas.addAll(getTarefasOrdenadas());
		
		if(codigoFiltro != null && !codigoFiltro.isEmpty()) {
			this.codigoFiltro = this.codigoFiltro.trim();
			listaTarefas.removeIf(t -> !t.getCodigo().toLowerCase().contains(codigoFiltro.toLowerCase()));
		}
		
		if(tituloFiltro != null && !tituloFiltro.isEmpty()) {
			this.tituloFiltro = this.tituloFiltro.trim();
			listaTarefas.removeIf(t -> !t.getTitulo().toLowerCase().contains(tituloFiltro.toLowerCase()));
		}
		
		if(descricaoFiltro != null && !descricaoFiltro.isEmpty()) { 
			this.descricaoFiltro = this.descricaoFiltro.trim();
			listaTarefas.removeIf(t -> !t.getDescricao().toLowerCase().contains(descricaoFiltro.toLowerCase()));
		}
		
		if(prioridadeFiltro != null) {
			listaTarefas.removeIf(t -> !t.getPrioridade().equals(prioridadeFiltro));
		}
		
		if(dataLimiteInicioFiltro != null || dataLimiteFimFiltro != null) {
			listaTarefas.removeIf(t -> !estaNoIntervalo(t.getDataLimite()));
		}
		
		if(statusFiltro != null) {
			listaTarefas.removeIf(t -> !t.getStatus().equals(statusFiltro));
		}
		
		listaTarefas.removeIf(t -> t.isArquivada());
		
        this.tarefas = listaTarefas;
	}
	
    
    public void carregarTarefasArquivadas() {
    	List<Tarefa> listaTarefas = new ArrayList<Tarefa>();
		listaTarefas.addAll(getTarefasOrdenadas());
		
		if (codigoFiltroArquivadas != null && !codigoFiltroArquivadas.isEmpty()) {
			this.codigoFiltroArquivadas = this.codigoFiltroArquivadas.trim();
			listaTarefas.removeIf(t -> !t.getCodigo().toLowerCase().contains(codigoFiltroArquivadas.toLowerCase()));
		}
		
		if (tituloFiltroArquivadas != null && !tituloFiltroArquivadas.isEmpty()) {
			this.tituloFiltroArquivadas = this.tituloFiltroArquivadas.trim();
			listaTarefas.removeIf(t -> !t.getTitulo().toLowerCase().contains(tituloFiltroArquivadas.toLowerCase()));
		}
		
		if (descricaoFiltroArquivadas != null && !descricaoFiltroArquivadas.isEmpty()) {
			this.descricaoFiltroArquivadas = this.descricaoFiltroArquivadas.trim();
			listaTarefas.removeIf(t -> !t.getDescricao().toLowerCase().contains(descricaoFiltroArquivadas.toLowerCase()));
		}
		
		if (prioridadeFiltroArquivadas != null) {
			listaTarefas.removeIf(t -> !t.getPrioridade().equals(prioridadeFiltroArquivadas));
		}
		
		if (dataLimiteInicioFiltroArquivadas != null || dataLimiteFimFiltroArquivadas != null) {
			listaTarefas.removeIf(t -> !estaNoIntervaloArquivadas(t.getDataLimite()));
		}
		
		listaTarefas.removeIf(t -> t.getStatus() != Status.FINALIZADA);
		listaTarefas.removeIf(t -> !t.isArquivada());
		
        this.tarefasArquivadas = listaTarefas;
    	  
    }
    
    // Método para retornar as classes CSS para cada linha
    public String getRowClasses() {
        return tarefas.stream()
                .map(tarefa -> tarefa.getClasseCSS())
                .collect(Collectors.joining(","));
    }
	
	private boolean estaNoIntervalo(LocalDateTime dataHora) {
        // Verifica se dateTimeToCheck está entre start e end (inclusive)
        return (dataHora.isEqual(this.dataLimiteInicioFiltro) || dataHora.isAfter(this.dataLimiteInicioFiltro)) &&
               (dataHora.isEqual(this.dataLimiteFimFiltro) || dataHora.isBefore(this.dataLimiteFimFiltro));
    }
	
	private boolean estaNoIntervaloArquivadas(LocalDateTime dataHora) {
        // Verifica se dateTimeToCheck está entre start e end (inclusive)
        return (dataHora.isEqual(this.dataLimiteInicioFiltroArquivadas) || dataHora.isAfter(this.dataLimiteInicioFiltroArquivadas)) &&
               (dataHora.isEqual(this.dataLimiteFimFiltroArquivadas) || dataHora.isBefore(this.dataLimiteFimFiltroArquivadas));
    }

	@SuppressWarnings("unchecked")
	public List<Tarefa> getTarefasOrdenadas() {
        List<Tarefa> listaTarefas = daoTarefa.getEntityManager().createNamedQuery("TarefasOrdenadas.de")
                .setParameter("responsavel", LoginUtil.getUsuario())
                .getResultList();

        return listaTarefas;
	}
	
	public String logout() {
		LoginUtil.logout();
		return "index?faces-redirect=true";
	}
	
	public String getPrimeiraLetraNome() {
		return LoginUtil.getUsuario().getNome().substring(0, 1);
	}
	
	
	public float getPorcentagemTarefasFinalizadas() {
		Long quantTarefasTotal = getQuantTarefasTotal();
		
		if (quantTarefasTotal == 0) {
			return 0;
		}
		
		return (getQuantTarefasFinalizadas() * 100) / quantTarefasTotal;
	}
	
	public Long getQuantTarefasTotal() {
		return (Long) daoTarefa.getEntityManager().createNamedQuery("QuantidadeTarefas.de")
				.setParameter("responsavel", LoginUtil.getUsuario()).getSingleResult();
	}
	
	public Long getQuantTarefasFinalizadas() {
		return (Long) daoTarefa.getEntityManager().createNamedQuery("QuantidadeTarefasFinalizadas.de")
				.setParameter("responsavel", LoginUtil.getUsuario())
				.setParameter("statusFinalizada", Status.FINALIZADA)
				.getSingleResult();
	}
	
	// Getters e Setters
    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
	
	public List<Tarefa> getTarefasArquivadas() {
        return tarefasArquivadas;
	}
	
	public void setTarefasArquivadas(List<Tarefa> tarefasArquivadas) {
		this.tarefasArquivadas = tarefasArquivadas;
	}

	
	public Prioridade[] getNiveisPrioridade(){
		   return Prioridade.values();
	}
	
	public Status[] getTiposStatus(){
		   return Status.values();
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Tarefa getTarefaEdit() {
		return tarefaEdit;
	}

	public void setTarefaEdit(Tarefa tarefaEdit) {
		this.tarefaEdit = tarefaEdit;
	}

	public DAOGeneric<Tarefa> getDaoTarefa() {
		return daoTarefa;
	}

	public void setDaoTarefa(DAOGeneric<Tarefa> daoTarefa) {
		this.daoTarefa = daoTarefa;
	}

	public String getTituloFiltro() {
		return tituloFiltro;
	}

	public void setTituloFiltro(String tituloFiltro) {
		this.tituloFiltro = tituloFiltro;
	}

	public String getDescricaoFiltro() {
		return descricaoFiltro;
	}

	public void setDescricaoFiltro(String descricaoFiltro) {
		this.descricaoFiltro = descricaoFiltro;
	}

	public Prioridade getPrioridadeFiltro() {
		return prioridadeFiltro;
	}

	public void setPrioridadeFiltro(Prioridade prioridadeFiltro) {
		this.prioridadeFiltro = prioridadeFiltro;
	}

	public Status getStatusFiltro() {
		return statusFiltro;
	}

	public void setStatusFiltro(Status statusFiltro) {
		this.statusFiltro = statusFiltro;
	}
	
	public LocalDateTime getDataLimiteInicioFiltro() {
		return dataLimiteInicioFiltro;
	}

	public void setDataLimiteInicioFiltro(LocalDateTime dataLimiteInicioFiltro) {
		this.dataLimiteInicioFiltro = dataLimiteInicioFiltro;
	}

	public LocalDateTime getDataLimiteFimFiltro() {
		return dataLimiteFimFiltro;
	}

	public void setDataLimiteFimFiltro(LocalDateTime dataLimiteFimFiltro) {
		this.dataLimiteFimFiltro = dataLimiteFimFiltro;
	}

	public String getCodigoFiltro() {
		return codigoFiltro;
	}

	public void setCodigoFiltro(String codigoFiltro) {
		this.codigoFiltro = codigoFiltro;
	}

	public String getTituloFiltroArquivadas() {
		return tituloFiltroArquivadas;
	}

	public void setTituloFiltroArquivadas(String tituloFiltroArquivadas) {
		this.tituloFiltroArquivadas = tituloFiltroArquivadas;
	}

	public String getDescricaoFiltroArquivadas() {
		return descricaoFiltroArquivadas;
	}

	public void setDescricaoFiltroArquivadas(String descricaoFiltroArquivadas) {
		this.descricaoFiltroArquivadas = descricaoFiltroArquivadas;
	}

	public Prioridade getPrioridadeFiltroArquivadas() {
		return prioridadeFiltroArquivadas;
	}

	public void setPrioridadeFiltroArquivadas(Prioridade prioridadeFiltroArquivadas) {
		this.prioridadeFiltroArquivadas = prioridadeFiltroArquivadas;
	}

	public Status getStatusFiltroArquivadas() {
		return statusFiltroArquivadas;
	}

	public void setStatusFiltroArquivadas(Status statusFiltroArquivadas) {
		this.statusFiltroArquivadas = statusFiltroArquivadas;
	}

	public LocalDateTime getDataLimiteInicioFiltroArquivadas() {
		return dataLimiteInicioFiltroArquivadas;
	}

	public void setDataLimiteInicioFiltroArquivadas(LocalDateTime dataLimiteInicioFiltroArquivadas) {
		this.dataLimiteInicioFiltroArquivadas = dataLimiteInicioFiltroArquivadas;
	}

	public LocalDateTime getDataLimiteFimFiltroArquivadas() {
		return dataLimiteFimFiltroArquivadas;
	}

	public void setDataLimiteFimFiltroArquivadas(LocalDateTime dataLimiteFimFiltroArquivadas) {
		this.dataLimiteFimFiltroArquivadas = dataLimiteFimFiltroArquivadas;
	}

	public String getCodigoFiltroArquivadas() {
		return codigoFiltroArquivadas;
	}

	public void setCodigoFiltroArquivadas(String codigoFiltroArquivadas) {
		this.codigoFiltroArquivadas = codigoFiltroArquivadas;
	}

	public String getNovaObservacao() {
		return novaObservacao;
	}

	public void setNovaObservacao(String novaObservacao) {
		this.novaObservacao = novaObservacao;
	}
	
	
}
