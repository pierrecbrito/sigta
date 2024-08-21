package br.com.pierre.sigta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pierre.sigta.dao.DAOGeneric;
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
	
	public String cadastrar() {
		tarefa.setResponsavel(LoginUtil.getUsuario());
		daoTarefa.salvar(this.tarefa);
		this.tarefaEdit = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public String atualizar() {
		daoTarefa.atualizar(this.tarefaEdit);
		this.tarefaEdit = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public String excluir() {
		daoTarefa.deletar(this.tarefaEdit);
		this.tarefaEdit = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public void selecionarTarefa(Tarefa tarefa) {
		this.tarefaEdit = tarefa;
	}
	
	public String concluirTarefa(Tarefa tarefa) {
		this.tarefaEdit = tarefa;
		this.tarefaEdit.setStatus(Status.FINALIZADA);
		daoTarefa.atualizar(this.tarefaEdit);
		this.tarefaEdit = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public List<Tarefa> getTarefas() {
		List<Tarefa> listaTarefas = new ArrayList<Tarefa>();
		listaTarefas.addAll(getTarefasEmAndamentoOrdenadas());
		listaTarefas.addAll(getTarefasFinalizadasOrdenadas());
		
        return listaTarefas;
	}

	public List<Tarefa> getTarefasEmAndamentoOrdenadas() {
		List<Tarefa> listaTarefas = daoTarefa.getEntityManager().createNamedQuery("TarefasEmAndamentoOrdenadas.de")
				.setParameter("responsavel", LoginUtil.getUsuario())
				.setParameter("statusEmAndamento", Status.EXECUTANDO).getResultList();

		return listaTarefas;
	}
	
	public List<Tarefa> getTarefasFinalizadasOrdenadas() {
        List<Tarefa> listaTarefas = daoTarefa.getEntityManager().createNamedQuery("TarefasFinalizadasOrdenadas.de")
                .setParameter("responsavel", LoginUtil.getUsuario())
                .setParameter("statusFinalizada", Status.FINALIZADA).getResultList();

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
		return (getQuantTarefasFinalizadas() * 100) / getQuantTarefasTotal();
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
	
	
}
