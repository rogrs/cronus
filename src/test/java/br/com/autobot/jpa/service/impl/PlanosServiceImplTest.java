package br.com.autobot.jpa.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.autobot.vo.Planos;
import br.com.autobot.vo.Projetos;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ProjetosServiceImplTest.class
})
public class PlanosServiceImplTest {

    private PlanosServiceImpl service = null;
   
    
    
    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
        System.out.println("@BeforeClass - oneTimeSetUp");
    }
 
    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
        System.out.println("@AfterClass - oneTimeTearDown");
    }
 
 
 
    @After
    public void tearDown() {
        //collection.clear();
        System.out.println("@After - tearDown");
    }

    @Before
    public void setUp() {
        service = new PlanosServiceImpl();
     
    }

    private String getNamePlano() {

        long i = service.count() + 1;
        return "Plano de Teste " + i;
    }

    @Test
    public void listarPlanos(String planName) {
        List<Planos> planos = service.findByName(planName);

        for (Planos obj : planos) {
            System.out.println(obj);
        }
        assertTrue(planos.isEmpty());
    }

    @Test
    public void criarPlanoTest() {
        Projetos projeto = new Projetos(1L);
        String planName = getNamePlano();
        Planos entity = new Planos(projeto.getId(), planName);

        service.persist(entity);

    }

}
