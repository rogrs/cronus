package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Plano;
import com.mycompany.myapp.repository.PlanoRepository;

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
 * Test class for the PlanoResource REST controller.
 *
 * @see PlanoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlanoResourceTest {

    private static final String DEFAULT_DESCRICAO = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRICAO = "UPDATED_TEXT";
    private static final String DEFAULT_DETALHE = "SAMPLE_TEXT";
    private static final String UPDATED_DETALHE = "UPDATED_TEXT";

    @Inject
    private PlanoRepository planoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPlanoMockMvc;

    private Plano plano;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanoResource planoResource = new PlanoResource();
        ReflectionTestUtils.setField(planoResource, "planoRepository", planoRepository);
        this.restPlanoMockMvc = MockMvcBuilders.standaloneSetup(planoResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        plano = new Plano();
        plano.setDescricao(DEFAULT_DESCRICAO);
        plano.setDetalhe(DEFAULT_DETALHE);
    }

    @Test
    @Transactional
    public void createPlano() throws Exception {
        int databaseSizeBeforeCreate = planoRepository.findAll().size();

        // Create the Plano

        restPlanoMockMvc.perform(post("/api/planos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plano)))
                .andExpect(status().isCreated());

        // Validate the Plano in the database
        List<Plano> planos = planoRepository.findAll();
        assertThat(planos).hasSize(databaseSizeBeforeCreate + 1);
        Plano testPlano = planos.get(planos.size() - 1);
        assertThat(testPlano.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlano.getDetalhe()).isEqualTo(DEFAULT_DETALHE);
    }

    @Test
    @Transactional
    public void getAllPlanos() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planos
        restPlanoMockMvc.perform(get("/api/planos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plano.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].detalhe").value(hasItem(DEFAULT_DETALHE.toString())));
    }

    @Test
    @Transactional
    public void getPlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get the plano
        restPlanoMockMvc.perform(get("/api/planos/{id}", plano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plano.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.detalhe").value(DEFAULT_DETALHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlano() throws Exception {
        // Get the plano
        restPlanoMockMvc.perform(get("/api/planos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

		int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Update the plano
        plano.setDescricao(UPDATED_DESCRICAO);
        plano.setDetalhe(UPDATED_DETALHE);
        

        restPlanoMockMvc.perform(put("/api/planos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plano)))
                .andExpect(status().isOk());

        // Validate the Plano in the database
        List<Plano> planos = planoRepository.findAll();
        assertThat(planos).hasSize(databaseSizeBeforeUpdate);
        Plano testPlano = planos.get(planos.size() - 1);
        assertThat(testPlano.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlano.getDetalhe()).isEqualTo(UPDATED_DETALHE);
    }

    @Test
    @Transactional
    public void deletePlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

		int databaseSizeBeforeDelete = planoRepository.findAll().size();

        // Get the plano
        restPlanoMockMvc.perform(delete("/api/planos/{id}", plano.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plano> planos = planoRepository.findAll();
        assertThat(planos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
