package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ExecutarPlano;
import com.mycompany.myapp.repository.ExecutarPlanoRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ExecutarPlanoResource REST controller.
 *
 * @see ExecutarPlanoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExecutarPlanoResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_CRIADO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CRIADO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CRIADO_STR = dateTimeFormatter.print(DEFAULT_CRIADO);

    private static final DateTime DEFAULT_INICIO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_INICIO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_INICIO_STR = dateTimeFormatter.print(DEFAULT_INICIO);

    private static final DateTime DEFAULT_FINALIZADO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_FINALIZADO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_FINALIZADO_STR = dateTimeFormatter.print(DEFAULT_FINALIZADO);

    @Inject
    private ExecutarPlanoRepository executarPlanoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restExecutarPlanoMockMvc;

    private ExecutarPlano executarPlano;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExecutarPlanoResource executarPlanoResource = new ExecutarPlanoResource();
        ReflectionTestUtils.setField(executarPlanoResource, "executarPlanoRepository", executarPlanoRepository);
        this.restExecutarPlanoMockMvc = MockMvcBuilders.standaloneSetup(executarPlanoResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        executarPlano = new ExecutarPlano();
        executarPlano.setCriado(DEFAULT_CRIADO);
        executarPlano.setInicio(DEFAULT_INICIO);
        executarPlano.setFinalizado(DEFAULT_FINALIZADO);
    }

    @Test
    @Transactional
    public void createExecutarPlano() throws Exception {
        int databaseSizeBeforeCreate = executarPlanoRepository.findAll().size();

        // Create the ExecutarPlano

        restExecutarPlanoMockMvc.perform(post("/api/executarPlanos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(executarPlano)))
                .andExpect(status().isCreated());

        // Validate the ExecutarPlano in the database
        List<ExecutarPlano> executarPlanos = executarPlanoRepository.findAll();
        assertThat(executarPlanos).hasSize(databaseSizeBeforeCreate + 1);
        ExecutarPlano testExecutarPlano = executarPlanos.get(executarPlanos.size() - 1);
        assertThat(testExecutarPlano.getCriado().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CRIADO);
        assertThat(testExecutarPlano.getInicio().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_INICIO);
        assertThat(testExecutarPlano.getFinalizado().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_FINALIZADO);
    }

    @Test
    @Transactional
    public void checkCriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = executarPlanoRepository.findAll().size();
        // set the field null
        executarPlano.setCriado(null);

        // Create the ExecutarPlano, which fails.

        restExecutarPlanoMockMvc.perform(post("/api/executarPlanos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(executarPlano)))
                .andExpect(status().isBadRequest());

        List<ExecutarPlano> executarPlanos = executarPlanoRepository.findAll();
        assertThat(executarPlanos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExecutarPlanos() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);

        // Get all the executarPlanos
        restExecutarPlanoMockMvc.perform(get("/api/executarPlanos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(executarPlano.getId().intValue())))
                .andExpect(jsonPath("$.[*].criado").value(hasItem(DEFAULT_CRIADO_STR)))
                .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO_STR)))
                .andExpect(jsonPath("$.[*].finalizado").value(hasItem(DEFAULT_FINALIZADO_STR)));
    }

    @Test
    @Transactional
    public void getExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);

        // Get the executarPlano
        restExecutarPlanoMockMvc.perform(get("/api/executarPlanos/{id}", executarPlano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(executarPlano.getId().intValue()))
            .andExpect(jsonPath("$.criado").value(DEFAULT_CRIADO_STR))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO_STR))
            .andExpect(jsonPath("$.finalizado").value(DEFAULT_FINALIZADO_STR));
    }

    @Test
    @Transactional
    public void getNonExistingExecutarPlano() throws Exception {
        // Get the executarPlano
        restExecutarPlanoMockMvc.perform(get("/api/executarPlanos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);

		int databaseSizeBeforeUpdate = executarPlanoRepository.findAll().size();

        // Update the executarPlano
        executarPlano.setCriado(UPDATED_CRIADO);
        executarPlano.setInicio(UPDATED_INICIO);
        executarPlano.setFinalizado(UPDATED_FINALIZADO);
        

        restExecutarPlanoMockMvc.perform(put("/api/executarPlanos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(executarPlano)))
                .andExpect(status().isOk());

        // Validate the ExecutarPlano in the database
        List<ExecutarPlano> executarPlanos = executarPlanoRepository.findAll();
        assertThat(executarPlanos).hasSize(databaseSizeBeforeUpdate);
        ExecutarPlano testExecutarPlano = executarPlanos.get(executarPlanos.size() - 1);
        assertThat(testExecutarPlano.getCriado().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CRIADO);
        assertThat(testExecutarPlano.getInicio().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_INICIO);
        assertThat(testExecutarPlano.getFinalizado().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_FINALIZADO);
    }

    @Test
    @Transactional
    public void deleteExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);

		int databaseSizeBeforeDelete = executarPlanoRepository.findAll().size();

        // Get the executarPlano
        restExecutarPlanoMockMvc.perform(delete("/api/executarPlanos/{id}", executarPlano.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExecutarPlano> executarPlanos = executarPlanoRepository.findAll();
        assertThat(executarPlanos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
