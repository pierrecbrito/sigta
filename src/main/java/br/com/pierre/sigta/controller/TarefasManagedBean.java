package br.com.pierre.sigta.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Prioridade;
import br.com.pierre.sigta.model.Tarefa;
import br.com.pierre.sigta.util.LoginUtil;

@ManagedBean(name="tarefasManagedBean")
@ViewScoped
public class TarefasManagedBean {
	private Tarefa tarefa = new Tarefa();
	DAOGeneric<Tarefa> dao = new DAOGeneric<>();
	
	public String cadastrar() {
		System.out.println("Usuário: " + LoginUtil.getUsuario());
		tarefa.setResponsavel(LoginUtil.getUsuario());
		tarefa = dao.salvar(tarefa);
		return "";
	}
	
	public List<Tarefa> getTarefas() {
        return LoginUtil.getUsuario().getTarefas();
	}
	
	public Prioridade[] getNiveisPrioridade(){
		   return Prioridade.values();
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	
}
