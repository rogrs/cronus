package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.Projeto;
import br.com.rogrs.autobot.repository.ProjetoRepository;
import br.com.rogrs.autobot.repository.search.ProjetoSearchRepository;

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
 * Test class for the ProjetoResource REST controller.
 *
 * @see ProjetoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class ProjetoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES = "BBBBBBBBBB";

    @Inject
    private ProjetoRepository projetoRepository;

    @Inject
    private ProjetoSearchRepository projetoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProjetoMockMvc;

    private Projeto projeto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjetoResource projetoResource = new ProjetoResource();
        ReflectionTestUtils.setField(projetoResource, "projetoSearchRepository", projetoSearchRepository);
        ReflectionTestUtils.setField(projetoResource, "projetoRepository", projetoRepository);
        this.restProjetoMockMvc = MockMvcBuilders.standaloneSetup(projetoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projeto createEntity(EntityManager em) {
        Projeto projeto = new Projeto()
                .descricao(DEFAULT_DESCRICAO)
                .detalhes(DEFAULT_DETALHES);
        return projeto;
    }

    @Before
    public void initTest() {
        projetoSearchRepository.deleteAll();
        projeto = createEntity(em);
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
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeCreate + 1);
        Projeto testProjeto = projetoList.get(projetoList.size() - 1);
        assertThat(testProjeto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProjeto.getDetalhes()).isEqualTo(DEFAULT_DETALHES);

        // Validate the Projeto in ElasticSearch
        Projeto projetoEs = projetoSearchRepository.findOne(testProjeto.getId());
        assertThat(projetoEs).isEqualToComparingFieldByField(testProjeto);
    }

    @Test
    @Transactional
    public void createProjetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projetoRepository.findAll().size();

        // Create the Projeto with an existing ID
        Projeto existingProjeto = new Projeto();
        existingProjeto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetoMockMvc.perform(post("/api/projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProjeto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetoRepository.findAll().size();
        // set the field null
        projeto.setDescricao(null);

        // Create the Projeto, which fails.

        restProjetoMockMvc.perform(post("/api/projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeto)))
            .andExpect(status().isBadRequest());

        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjetos() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        // Get all the projetoList
        restProjetoMockMvc.perform(get("/api/projetos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
        projetoSearchRepository.save(projeto);
        int databaseSizeBeforeUpdate = projetoRepository.findAll().size();

        // Update the projeto
        Projeto updatedProjeto = projetoRepository.findOne(projeto.getId());
        updatedProjeto
                .descricao(UPDATED_DESCRICAO)
                .detalhes(UPDATED_DETALHES);

        restProjetoMockMvc.perform(put("/api/projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjeto)))
            .andExpect(status().isOk());

        // Validate the Projeto in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeUpdate);
        Projeto testProjeto = projetoList.get(projetoList.size() - 1);
        assertThat(testProjeto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProjeto.getDetalhes()).isEqualTo(UPDATED_DETALHES);

        // Validate the Projeto in ElasticSearch
        Projeto projetoEs = projetoSearchRepository.findOne(testProjeto.getId());
        assertThat(projetoEs).isEqualToComparingFieldByField(testProjeto);
    }

    @Test
    @Transactional
    public void updateNonExistingProjeto() throws Exception {
        int databaseSizeBeforeUpdate = projetoRepository.findAll().size();

        // Create the Projeto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjetoMockMvc.perform(put("/api/projetos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeto)))
            .andExpect(status().isCreated());

        // Validate the Projeto in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);
        projetoSearchRepository.save(projeto);
        int databaseSizeBeforeDelete = projetoRepository.findAll().size();

        // Get the projeto
        restProjetoMockMvc.perform(delete("/api/projetos/{id}", projeto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean projetoExistsInEs = projetoSearchRepository.exists(projeto.getId());
        assertThat(projetoExistsInEs).isFalse();

        // Validate the database is empty
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);
        projetoSearchRepository.save(projeto);

        // Search the projeto
        restProjetoMockMvc.perform(get("/api/_search/projetos?query=id:" + projeto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projeto.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())));
    }
}
