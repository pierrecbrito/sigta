package br.com.pierre.sigta.controller;

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
	private DAOGeneric<Tarefa> daoTarefa = new DAOGeneric<Tarefa>();
	
	public String cadastrar() {
		tarefa.setResponsavel(LoginUtil.getUsuario());
		daoTarefa.salvar(tarefa);
		tarefa = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public String atualizar() {
		daoTarefa.atualizar(tarefa);
		tarefa = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public String excluir() {
		daoTarefa.deletar(tarefa);
		tarefa = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public void selecionarTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public String concluirTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
		this.tarefa.setStatus(Status.FINALIZADA);
		daoTarefa.atualizar(this.tarefa);
		this.tarefa = new Tarefa();
		return "dash?faces-redirect=true";
	}
	
	public List<Tarefa> getTarefas() {
		List<Tarefa> listaTarefas = daoTarefa.getEntityManager().createNamedQuery("Tarefas.de")
				.setParameter("responsavel", LoginUtil.getUsuario())
				.getResultList();
		
        return listaTarefas;
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
	
	
}
