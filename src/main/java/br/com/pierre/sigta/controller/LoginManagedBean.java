package br.com.pierre.sigta.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import br.com.pierre.sigta.model.Usuario;
import br.com.pierre.sigta.util.LoginUtil;

@ManagedBean( name = "loginManaged")
@ViewScoped
public class LoginManagedBean {
	private String email = "";
	private String senha = "";
	
	
	public String logar() {
		Usuario usuario = LoginUtil.logar(email, senha);

		if(usuario != null) {
			HttpSession session = LoginUtil.getSession();
			session.setAttribute("usuario", usuario);
			return "dash.xhtml?faces-redirect=true";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email ou senha incorretos!"));
		
        return "";
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
