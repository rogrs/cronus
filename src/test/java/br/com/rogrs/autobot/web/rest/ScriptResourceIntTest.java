package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.Script;
import br.com.rogrs.autobot.repository.ScriptRepository;
import br.com.rogrs.autobot.repository.search.ScriptSearchRepository;

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
 * Test class for the ScriptResource REST controller.
 *
 * @see ScriptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class ScriptResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Inject
    private ScriptRepository scriptRepository;

    @Inject
    private ScriptSearchRepository scriptSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restScriptMockMvc;

    private Script script;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScriptResource scriptResource = new ScriptResource();
        ReflectionTestUtils.setField(scriptResource, "scriptSearchRepository", scriptSearchRepository);
        ReflectionTestUtils.setField(scriptResource, "scriptRepository", scriptRepository);
        this.restScriptMockMvc = MockMvcBuilders.standaloneSetup(scriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Script createEntity(EntityManager em) {
        Script script = new Script()
                .descricao(DEFAULT_DESCRICAO)
                .path(DEFAULT_PATH);
        return script;
    }

    @Before
    public void initTest() {
        scriptSearchRepository.deleteAll();
        script = createEntity(em);
    }

    @Test
    @Transactional
    public void createScript() throws Exception {
        int databaseSizeBeforeCreate = scriptRepository.findAll().size();

        // Create the Script

        restScriptMockMvc.perform(post("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(script)))
            .andExpect(status().isCreated());

        // Validate the Script in the database
        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeCreate + 1);
        Script testScript = scriptList.get(scriptList.size() - 1);
        assertThat(testScript.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testScript.getPath()).isEqualTo(DEFAULT_PATH);

        // Validate the Script in ElasticSearch
        Script scriptEs = scriptSearchRepository.findOne(testScript.getId());
        assertThat(scriptEs).isEqualToComparingFieldByField(testScript);
    }

    @Test
    @Transactional
    public void createScriptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scriptRepository.findAll().size();

        // Create the Script with an existing ID
        Script existingScript = new Script();
        existingScript.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScriptMockMvc.perform(post("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingScript)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = scriptRepository.findAll().size();
        // set the field null
        script.setDescricao(null);

        // Create the Script, which fails.

        restScriptMockMvc.perform(post("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(script)))
            .andExpect(status().isBadRequest());

        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = scriptRepository.findAll().size();
        // set the field null
        script.setPath(null);

        // Create the Script, which fails.

        restScriptMockMvc.perform(post("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(script)))
            .andExpect(status().isBadRequest());

        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScripts() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

        // Get all the scriptList
        restScriptMockMvc.perform(get("/api/scripts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(script.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }

    @Test
    @Transactional
    public void getScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

        // Get the script
        restScriptMockMvc.perform(get("/api/scripts/{id}", script.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(script.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScript() throws Exception {
        // Get the script
        restScriptMockMvc.perform(get("/api/scripts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);
        scriptSearchRepository.save(script);
        int databaseSizeBeforeUpdate = scriptRepository.findAll().size();

        // Update the script
        Script updatedScript = scriptRepository.findOne(script.getId());
        updatedScript
                .descricao(UPDATED_DESCRICAO)
                .path(UPDATED_PATH);

        restScriptMockMvc.perform(put("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScript)))
            .andExpect(status().isOk());

        // Validate the Script in the database
        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeUpdate);
        Script testScript = scriptList.get(scriptList.size() - 1);
        assertThat(testScript.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testScript.getPath()).isEqualTo(UPDATED_PATH);

        // Validate the Script in ElasticSearch
        Script scriptEs = scriptSearchRepository.findOne(testScript.getId());
        assertThat(scriptEs).isEqualToComparingFieldByField(testScript);
    }

    @Test
    @Transactional
    public void updateNonExistingScript() throws Exception {
        int databaseSizeBeforeUpdate = scriptRepository.findAll().size();

        // Create the Script

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScriptMockMvc.perform(put("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(script)))
            .andExpect(status().isCreated());

        // Validate the Script in the database
        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);
        scriptSearchRepository.save(script);
        int databaseSizeBeforeDelete = scriptRepository.findAll().size();

        // Get the script
        restScriptMockMvc.perform(delete("/api/scripts/{id}", script.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean scriptExistsInEs = scriptSearchRepository.exists(script.getId());
        assertThat(scriptExistsInEs).isFalse();

        // Validate the database is empty
        List<Script> scriptList = scriptRepository.findAll();
        assertThat(scriptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);
        scriptSearchRepository.save(script);

        // Search the script
        restScriptMockMvc.perform(get("/api/_search/scripts?query=id:" + script.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(script.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }
}
