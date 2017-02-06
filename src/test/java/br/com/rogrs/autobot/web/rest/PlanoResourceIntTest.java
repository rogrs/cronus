package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.Plano;
import br.com.rogrs.autobot.repository.PlanoRepository;
import br.com.rogrs.autobot.repository.search.PlanoSearchRepository;

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

import br.com.rogrs.autobot.domain.enumeration.TipoPlano;
/**
 * Test class for the PlanoResource REST controller.
 *
 * @see PlanoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class PlanoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES = "BBBBBBBBBB";

    private static final TipoPlano DEFAULT_TIPO = TipoPlano.UNITARIO;
    private static final TipoPlano UPDATED_TIPO = TipoPlano.SEGURANCA;

    @Inject
    private PlanoRepository planoRepository;

    @Inject
    private PlanoSearchRepository planoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPlanoMockMvc;

    private Plano plano;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanoResource planoResource = new PlanoResource();
        ReflectionTestUtils.setField(planoResource, "planoSearchRepository", planoSearchRepository);
        ReflectionTestUtils.setField(planoResource, "planoRepository", planoRepository);
        this.restPlanoMockMvc = MockMvcBuilders.standaloneSetup(planoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plano createEntity(EntityManager em) {
        Plano plano = new Plano()
                .descricao(DEFAULT_DESCRICAO)
                .detalhes(DEFAULT_DETALHES)
                .tipo(DEFAULT_TIPO);
        return plano;
    }

    @Before
    public void initTest() {
        planoSearchRepository.deleteAll();
        plano = createEntity(em);
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
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeCreate + 1);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlano.getDetalhes()).isEqualTo(DEFAULT_DETALHES);
        assertThat(testPlano.getTipo()).isEqualTo(DEFAULT_TIPO);

        // Validate the Plano in ElasticSearch
        Plano planoEs = planoSearchRepository.findOne(testPlano.getId());
        assertThat(planoEs).isEqualToComparingFieldByField(testPlano);
    }

    @Test
    @Transactional
    public void createPlanoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRepository.findAll().size();

        // Create the Plano with an existing ID
        Plano existingPlano = new Plano();
        existingPlano.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPlano)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRepository.findAll().size();
        // set the field null
        plano.setDescricao(null);

        // Create the Plano, which fails.

        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isBadRequest());

        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanos() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList
        restPlanoMockMvc.perform(get("/api/planos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plano.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getPlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get the plano
        restPlanoMockMvc.perform(get("/api/planos/{id}", plano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plano.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.detalhes").value(DEFAULT_DETALHES.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
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
        planoSearchRepository.save(plano);
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Update the plano
        Plano updatedPlano = planoRepository.findOne(plano.getId());
        updatedPlano
                .descricao(UPDATED_DESCRICAO)
                .detalhes(UPDATED_DETALHES)
                .tipo(UPDATED_TIPO);

        restPlanoMockMvc.perform(put("/api/planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlano)))
            .andExpect(status().isOk());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlano.getDetalhes()).isEqualTo(UPDATED_DETALHES);
        assertThat(testPlano.getTipo()).isEqualTo(UPDATED_TIPO);

        // Validate the Plano in ElasticSearch
        Plano planoEs = planoSearchRepository.findOne(testPlano.getId());
        assertThat(planoEs).isEqualToComparingFieldByField(testPlano);
    }

    @Test
    @Transactional
    public void updateNonExistingPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Create the Plano

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanoMockMvc.perform(put("/api/planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isCreated());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);
        planoSearchRepository.save(plano);
        int databaseSizeBeforeDelete = planoRepository.findAll().size();

        // Get the plano
        restPlanoMockMvc.perform(delete("/api/planos/{id}", plano.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean planoExistsInEs = planoSearchRepository.exists(plano.getId());
        assertThat(planoExistsInEs).isFalse();

        // Validate the database is empty
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);
        planoSearchRepository.save(plano);

        // Search the plano
        restPlanoMockMvc.perform(get("/api/_search/planos?query=id:" + plano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plano.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
}
