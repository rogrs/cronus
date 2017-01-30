package br.com.rogrs.autobot.web.rest;

import br.com.rogrs.autobot.AutobotApp;

import br.com.rogrs.autobot.domain.Plugin;
import br.com.rogrs.autobot.repository.PluginRepository;
import br.com.rogrs.autobot.repository.search.PluginSearchRepository;

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
 * Test class for the PluginResource REST controller.
 *
 * @see PluginResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutobotApp.class)
public class PluginResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COMANDO = "AAAAAAAAAA";
    private static final String UPDATED_COMANDO = "BBBBBBBBBB";

    @Inject
    private PluginRepository pluginRepository;

    @Inject
    private PluginSearchRepository pluginSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPluginMockMvc;

    private Plugin plugin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PluginResource pluginResource = new PluginResource();
        ReflectionTestUtils.setField(pluginResource, "pluginSearchRepository", pluginSearchRepository);
        ReflectionTestUtils.setField(pluginResource, "pluginRepository", pluginRepository);
        this.restPluginMockMvc = MockMvcBuilders.standaloneSetup(pluginResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plugin createEntity(EntityManager em) {
        Plugin plugin = new Plugin()
                .nome(DEFAULT_NOME)
                .comando(DEFAULT_COMANDO);
        return plugin;
    }

    @Before
    public void initTest() {
        pluginSearchRepository.deleteAll();
        plugin = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlugin() throws Exception {
        int databaseSizeBeforeCreate = pluginRepository.findAll().size();

        // Create the Plugin

        restPluginMockMvc.perform(post("/api/plugins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plugin)))
            .andExpect(status().isCreated());

        // Validate the Plugin in the database
        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeCreate + 1);
        Plugin testPlugin = pluginList.get(pluginList.size() - 1);
        assertThat(testPlugin.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPlugin.getComando()).isEqualTo(DEFAULT_COMANDO);

        // Validate the Plugin in ElasticSearch
        Plugin pluginEs = pluginSearchRepository.findOne(testPlugin.getId());
        assertThat(pluginEs).isEqualToComparingFieldByField(testPlugin);
    }

    @Test
    @Transactional
    public void createPluginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pluginRepository.findAll().size();

        // Create the Plugin with an existing ID
        Plugin existingPlugin = new Plugin();
        existingPlugin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPluginMockMvc.perform(post("/api/plugins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPlugin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pluginRepository.findAll().size();
        // set the field null
        plugin.setNome(null);

        // Create the Plugin, which fails.

        restPluginMockMvc.perform(post("/api/plugins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plugin)))
            .andExpect(status().isBadRequest());

        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComandoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pluginRepository.findAll().size();
        // set the field null
        plugin.setComando(null);

        // Create the Plugin, which fails.

        restPluginMockMvc.perform(post("/api/plugins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plugin)))
            .andExpect(status().isBadRequest());

        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlugins() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);

        // Get all the pluginList
        restPluginMockMvc.perform(get("/api/plugins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plugin.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].comando").value(hasItem(DEFAULT_COMANDO.toString())));
    }

    @Test
    @Transactional
    public void getPlugin() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);

        // Get the plugin
        restPluginMockMvc.perform(get("/api/plugins/{id}", plugin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plugin.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.comando").value(DEFAULT_COMANDO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlugin() throws Exception {
        // Get the plugin
        restPluginMockMvc.perform(get("/api/plugins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlugin() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);
        pluginSearchRepository.save(plugin);
        int databaseSizeBeforeUpdate = pluginRepository.findAll().size();

        // Update the plugin
        Plugin updatedPlugin = pluginRepository.findOne(plugin.getId());
        updatedPlugin
                .nome(UPDATED_NOME)
                .comando(UPDATED_COMANDO);

        restPluginMockMvc.perform(put("/api/plugins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlugin)))
            .andExpect(status().isOk());

        // Validate the Plugin in the database
        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeUpdate);
        Plugin testPlugin = pluginList.get(pluginList.size() - 1);
        assertThat(testPlugin.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPlugin.getComando()).isEqualTo(UPDATED_COMANDO);

        // Validate the Plugin in ElasticSearch
        Plugin pluginEs = pluginSearchRepository.findOne(testPlugin.getId());
        assertThat(pluginEs).isEqualToComparingFieldByField(testPlugin);
    }

    @Test
    @Transactional
    public void updateNonExistingPlugin() throws Exception {
        int databaseSizeBeforeUpdate = pluginRepository.findAll().size();

        // Create the Plugin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPluginMockMvc.perform(put("/api/plugins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plugin)))
            .andExpect(status().isCreated());

        // Validate the Plugin in the database
        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlugin() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);
        pluginSearchRepository.save(plugin);
        int databaseSizeBeforeDelete = pluginRepository.findAll().size();

        // Get the plugin
        restPluginMockMvc.perform(delete("/api/plugins/{id}", plugin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean pluginExistsInEs = pluginSearchRepository.exists(plugin.getId());
        assertThat(pluginExistsInEs).isFalse();

        // Validate the database is empty
        List<Plugin> pluginList = pluginRepository.findAll();
        assertThat(pluginList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPlugin() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);
        pluginSearchRepository.save(plugin);

        // Search the plugin
        restPluginMockMvc.perform(get("/api/_search/plugins?query=id:" + plugin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plugin.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].comando").value(hasItem(DEFAULT_COMANDO.toString())));
    }
}
