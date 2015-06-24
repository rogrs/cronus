package br.com.autobot.jpa.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.autobot.vo.Projetos;

public class ProjetosServiceImplTest {

    private ProjetosServiceImpl service = null;

    @Before
    public void setUp() {
        service = new ProjetosServiceImpl();

    }

    private String getNameProjeto() {

        long i = service.count() + 1;
        return "Projeto " + i;
    }

  
    @Test
    public void listarPlanos() {
        List<Projetos> projetos = service.findAll();

        assertTrue(projetos.isEmpty());
    }

    @Test
    public void criarProjetoTest() {

        String projectName = getNameProjeto();
        Projetos entity = new Projetos(projectName);
        try {
            service.persist(entity);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
