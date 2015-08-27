package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Atividade;
import com.mycompany.myapp.repository.AtividadeRepository;

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
 * Test class for the AtividadeResource REST controller.
 *
 * @see AtividadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AtividadeResourceTest {

    private static final String DEFAULT_DESCRICAO = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRICAO = "UPDATED_TEXT";
    private static final String DEFAULT_DETALHE = "SAMPLE_TEXT";
    private static final String UPDATED_DETALHE = "UPDATED_TEXT";

    private static final Integer DEFAULT_FALHA_PARA = 1;
    private static final Integer UPDATED_FALHA_PARA = 2;

    @Inject
    private AtividadeRepository atividadeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restAtividadeMockMvc;

    private Atividade atividade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AtividadeResource atividadeResource = new AtividadeResource();
        ReflectionTestUtils.setField(atividadeResource, "atividadeRepository", atividadeRepository);
        this.restAtividadeMockMvc = MockMvcBuilders.standaloneSetup(atividadeResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        atividade = new Atividade();
        atividade.setDescricao(DEFAULT_DESCRICAO);
        atividade.setDetalhe(DEFAULT_DETALHE);
        atividade.setFalha_para(DEFAULT_FALHA_PARA);
    }

    @Test
    @Transactional
    public void createAtividade() throws Exception {
        int databaseSizeBeforeCreate = atividadeRepository.findAll().size();

        // Create the Atividade

        restAtividadeMockMvc.perform(post("/api/atividades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(atividade)))
                .andExpect(status().isCreated());

        // Validate the Atividade in the database
        List<Atividade> atividades = atividadeRepository.findAll();
        assertThat(atividades).hasSize(databaseSizeBeforeCreate + 1);
        Atividade testAtividade = atividades.get(atividades.size() - 1);
        assertThat(testAtividade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAtividade.getDetalhe()).isEqualTo(DEFAULT_DETALHE);
        assertThat(testAtividade.getFalha_para()).isEqualTo(DEFAULT_FALHA_PARA);
    }

    @Test
    @Transactional
    public void getAllAtividades() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividades
        restAtividadeMockMvc.perform(get("/api/atividades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(atividade.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].detalhe").value(hasItem(DEFAULT_DETALHE.toString())))
                .andExpect(jsonPath("$.[*].falha_para").value(hasItem(DEFAULT_FALHA_PARA)));
    }

    @Test
    @Transactional
    public void getAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get the atividade
        restAtividadeMockMvc.perform(get("/api/atividades/{id}", atividade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(atividade.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.detalhe").value(DEFAULT_DETALHE.toString()))
            .andExpect(jsonPath("$.falha_para").value(DEFAULT_FALHA_PARA));
    }

    @Test
    @Transactional
    public void getNonExistingAtividade() throws Exception {
        // Get the atividade
        restAtividadeMockMvc.perform(get("/api/atividades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

		int databaseSizeBeforeUpdate = atividadeRepository.findAll().size();

        // Update the atividade
        atividade.setDescricao(UPDATED_DESCRICAO);
        atividade.setDetalhe(UPDATED_DETALHE);
        atividade.setFalha_para(UPDATED_FALHA_PARA);
        

        restAtividadeMockMvc.perform(put("/api/atividades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(atividade)))
                .andExpect(status().isOk());

        // Validate the Atividade in the database
        List<Atividade> atividades = atividadeRepository.findAll();
        assertThat(atividades).hasSize(databaseSizeBeforeUpdate);
        Atividade testAtividade = atividades.get(atividades.size() - 1);
        assertThat(testAtividade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAtividade.getDetalhe()).isEqualTo(UPDATED_DETALHE);
        assertThat(testAtividade.getFalha_para()).isEqualTo(UPDATED_FALHA_PARA);
    }

    @Test
    @Transactional
    public void deleteAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

		int databaseSizeBeforeDelete = atividadeRepository.findAll().size();

        // Get the atividade
        restAtividadeMockMvc.perform(delete("/api/atividades/{id}", atividade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Atividade> atividades = atividadeRepository.findAll();
        assertThat(atividades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
