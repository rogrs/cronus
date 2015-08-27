package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Projeto;
import com.mycompany.myapp.repository.ProjetoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProjetoResource REST controller.
 *
 * @see ProjetoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjetoResourceTest {

    private static final String DEFAULT_DESCRICAO = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRICAO = "UPDATED_TEXT";
    private static final String DEFAULT_DETALHES = "SAMPLE_TEXT";
    private static final String UPDATED_DETALHES = "UPDATED_TEXT";

    @Inject
    private ProjetoRepository projetoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restProjetoMockMvc;

    private Projeto projeto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjetoResource projetoResource = new ProjetoResource();
        ReflectionTestUtils.setField(projetoResource, "projetoRepository", projetoRepository);
        this.restProjetoMockMvc = MockMvcBuilders.standaloneSetup(projetoResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projeto = new Projeto();
        projeto.setDescricao(DEFAULT_DESCRICAO);
        projeto.setDetalhes(DEFAULT_DETALHES);
    }

    @Test
    @Transactional
    public void createProjeto() throws Exception {
        int databaseSizeBeforeCreate = projetoRepository.findAll().size();

        // Create the Projeto

        restProjetoMockMvc.perform(post("/api/projetos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projeto)))
                .andExpect(status().isCreated());

        // Validate the Projeto in the database
        List<Projeto> projetos = projetoRepository.findAll();
        assertThat(projetos).hasSize(databaseSizeBeforeCreate + 1);
        Projeto testProjeto = projetos.get(projetos.size() - 1);
        assertThat(testProjeto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProjeto.getDetalhes()).isEqualTo(DEFAULT_DETALHES);
    }

    @Test
    @Transactional
    public void getAllProjetos() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        // Get all the projetos
        restProjetoMockMvc.perform(get("/api/projetos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projeto.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())));
    }

    @Test
    @Transactional
    public void getProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        // Get the projeto
        restProjetoMockMvc.perform(get("/api/projetos/{id}", projeto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projeto.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.detalhes").value(DEFAULT_DETALHES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjeto() throws Exception {
        // Get the projeto
        restProjetoMockMvc.perform(get("/api/projetos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

		int databaseSizeBeforeUpdate = projetoRepository.findAll().size();

        // Update the projeto
        projeto.setDescricao(UPDATED_DESCRICAO);
        projeto.setDetalhes(UPDATED_DETALHES);
        

        restProjetoMockMvc.perform(put("/api/projetos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projeto)))
                .andExpect(status().isOk());

        // Validate the Projeto in the database
        List<Projeto> projetos = projetoRepository.findAll();
        assertThat(projetos).hasSize(databaseSizeBeforeUpdate);
        Projeto testProjeto = projetos.get(projetos.size() - 1);
        assertThat(testProjeto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProjeto.getDetalhes()).isEqualTo(UPDATED_DETALHES);
    }

    @Test
    @Transactional
    public void deleteProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

		int databaseSizeBeforeDelete = projetoRepository.findAll().size();

        // Get the projeto
        restProjetoMockMvc.perform(delete("/api/projetos/{id}", projeto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Projeto> projetos = projetoRepository.findAll();
        assertThat(projetos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
