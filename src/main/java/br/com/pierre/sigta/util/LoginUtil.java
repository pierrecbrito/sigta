package br.com.pierre.sigta.util;

import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import br.com.pierre.sigta.model.Usuario;

public class LoginUtil {
	
	public static Usuario logar(String email, String senha) {
		Usuario usuario;
		try {
			usuario = (Usuario) HibernateUtil.getEntityManagerFactory().createEntityManager()
				.createQuery(" from Usuario where email = :email")
				.setParameter("email", email)
				.getSingleResult();
			
			if(BCrypt.checkpw(senha, usuario.getSenha())) {
				return usuario;
			}
			
		} catch (NoResultException ex) {
			return null;
		}
		
		return null;
	}
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static String getUsuarioNome() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		return usuario.getNome();
	}

	public static Long getUserId() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		return usuario.getId();
	}
	
	public static void logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
	}
}
