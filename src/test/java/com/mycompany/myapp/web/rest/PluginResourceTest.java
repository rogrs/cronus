package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Plugin;
import com.mycompany.myapp.repository.PluginRepository;

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
 * Test class for the PluginResource REST controller.
 *
 * @see PluginResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PluginResourceTest {

    private static final String DEFAULT_DESCRICAO = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRICAO = "UPDATED_TEXT";
    private static final String DEFAULT_COMANDO = "SAMPLE_TEXT";
    private static final String UPDATED_COMANDO = "UPDATED_TEXT";

    @Inject
    private PluginRepository pluginRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPluginMockMvc;

    private Plugin plugin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PluginResource pluginResource = new PluginResource();
        ReflectionTestUtils.setField(pluginResource, "pluginRepository", pluginRepository);
        this.restPluginMockMvc = MockMvcBuilders.standaloneSetup(pluginResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        plugin = new Plugin();
        plugin.setDescricao(DEFAULT_DESCRICAO);
        plugin.setComando(DEFAULT_COMANDO);
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
        List<Plugin> plugins = pluginRepository.findAll();
        assertThat(plugins).hasSize(databaseSizeBeforeCreate + 1);
        Plugin testPlugin = plugins.get(plugins.size() - 1);
        assertThat(testPlugin.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlugin.getComando()).isEqualTo(DEFAULT_COMANDO);
    }

    @Test
    @Transactional
    public void getAllPlugins() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);

        // Get all the plugins
        restPluginMockMvc.perform(get("/api/plugins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plugin.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plugin.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
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

		int databaseSizeBeforeUpdate = pluginRepository.findAll().size();

        // Update the plugin
        plugin.setDescricao(UPDATED_DESCRICAO);
        plugin.setComando(UPDATED_COMANDO);
        

        restPluginMockMvc.perform(put("/api/plugins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plugin)))
                .andExpect(status().isOk());

        // Validate the Plugin in the database
        List<Plugin> plugins = pluginRepository.findAll();
        assertThat(plugins).hasSize(databaseSizeBeforeUpdate);
        Plugin testPlugin = plugins.get(plugins.size() - 1);
        assertThat(testPlugin.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlugin.getComando()).isEqualTo(UPDATED_COMANDO);
    }

    @Test
    @Transactional
    public void deletePlugin() throws Exception {
        // Initialize the database
        pluginRepository.saveAndFlush(plugin);

		int databaseSizeBeforeDelete = pluginRepository.findAll().size();

        // Get the plugin
        restPluginMockMvc.perform(delete("/api/plugins/{id}", plugin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plugin> plugins = pluginRepository.findAll();
        assertThat(plugins).hasSize(databaseSizeBeforeDelete - 1);
    }
}
