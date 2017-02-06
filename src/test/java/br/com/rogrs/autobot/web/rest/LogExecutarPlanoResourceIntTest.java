package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.LogExecutarPlano;
import br.com.rogrs.autobot.repository.LogExecutarPlanoRepository;
import br.com.rogrs.autobot.repository.search.LogExecutarPlanoSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rogrs.autobot.domain.enumeration.Status;
/**
 * Test class for the LogExecutarPlanoResource REST controller.
 *
 * @see LogExecutarPlanoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class LogExecutarPlanoResourceIntTest {

    private static final LocalDate DEFAULT_CRIADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRIADO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINALIZADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINALIZADO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_MENSAGEM = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.SUCESSO;
    private static final Status UPDATED_STATUS = Status.FALHA;

    @Inject
    private LogExecutarPlanoRepository logExecutarPlanoRepository;

    @Inject
    private LogExecutarPlanoSearchRepository logExecutarPlanoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLogExecutarPlanoMockMvc;

    private LogExecutarPlano logExecutarPlano;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LogExecutarPlanoResource logExecutarPlanoResource = new LogExecutarPlanoResource();
        ReflectionTestUtils.setField(logExecutarPlanoResource, "logExecutarPlanoSearchRepository", logExecutarPlanoSearchRepository);
        ReflectionTestUtils.setField(logExecutarPlanoResource, "logExecutarPlanoRepository", logExecutarPlanoRepository);
        this.restLogExecutarPlanoMockMvc = MockMvcBuilders.standaloneSetup(logExecutarPlanoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogExecutarPlano createEntity(EntityManager em) {
        LogExecutarPlano logExecutarPlano = new LogExecutarPlano()
                .criado(DEFAULT_CRIADO)
                .finalizado(DEFAULT_FINALIZADO)
                .mensagem(DEFAULT_MENSAGEM)
                .status(DEFAULT_STATUS);
        return logExecutarPlano;
    }

    @Before
    public void initTest() {
        logExecutarPlanoSearchRepository.deleteAll();
        logExecutarPlano = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogExecutarPlano() throws Exception {
        int databaseSizeBeforeCreate = logExecutarPlanoRepository.findAll().size();

        // Create the LogExecutarPlano

        restLogExecutarPlanoMockMvc.perform(post("/api/log-executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logExecutarPlano)))
            .andExpect(status().isCreated());

        // Validate the LogExecutarPlano in the database
        List<LogExecutarPlano> logExecutarPlanoList = logExecutarPlanoRepository.findAll();
        assertThat(logExecutarPlanoList).hasSize(databaseSizeBeforeCreate + 1);
        LogExecutarPlano testLogExecutarPlano = logExecutarPlanoList.get(logExecutarPlanoList.size() - 1);
        assertThat(testLogExecutarPlano.getCriado()).isEqualTo(DEFAULT_CRIADO);
        assertThat(testLogExecutarPlano.getFinalizado()).isEqualTo(DEFAULT_FINALIZADO);
        assertThat(testLogExecutarPlano.getMensagem()).isEqualTo(DEFAULT_MENSAGEM);
        assertThat(testLogExecutarPlano.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the LogExecutarPlano in ElasticSearch
        LogExecutarPlano logExecutarPlanoEs = logExecutarPlanoSearchRepository.findOne(testLogExecutarPlano.getId());
        assertThat(logExecutarPlanoEs).isEqualToComparingFieldByField(testLogExecutarPlano);
    }

    @Test
    @Transactional
    public void createLogExecutarPlanoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logExecutarPlanoRepository.findAll().size();

        // Create the LogExecutarPlano with an existing ID
        LogExecutarPlano existingLogExecutarPlano = new LogExecutarPlano();
        existingLogExecutarPlano.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogExecutarPlanoMockMvc.perform(post("/api/log-executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLogExecutarPlano)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LogExecutarPlano> logExecutarPlanoList = logExecutarPlanoRepository.findAll();
        assertThat(logExecutarPlanoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = logExecutarPlanoRepository.findAll().size();
        // set the field null
        logExecutarPlano.setCriado(null);

        // Create the LogExecutarPlano, which fails.

        restLogExecutarPlanoMockMvc.perform(post("/api/log-executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logExecutarPlano)))
            .andExpect(status().isBadRequest());

        List<LogExecutarPlano> logExecutarPlanoList = logExecutarPlanoRepository.findAll();
        assertThat(logExecutarPlanoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogExecutarPlanos() throws Exception {
        // Initialize the database
        logExecutarPlanoRepository.saveAndFlush(logExecutarPlano);

        // Get all the logExecutarPlanoList
        restLogExecutarPlanoMockMvc.perform(get("/api/log-executar-planos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logExecutarPlano.getId().intValue())))
            .andExpect(jsonPath("$.[*].criado").value(hasItem(DEFAULT_CRIADO.toString())))
            .andExpect(jsonPath("$.[*].finalizado").value(hasItem(DEFAULT_FINALIZADO.toString())))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getLogExecutarPlano() throws Exception {
        // Initialize the database
        logExecutarPlanoRepository.saveAndFlush(logExecutarPlano);

        // Get the logExecutarPlano
        restLogExecutarPlanoMockMvc.perform(get("/api/log-executar-planos/{id}", logExecutarPlano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logExecutarPlano.getId().intValue()))
            .andExpect(jsonPath("$.criado").value(DEFAULT_CRIADO.toString()))
            .andExpect(jsonPath("$.finalizado").value(DEFAULT_FINALIZADO.toString()))
            .andExpect(jsonPath("$.mensagem").value(DEFAULT_MENSAGEM.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogExecutarPlano() throws Exception {
        // Get the logExecutarPlano
        restLogExecutarPlanoMockMvc.perform(get("/api/log-executar-planos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogExecutarPlano() throws Exception {
        // Initialize the database
        logExecutarPlanoRepository.saveAndFlush(logExecutarPlano);
        logExecutarPlanoSearchRepository.save(logExecutarPlano);
        int databaseSizeBeforeUpdate = logExecutarPlanoRepository.findAll().size();

        // Update the logExecutarPlano
        LogExecutarPlano updatedLogExecutarPlano = logExecutarPlanoRepository.findOne(logExecutarPlano.getId());
        updatedLogExecutarPlano
                .criado(UPDATED_CRIADO)
                .finalizado(UPDATED_FINALIZADO)
                .mensagem(UPDATED_MENSAGEM)
                .status(UPDATED_STATUS);

        restLogExecutarPlanoMockMvc.perform(put("/api/log-executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogExecutarPlano)))
            .andExpect(status().isOk());

        // Validate the LogExecutarPlano in the database
        List<LogExecutarPlano> logExecutarPlanoList = logExecutarPlanoRepository.findAll();
        assertThat(logExecutarPlanoList).hasSize(databaseSizeBeforeUpdate);
        LogExecutarPlano testLogExecutarPlano = logExecutarPlanoList.get(logExecutarPlanoList.size() - 1);
        assertThat(testLogExecutarPlano.getCriado()).isEqualTo(UPDATED_CRIADO);
        assertThat(testLogExecutarPlano.getFinalizado()).isEqualTo(UPDATED_FINALIZADO);
        assertThat(testLogExecutarPlano.getMensagem()).isEqualTo(UPDATED_MENSAGEM);
        assertThat(testLogExecutarPlano.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the LogExecutarPlano in ElasticSearch
        LogExecutarPlano logExecutarPlanoEs = logExecutarPlanoSearchRepository.findOne(testLogExecutarPlano.getId());
        assertThat(logExecutarPlanoEs).isEqualToComparingFieldByField(testLogExecutarPlano);
    }

    @Test
    @Transactional
    public void updateNonExistingLogExecutarPlano() throws Exception {
        int databaseSizeBeforeUpdate = logExecutarPlanoRepository.findAll().size();

        // Create the LogExecutarPlano

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogExecutarPlanoMockMvc.perform(put("/api/log-executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logExecutarPlano)))
            .andExpect(status().isCreated());

        // Validate the LogExecutarPlano in the database
        List<LogExecutarPlano> logExecutarPlanoList = logExecutarPlanoRepository.findAll();
        assertThat(logExecutarPlanoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogExecutarPlano() throws Exception {
        // Initialize the database
        logExecutarPlanoRepository.saveAndFlush(logExecutarPlano);
        logExecutarPlanoSearchRepository.save(logExecutarPlano);
        int databaseSizeBeforeDelete = logExecutarPlanoRepository.findAll().size();

        // Get the logExecutarPlano
        restLogExecutarPlanoMockMvc.perform(delete("/api/log-executar-planos/{id}", logExecutarPlano.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean logExecutarPlanoExistsInEs = logExecutarPlanoSearchRepository.exists(logExecutarPlano.getId());
        assertThat(logExecutarPlanoExistsInEs).isFalse();

        // Validate the database is empty
        List<LogExecutarPlano> logExecutarPlanoList = logExecutarPlanoRepository.findAll();
        assertThat(logExecutarPlanoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLogExecutarPlano() throws Exception {
        // Initialize the database
        logExecutarPlanoRepository.saveAndFlush(logExecutarPlano);
        logExecutarPlanoSearchRepository.save(logExecutarPlano);

        // Search the logExecutarPlano
        restLogExecutarPlanoMockMvc.perform(get("/api/_search/log-executar-planos?query=id:" + logExecutarPlano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logExecutarPlano.getId().intValue())))
            .andExpect(jsonPath("$.[*].criado").value(hasItem(DEFAULT_CRIADO.toString())))
            .andExpect(jsonPath("$.[*].finalizado").value(hasItem(DEFAULT_FINALIZADO.toString())))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
