package br.com.autobot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

//http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-rest-api/

import br.com.autobot.Application;
import br.com.autobot.model.Projetos;
import br.com.autobot.repository.ProjetosRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProjetosControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    private List<Projetos> projetosList = new ArrayList<>();

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private ProjetosRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


    @Before
    public void setup() {
    	 this.mockMvc = webAppContextSetup(webApplicationContext).build();

         this.repository.deleteAllInBatch();

         this.projetosList.add(repository.save(new Projetos("Projeto Alfa Terionte")));
         this.projetosList.add(repository.save(new Projetos("Projeto Omega")));
         this.projetosList.add(repository.save(new Projetos("zeus")));
    }

  /*  @Test
    public void projetoNotFound() throws Exception {
        mockMvc.perform(post("/projetos/")
                .content(this.json(new Projetos()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }*/

   /* @Test
    public void readSingleProjetos() throws Exception {
        mockMvc.perform(get("/projetos/"
                + this.projetosList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.projetosList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.projeto", is("Projeto Omega")));
    }
*/
  /*  @Test
    public void readProjetos() throws Exception {
        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.projetosList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].projeto", is("carlota")))
                .andExpect(jsonPath("$[1].id", is(this.projetosList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].projeto", is("teste projeto")));
    }*/

    @Test
    public void createProjeto() throws Exception {
        String projetoJson = json(new Projetos("Projeto Carionte"));
        this.mockMvc.perform(post("/projetos")
                .contentType(contentType)
                .content(projetoJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
	 
}
