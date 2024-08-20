package br.com.pierre.sigta.util;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Usuario;

public class MeuCadastro {

	public static void main(String[] args) {

		DAOGeneric<Usuario> daoGeneric = new DAOGeneric<Usuario>();
		
		System.out.println(daoGeneric.buscarPorId(Usuario.class, 3L));
		
		System.exit(0);
	}

}
