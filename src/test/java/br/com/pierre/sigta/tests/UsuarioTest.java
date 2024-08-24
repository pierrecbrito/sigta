package br.com.pierre.sigta.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import br.com.pierre.sigta.model.Tarefa;
import br.com.pierre.sigta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioTest {
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        usuario.setId(id);
        assertEquals(id, usuario.getId(), "O ID deve ser igual ao valor definido");
    }

    @Test
    public void testGetSetNome() {
        String nome = "Nome de Teste";
        usuario.setNome(nome);
        assertEquals(nome, usuario.getNome(), "O nome deve ser igual ao valor definido");
    }

    @Test
    public void testGetSetEmail() {
        String email = "teste@example.com";
        usuario.setEmail(email);
        assertEquals(email, usuario.getEmail(), "O email deve ser igual ao valor definido");
    }

    @Test
    public void testSetSenha() {
        String senha = "senha123";
        usuario.setSenha(senha);
        assertNotNull(usuario.getSenha(), "A senha não deve ser nula após ser definida");
        assertTrue(BCrypt.checkpw(senha, usuario.getSenha()), "A senha deve ser corretamente hashada");
    }

    @Test
    public void testGetSetTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setDescricao("Descrição da Tarefa 1");
        tarefas.add(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setDescricao("Descrição da Tarefa 2");
        tarefas.add(tarefa2);

        usuario.setTarefas(tarefas);
        assertEquals(tarefas, usuario.getTarefas(), "A lista de tarefas deve ser igual à lista definida");
    }

}
