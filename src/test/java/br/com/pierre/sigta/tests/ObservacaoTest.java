package br.com.pierre.sigta.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.pierre.sigta.model.Observacao;
import br.com.pierre.sigta.model.Tarefa;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ObservacaoTest {

    private Observacao observacao;

    @BeforeEach
    public void setUp() {
        observacao = new Observacao();
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        observacao.setId(id);
        assertEquals(id, observacao.getId(), "O ID deve ser igual ao valor definido");
    }

    @Test
    public void testGetSetConteudo() {
        String conteudo = "Conteúdo de Teste";
        observacao.setConteudo(conteudo);
        assertEquals(conteudo, observacao.getConteudo(), "O conteúdo deve ser igual ao valor definido");
    }

    @Test
    public void testGetSetCriadoEm() {
        LocalDateTime criadoEm = LocalDateTime.of(2023, 1, 1, 10, 0);
        observacao.setCriadoEm(criadoEm);
        assertEquals(criadoEm, observacao.getCriadoEm(), "A data de criação deve ser igual ao valor definido");
    }

    @Test
    public void testGetSetTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa de Teste");
        tarefa.setDescricao("Descrição da Tarefa de Teste");
        observacao.setTarefa(tarefa);
        assertEquals(tarefa, observacao.getTarefa(), "A tarefa deve ser igual ao valor definido");
    }

    @Test
    public void testGetCriadoEmComoDate() {
        LocalDateTime criadoEm = LocalDateTime.of(2023, 1, 1, 10, 0);
        observacao.setCriadoEm(criadoEm);
        Date expectedDate = Date.from(criadoEm.atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(expectedDate, observacao.getCriadoEmComoDate(), "A data de criação como Date deve ser igual ao valor esperado");
    }
}
