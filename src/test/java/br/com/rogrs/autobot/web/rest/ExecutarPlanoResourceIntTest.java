package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.ExecutarPlano;
import br.com.rogrs.autobot.repository.ExecutarPlanoRepository;
import br.com.rogrs.autobot.repository.search.ExecutarPlanoSearchRepository;

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

import br.com.rogrs.autobot.domain.enumeration.Status;
/**
 * Test class for the ExecutarPlanoResource REST controller.
 *
 * @see ExecutarPlanoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class ExecutarPlanoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES = "BBBBBBBBBB";

    private static final String DEFAULT_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_MENSAGEM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARAR_NA_FALHA = false;
    private static final Boolean UPDATED_PARAR_NA_FALHA = true;

    private static final Status DEFAULT_STATUS = Status.SUCESSO;
    private static final Status UPDATED_STATUS = Status.FALHA;

    @Inject
    private ExecutarPlanoRepository executarPlanoRepository;

    @Inject
    private ExecutarPlanoSearchRepository executarPlanoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExecutarPlanoMockMvc;

    private ExecutarPlano executarPlano;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExecutarPlanoResource executarPlanoResource = new ExecutarPlanoResource();
        ReflectionTestUtils.setField(executarPlanoResource, "executarPlanoSearchRepository", executarPlanoSearchRepository);
        ReflectionTestUtils.setField(executarPlanoResource, "executarPlanoRepository", executarPlanoRepository);
        this.restExecutarPlanoMockMvc = MockMvcBuilders.standaloneSetup(executarPlanoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExecutarPlano createEntity(EntityManager em) {
        ExecutarPlano executarPlano = new ExecutarPlano()
                .descricao(DEFAULT_DESCRICAO)
                .detalhes(DEFAULT_DETALHES)
                .mensagem(DEFAULT_MENSAGEM)
                .pararNaFalha(DEFAULT_PARAR_NA_FALHA)
                .status(DEFAULT_STATUS);
        return executarPlano;
    }

    @Before
    public void initTest() {
        executarPlanoSearchRepository.deleteAll();
        executarPlano = createEntity(em);
    }

    @Test
    @Transactional
    public void createExecutarPlano() throws Exception {
        int databaseSizeBeforeCreate = executarPlanoRepository.findAll().size();

        // Create the ExecutarPlano

        restExecutarPlanoMockMvc.perform(post("/api/executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(executarPlano)))
            .andExpect(status().isCreated());

        // Validate the ExecutarPlano in the database
        List<ExecutarPlano> executarPlanoList = executarPlanoRepository.findAll();
        assertThat(executarPlanoList).hasSize(databaseSizeBeforeCreate + 1);
        ExecutarPlano testExecutarPlano = executarPlanoList.get(executarPlanoList.size() - 1);
        assertThat(testExecutarPlano.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testExecutarPlano.getDetalhes()).isEqualTo(DEFAULT_DETALHES);
        assertThat(testExecutarPlano.getMensagem()).isEqualTo(DEFAULT_MENSAGEM);
        assertThat(testExecutarPlano.isPararNaFalha()).isEqualTo(DEFAULT_PARAR_NA_FALHA);
        assertThat(testExecutarPlano.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ExecutarPlano in ElasticSearch
        ExecutarPlano executarPlanoEs = executarPlanoSearchRepository.findOne(testExecutarPlano.getId());
        assertThat(executarPlanoEs).isEqualToComparingFieldByField(testExecutarPlano);
    }

    @Test
    @Transactional
    public void createExecutarPlanoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = executarPlanoRepository.findAll().size();

        // Create the ExecutarPlano with an existing ID
        ExecutarPlano existingExecutarPlano = new ExecutarPlano();
        existingExecutarPlano.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExecutarPlanoMockMvc.perform(post("/api/executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExecutarPlano)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExecutarPlano> executarPlanoList = executarPlanoRepository.findAll();
        assertThat(executarPlanoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = executarPlanoRepository.findAll().size();
        // set the field null
        executarPlano.setDescricao(null);

        // Create the ExecutarPlano, which fails.

        restExecutarPlanoMockMvc.perform(post("/api/executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(executarPlano)))
            .andExpect(status().isBadRequest());

        List<ExecutarPlano> executarPlanoList = executarPlanoRepository.findAll();
        assertThat(executarPlanoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExecutarPlanos() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);

        // Get all the executarPlanoList
        restExecutarPlanoMockMvc.perform(get("/api/executar-planos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(executarPlano.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM.toString())))
            .andExpect(jsonPath("$.[*].pararNaFalha").value(hasItem(DEFAULT_PARAR_NA_FALHA.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);

        // Get the executarPlano
        restExecutarPlanoMockMvc.perform(get("/api/executar-planos/{id}", executarPlano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(executarPlano.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.detalhes").value(DEFAULT_DETALHES.toString()))
            .andExpect(jsonPath("$.mensagem").value(DEFAULT_MENSAGEM.toString()))
            .andExpect(jsonPath("$.pararNaFalha").value(DEFAULT_PARAR_NA_FALHA.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExecutarPlano() throws Exception {
        // Get the executarPlano
        restExecutarPlanoMockMvc.perform(get("/api/executar-planos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);
        executarPlanoSearchRepository.save(executarPlano);
        int databaseSizeBeforeUpdate = executarPlanoRepository.findAll().size();

        // Update the executarPlano
        ExecutarPlano updatedExecutarPlano = executarPlanoRepository.findOne(executarPlano.getId());
        updatedExecutarPlano
                .descricao(UPDATED_DESCRICAO)
                .detalhes(UPDATED_DETALHES)
                .mensagem(UPDATED_MENSAGEM)
                .pararNaFalha(UPDATED_PARAR_NA_FALHA)
                .status(UPDATED_STATUS);

        restExecutarPlanoMockMvc.perform(put("/api/executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExecutarPlano)))
            .andExpect(status().isOk());

        // Validate the ExecutarPlano in the database
        List<ExecutarPlano> executarPlanoList = executarPlanoRepository.findAll();
        assertThat(executarPlanoList).hasSize(databaseSizeBeforeUpdate);
        ExecutarPlano testExecutarPlano = executarPlanoList.get(executarPlanoList.size() - 1);
        assertThat(testExecutarPlano.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testExecutarPlano.getDetalhes()).isEqualTo(UPDATED_DETALHES);
        assertThat(testExecutarPlano.getMensagem()).isEqualTo(UPDATED_MENSAGEM);
        assertThat(testExecutarPlano.isPararNaFalha()).isEqualTo(UPDATED_PARAR_NA_FALHA);
        assertThat(testExecutarPlano.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ExecutarPlano in ElasticSearch
        ExecutarPlano executarPlanoEs = executarPlanoSearchRepository.findOne(testExecutarPlano.getId());
        assertThat(executarPlanoEs).isEqualToComparingFieldByField(testExecutarPlano);
    }

    @Test
    @Transactional
    public void updateNonExistingExecutarPlano() throws Exception {
        int databaseSizeBeforeUpdate = executarPlanoRepository.findAll().size();

        // Create the ExecutarPlano

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExecutarPlanoMockMvc.perform(put("/api/executar-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(executarPlano)))
            .andExpect(status().isCreated());

        // Validate the ExecutarPlano in the database
        List<ExecutarPlano> executarPlanoList = executarPlanoRepository.findAll();
        assertThat(executarPlanoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);
        executarPlanoSearchRepository.save(executarPlano);
        int databaseSizeBeforeDelete = executarPlanoRepository.findAll().size();

        // Get the executarPlano
        restExecutarPlanoMockMvc.perform(delete("/api/executar-planos/{id}", executarPlano.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean executarPlanoExistsInEs = executarPlanoSearchRepository.exists(executarPlano.getId());
        assertThat(executarPlanoExistsInEs).isFalse();

        // Validate the database is empty
        List<ExecutarPlano> executarPlanoList = executarPlanoRepository.findAll();
        assertThat(executarPlanoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExecutarPlano() throws Exception {
        // Initialize the database
        executarPlanoRepository.saveAndFlush(executarPlano);
        executarPlanoSearchRepository.save(executarPlano);

        // Search the executarPlano
        restExecutarPlanoMockMvc.perform(get("/api/_search/executar-planos?query=id:" + executarPlano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(executarPlano.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM.toString())))
            .andExpect(jsonPath("$.[*].pararNaFalha").value(hasItem(DEFAULT_PARAR_NA_FALHA.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
