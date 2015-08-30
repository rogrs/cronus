package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Script;
import com.mycompany.myapp.repository.ScriptRepository;


/**
 * Test class for the ScriptResource REST controller.
 *
 * @see ScriptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ScriptResourceTest {

    private static final String DEFAULT_DESCRICAO = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRICAO = "UPDATED_TEXT";
    private static final String DEFAULT_DETALHE = "SAMPLE_TEXT";
    private static final String UPDATED_DETALHE = "UPDATED_TEXT";

    @Inject
    private ScriptRepository scriptRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restScriptMockMvc;

    private Script script;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScriptResource scriptResource = new ScriptResource();
        ReflectionTestUtils.setField(scriptResource, "scriptRepository", scriptRepository);
        this.restScriptMockMvc = MockMvcBuilders.standaloneSetup(scriptResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        script = new Script();
        script.setDescricao(DEFAULT_DESCRICAO);
        script.setDetalhe(DEFAULT_DETALHE);
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
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeCreate + 1);
        Script testScript = scripts.get(scripts.size() - 1);
        assertThat(testScript.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testScript.getDetalhe()).isEqualTo(DEFAULT_DETALHE);
    }

    @Test
    @Transactional
    public void getAllScripts() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

        // Get all the scripts
        restScriptMockMvc.perform(get("/api/scripts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(script.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].detalhe").value(hasItem(DEFAULT_DETALHE.toString())));
    }

    @Test
    @Transactional
    public void getScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

        // Get the script
        restScriptMockMvc.perform(get("/api/scripts/{id}", script.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(script.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.detalhe").value(DEFAULT_DETALHE.toString()));
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

		int databaseSizeBeforeUpdate = scriptRepository.findAll().size();

        // Update the script
        script.setDescricao(UPDATED_DESCRICAO);
        script.setDetalhe(UPDATED_DETALHE);
        

        restScriptMockMvc.perform(put("/api/scripts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(script)))
                .andExpect(status().isOk());

        // Validate the Script in the database
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeUpdate);
        Script testScript = scripts.get(scripts.size() - 1);
        assertThat(testScript.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testScript.getDetalhe()).isEqualTo(UPDATED_DETALHE);
    }

    @Test
    @Transactional
    public void deleteScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

		int databaseSizeBeforeDelete = scriptRepository.findAll().size();

        // Get the script
        restScriptMockMvc.perform(delete("/api/scripts/{id}", script.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
