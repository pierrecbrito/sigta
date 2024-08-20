package br.com.pierre.sigta.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Tarefa;

@ManagedBean(name="tarefasManagedBean")
@ViewScoped
public class TarefasManagedBean {
	private Tarefa tarefa = new Tarefa();
	DAOGeneric<Tarefa> dao = new DAOGeneric<>();
	
	public String cadastrar() {
		tarefa = dao.salvar(tarefa);
		return "";
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	
}
