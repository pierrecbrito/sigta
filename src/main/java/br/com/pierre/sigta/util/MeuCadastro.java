package br.com.pierre.sigta.util;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Usuario;

public class MeuCadastro {

	public static void main(String[] args) {
		Usuario usuario = new Usuario();
		usuario.setNome("Pierre Brito");
		usuario.setEmail("pierre@gmail.com");
		usuario.setSenha("123456789");
		
		DAOGeneric<Usuario> daoGeneric = new DAOGeneric<Usuario>();
		daoGeneric.salvar(usuario);
	}

}
