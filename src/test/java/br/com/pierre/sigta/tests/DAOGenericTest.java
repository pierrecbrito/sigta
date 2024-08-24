package br.com.pierre.sigta.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.pierre.sigta.dao.DAOGeneric;
import br.com.pierre.sigta.model.Observacao;
import br.com.pierre.sigta.model.Prioridade;
import br.com.pierre.sigta.model.Tarefa;
import br.com.pierre.sigta.model.Usuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DAOGenericTest {
    private static  DAOGeneric<Usuario> daoUsuario;
    private static DAOGeneric<Tarefa> daoTarefa;
    private static DAOGeneric<Observacao> daoObservacao;

    @BeforeAll
    public static void setUp() {
        daoUsuario = new DAOGeneric<>();
        daoTarefa = new DAOGeneric<>();
        daoObservacao = new DAOGeneric<>();
    }

    @Test
    public void testarModels() {
    	//Cria um usuário de testes
	    Usuario usuario = new Usuario();
        usuario.setNome("Nome de Teste");
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("Senha de Teste");

        daoUsuario.salvar(usuario);

        Usuario usuarioBD = daoUsuario.buscarPorId(Usuario.class, usuario.getId());
        assertNotNull(usuarioBD, "O usuário deve ser salvo no banco de dados");
        assertEquals("Nome de Teste", usuarioBD.getNome(), "O nome do usuário deve ser igual ao valor definido");
      
        //Cria uma tarefa de testes
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao("Descrição de Teste");
        tarefa.setTitulo("Tarefa teste");
        tarefa.setArquivada(false);
        tarefa.setDataLimite(LocalDateTime.of(2024,12,31,23,59));
        tarefa.setPrioridade(Prioridade.ALTA);
        tarefa.adicionarObservacao("A tarefa foi criada.");
        tarefa.setResponsavel(usuario);
      
        daoTarefa.salvar(tarefa);

        Tarefa tarefaBD = daoTarefa.buscarPorId(Tarefa.class, tarefa.getId());
        assertNotNull(tarefaBD);
        assertEquals("Descrição de Teste", tarefaBD.getDescricao(), "A descrição da tarefa deve ser igual ao valor definido.");
        
        //Cria uma observação de testes
        Observacao observacao = new Observacao();
  	    observacao.setConteudo("Texto de teste de nova observação");
  	    observacao.setTarefa(tarefaBD);
  	  
        daoObservacao.salvar(observacao);

        Observacao observacaoBD = daoObservacao.buscarPorId(Observacao.class, observacao.getId());
        assertNotNull(observacaoBD);
        assertEquals("Texto de teste de nova observação", observacaoBD.getConteudo(), "O conteúdo da observação deve ser igual ao valor definido.");
        
        //Teste de remoção
        usuario.setNome("Nome Atualizado");
        daoUsuario.atualizar(usuario);

        assertEquals("Nome Atualizado", usuarioBD.getNome());
      
        daoObservacao.deletar(observacaoBD);
        daoTarefa.deletar(tarefaBD);
        daoUsuario.deletar(usuarioBD);
        
        assertThrows(NoResultException.class, () -> {
        	daoUsuario.getEntityManager()
        	            .createNamedQuery("UsuarioPeloEmail")
        	            .setParameter("email", "teste@gmail.com")
        	            .getSingleResult();
        }, "O usuário deve ser excluído do banco de dados");

    }



}
