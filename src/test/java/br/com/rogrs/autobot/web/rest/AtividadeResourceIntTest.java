package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.Atividade;
import br.com.rogrs.autobot.repository.AtividadeRepository;
import br.com.rogrs.autobot.repository.search.AtividadeSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AtividadeResource REST controller.
 *
 * @see AtividadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class AtividadeResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COMANDO = "AAAAAAAAAA";
    private static final String UPDATED_COMANDO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARAR_NA_FALHA = false;
    private static final Boolean UPDATED_PARAR_NA_FALHA = true;

    @Inject
    private AtividadeRepository atividadeRepository;

    @Inject
    private AtividadeSearchRepository atividadeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAtividadeMockMvc;

    private Atividade atividade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AtividadeResource atividadeResource = new AtividadeResource();
        ReflectionTestUtils.setField(atividadeResource, "atividadeSearchRepository", atividadeSearchRepository);
        ReflectionTestUtils.setField(atividadeResource, "atividadeRepository", atividadeRepository);
        this.restAtividadeMockMvc = MockMvcBuilders.standaloneSetup(atividadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atividade createEntity(EntityManager em) {
        Atividade atividade = new Atividade()
                .nome(DEFAULT_NOME)
                .comando(DEFAULT_COMANDO)
                .pararNaFalha(DEFAULT_PARAR_NA_FALHA);
        return atividade;
    }

    @Before
    public void initTest() {
        atividadeSearchRepository.deleteAll();
        atividade = createEntity(em);
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
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeCreate + 1);
        Atividade testAtividade = atividadeList.get(atividadeList.size() - 1);
        assertThat(testAtividade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAtividade.getComando()).isEqualTo(DEFAULT_COMANDO);
        assertThat(testAtividade.isPararNaFalha()).isEqualTo(DEFAULT_PARAR_NA_FALHA);

        // Validate the Atividade in ElasticSearch
        Atividade atividadeEs = atividadeSearchRepository.findOne(testAtividade.getId());
        assertThat(atividadeEs).isEqualToComparingFieldByField(testAtividade);
    }

    @Test
    @Transactional
    public void createAtividadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atividadeRepository.findAll().size();

        // Create the Atividade with an existing ID
        Atividade existingAtividade = new Atividade();
        existingAtividade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtividadeMockMvc.perform(post("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAtividade)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = atividadeRepository.findAll().size();
        // set the field null
        atividade.setNome(null);

        // Create the Atividade, which fails.

        restAtividadeMockMvc.perform(post("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividade)))
            .andExpect(status().isBadRequest());

        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComandoIsRequired() throws Exception {
        int databaseSizeBeforeTest = atividadeRepository.findAll().size();
        // set the field null
        atividade.setComando(null);

        // Create the Atividade, which fails.

        restAtividadeMockMvc.perform(post("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividade)))
            .andExpect(status().isBadRequest());

        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAtividades() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get all the atividadeList
        restAtividadeMockMvc.perform(get("/api/atividades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].comando").value(hasItem(DEFAULT_COMANDO.toString())))
            .andExpect(jsonPath("$.[*].pararNaFalha").value(hasItem(DEFAULT_PARAR_NA_FALHA.booleanValue())));
    }

    @Test
    @Transactional
    public void getAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);

        // Get the atividade
        restAtividadeMockMvc.perform(get("/api/atividades/{id}", atividade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atividade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.comando").value(DEFAULT_COMANDO.toString()))
            .andExpect(jsonPath("$.pararNaFalha").value(DEFAULT_PARAR_NA_FALHA.booleanValue()));
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
        atividadeSearchRepository.save(atividade);
        int databaseSizeBeforeUpdate = atividadeRepository.findAll().size();

        // Update the atividade
        Atividade updatedAtividade = atividadeRepository.findOne(atividade.getId());
        updatedAtividade
                .nome(UPDATED_NOME)
                .comando(UPDATED_COMANDO)
                .pararNaFalha(UPDATED_PARAR_NA_FALHA);

        restAtividadeMockMvc.perform(put("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAtividade)))
            .andExpect(status().isOk());

        // Validate the Atividade in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeUpdate);
        Atividade testAtividade = atividadeList.get(atividadeList.size() - 1);
        assertThat(testAtividade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAtividade.getComando()).isEqualTo(UPDATED_COMANDO);
        assertThat(testAtividade.isPararNaFalha()).isEqualTo(UPDATED_PARAR_NA_FALHA);

        // Validate the Atividade in ElasticSearch
        Atividade atividadeEs = atividadeSearchRepository.findOne(testAtividade.getId());
        assertThat(atividadeEs).isEqualToComparingFieldByField(testAtividade);
    }

    @Test
    @Transactional
    public void updateNonExistingAtividade() throws Exception {
        int databaseSizeBeforeUpdate = atividadeRepository.findAll().size();

        // Create the Atividade

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAtividadeMockMvc.perform(put("/api/atividades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atividade)))
            .andExpect(status().isCreated());

        // Validate the Atividade in the database
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);
        atividadeSearchRepository.save(atividade);
        int databaseSizeBeforeDelete = atividadeRepository.findAll().size();

        // Get the atividade
        restAtividadeMockMvc.perform(delete("/api/atividades/{id}", atividade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean atividadeExistsInEs = atividadeSearchRepository.exists(atividade.getId());
        assertThat(atividadeExistsInEs).isFalse();

        // Validate the database is empty
        List<Atividade> atividadeList = atividadeRepository.findAll();
        assertThat(atividadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAtividade() throws Exception {
        // Initialize the database
        atividadeRepository.saveAndFlush(atividade);
        atividadeSearchRepository.save(atividade);

        // Search the atividade
        restAtividadeMockMvc.perform(get("/api/_search/atividades?query=id:" + atividade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].comando").value(hasItem(DEFAULT_COMANDO.toString())))
            .andExpect(jsonPath("$.[*].pararNaFalha").value(hasItem(DEFAULT_PARAR_NA_FALHA.booleanValue())));
    }
}
