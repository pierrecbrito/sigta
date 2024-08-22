package br.com.pierre.sigta.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Usuario;
import br.com.pierre.sigta.util.LoginUtil;

@ManagedBean( name = "signupManaged")
@RequestScoped
public class SignUpManagedBean {
	private Usuario usuario = new Usuario();
	DAOGeneric<Usuario> dao = new DAOGeneric<>();
	
	
	public String cadastrar() {
		usuario = dao.salvar(usuario);

		if(usuario != null) {
			HttpSession session = LoginUtil.getSession();
			session.setAttribute("usuario", usuario);
			return "dash.xhtml?faces-redirect=true";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Falha ao cadastrar!"));
		
        return "";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
