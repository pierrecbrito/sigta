package br.com.pierre.sigta.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.pierre.sigta.model.Observacao;
import br.com.pierre.sigta.model.Prioridade;
import br.com.pierre.sigta.model.Status;
import br.com.pierre.sigta.model.Tarefa;
import br.com.pierre.sigta.model.Usuario;


public class TarefaTest {
	private Tarefa tarefa;
	
	@BeforeEach
	public void setUp() {
		tarefa = new Tarefa();
	}
	
	@Test
	public void testGetSetTitulo() {
		String titulo = "Estudar JUnit5";
		tarefa.setTitulo(titulo);
        assertEquals(titulo, tarefa.getTitulo(), "O título da tarefa deve ser igual ao valor definido");
	}
	
	@Test
	public void testGetSetDescricao() {
		String descricao = "Estudar JUnit5 com foco em testes unitários";
        tarefa.setDescricao(descricao);
        assertEquals(descricao, tarefa.getDescricao(), "A descrição da tarefa deve ser igual ao valor definido");
	}
	
	@Test
	public void testGetSetDataLimite() {
		LocalDateTime dataLimite = LocalDateTime.of(2023,12,31,23,59);
		tarefa.setDataLimite(dataLimite);
		assertEquals(dataLimite, tarefa.getDataLimite(), "A data limite da tarefa deve ser igual ao valor definido");
	}
	
	@Test
	public void testGetSetPrioridade() {
		tarefa.setPrioridade(Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, tarefa.getPrioridade(), "A prioridade da tarefa deve ser igual ao valor definido");
	}
	
	@Test
	public void testGetSetStatus() {
		tarefa.setStatus(Status.FINALIZADA);
		assertEquals(Status.FINALIZADA, tarefa.getStatus(), "O status da tarefa deve ser igual ao valor definido");
	}
	
	@Test
	public void testGetSetCodigo() {
		String codigo = "56849";
		tarefa.setCodigo(codigo);
		assertEquals(codigo, tarefa.getCodigo(), "O código da tarefa deve ser igual ao valor definido");
	}
	
	@Test
    public void testGeracaoDeCodigo() {
		tarefa.setTitulo("Dar banho no cachorro");
		tarefa.setDescricao("Com sabonete de côco");
		Usuario responsavel = new Usuario();
		responsavel.setId(1L);
		tarefa.setResponsavel(responsavel);
		tarefa.setDataLimite(LocalDateTime.of(2024,12,31,23,59));
		tarefa.setPrioridade(Prioridade.ALTA);
		
        assertNull(tarefa.getCodigo(), "O código deve ser nulo inicialmente");

        tarefa.definirCodigo();

        assertFalse(tarefa.getCodigo().isEmpty(), "O código não deve ser vazio após a definição"); 
    }
	
	/**
	 * Teste para verifica a adição de observação na mudança de status
	 */
	@Test
    public void testMudancaStatus() {	
		assertTrue(tarefa.getObservacoes().isEmpty());
		
        tarefa.setStatus(Status.FINALIZADA);
		
        assertEquals(1, tarefa.getObservacoes().size(), "A lista de observações tem que ter observação de troca de status.");
    }
	
	@Test
    public void testAddObservacao() {	
		assertTrue(tarefa.getObservacoes().isEmpty());
		
		String conteudo = "Teste de add Observação";
        tarefa.adicionarObservacao(conteudo);
        
        assertEquals(1, tarefa.getObservacoes().size(), "A lista de observações deve conter uma observação");
        assertEquals(conteudo, tarefa.getObservacoes().get(0).getConteudo(), "A observação adicionada deve ser igual à observação fornecida");
        
        tarefa.adicionarObservacao(null);
        assertEquals(1, tarefa.getObservacoes().size(), "A lista de observações não deve adicionar observações nulas");
        
        tarefa.adicionarObservacao("");
        assertEquals(1, tarefa.getObservacoes().size(), "A lista de observações não deve adicionar observações vazias");
        
        tarefa.adicionarObservacao("   ");
        assertEquals(1, tarefa.getObservacoes().size(), "A lista de observações não deve adicionar observações com espaços em branco");
    }
	
	@Test
    public void testClasseCSS() {	
		tarefa.setStatus(Status.EXECUTANDO);
		
		tarefa.setDataLimite(LocalDateTime.now().plusMinutes(-30));
		assertEquals("tarefaVencida", tarefa.getClasseCSS(), "Tarefa vencida deve retornar a classe CSS 'tarefaVencida'");
		
		tarefa.setDataLimite(LocalDateTime.now().plusMinutes(30));
		assertEquals("tarefaProximaDeVencer", tarefa.getClasseCSS(), "Tarefa próxima de vencer deve retornar a classe CSS 'tarefaProximaDeVencer'");
		
		tarefa.setDataLimite(LocalDateTime.now().plusHours(2));
		assertEquals("natural", tarefa.getClasseCSS(), "Tarefa em andamento deve retornar a classe CSS 'natural'");
		
		tarefa.setStatus(Status.FINALIZADA);
		assertEquals("tarefaConcluida", tarefa.getClasseCSS(), "Tarefa finalizada deve retornar a classe CSS 'tarefaConcluida'");
		
    }
	
	@Test
    public void testObservacoesOrdenadasDesc() {
        Observacao obs1 = new Observacao();
        obs1.setConteudo("Primeira observação");
        obs1.setCriadoEm(LocalDateTime.now());

        Observacao obs2 = new Observacao();
        obs2.setConteudo("Segunda observação");
        obs2.setCriadoEm(LocalDateTime.now());

        Observacao obs3 = new Observacao();
        obs3.setConteudo("Terceira observação");
        obs3.setCriadoEm(LocalDateTime.now());

        tarefa.getObservacoes().add(obs1);
        tarefa.getObservacoes().add(obs2);
        tarefa.getObservacoes().add(obs3);

        List<Observacao> observacoesOrdenadas = tarefa.getObservacoesOrdenadasDesc();

        assertEquals(3, observacoesOrdenadas.size(), "A lista deve conter três observações");
        assertEquals(obs3, observacoesOrdenadas.get(0), "A primeira observação deve ser a mais recente");
        assertEquals(obs2, observacoesOrdenadas.get(1), "A segunda observação deve ser a do meio");
        assertEquals(obs1, observacoesOrdenadas.get(2), "A terceira observação deve ser a mais antiga");
    }
	
	@Test
	public void testArquivada() {
		tarefa.setArquivada(true);
		assertTrue(tarefa.isArquivada());
	}
}
