package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.LogExecute;
import com.mycompany.myapp.repository.LogExecuteRepository;

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
 * Test class for the LogExecuteResource REST controller.
 *
 * @see LogExecuteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LogExecuteResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_CREATED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.print(DEFAULT_CREATED);
    private static final String DEFAULT_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_MESSAGE = "UPDATED_TEXT";
    private static final String DEFAULT_HOSTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_HOSTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_LOGIN = "SAMPLE_TEXT";
    private static final String UPDATED_LOGIN = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";

    @Inject
    private LogExecuteRepository logExecuteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restLogExecuteMockMvc;

    private LogExecute logExecute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LogExecuteResource logExecuteResource = new LogExecuteResource();
        ReflectionTestUtils.setField(logExecuteResource, "logExecuteRepository", logExecuteRepository);
        this.restLogExecuteMockMvc = MockMvcBuilders.standaloneSetup(logExecuteResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        logExecute = new LogExecute();
        logExecute.setCreated(DEFAULT_CREATED);
        logExecute.setMessage(DEFAULT_MESSAGE);
        logExecute.setHostname(DEFAULT_HOSTNAME);
        logExecute.setLogin(DEFAULT_LOGIN);
        logExecute.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createLogExecute() throws Exception {
        int databaseSizeBeforeCreate = logExecuteRepository.findAll().size();

        // Create the LogExecute

        restLogExecuteMockMvc.perform(post("/api/logExecutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(logExecute)))
                .andExpect(status().isCreated());

        // Validate the LogExecute in the database
        List<LogExecute> logExecutes = logExecuteRepository.findAll();
        assertThat(logExecutes).hasSize(databaseSizeBeforeCreate + 1);
        LogExecute testLogExecute = logExecutes.get(logExecutes.size() - 1);
        assertThat(testLogExecute.getCreated().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATED);
        assertThat(testLogExecute.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testLogExecute.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testLogExecute.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testLogExecute.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllLogExecutes() throws Exception {
        // Initialize the database
        logExecuteRepository.saveAndFlush(logExecute);

        // Get all the logExecutes
        restLogExecuteMockMvc.perform(get("/api/logExecutes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(logExecute.getId().intValue())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME.toString())))
                .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getLogExecute() throws Exception {
        // Initialize the database
        logExecuteRepository.saveAndFlush(logExecute);

        // Get the logExecute
        restLogExecuteMockMvc.perform(get("/api/logExecutes/{id}", logExecute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(logExecute.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogExecute() throws Exception {
        // Get the logExecute
        restLogExecuteMockMvc.perform(get("/api/logExecutes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogExecute() throws Exception {
        // Initialize the database
        logExecuteRepository.saveAndFlush(logExecute);

		int databaseSizeBeforeUpdate = logExecuteRepository.findAll().size();

        // Update the logExecute
        logExecute.setCreated(UPDATED_CREATED);
        logExecute.setMessage(UPDATED_MESSAGE);
        logExecute.setHostname(UPDATED_HOSTNAME);
        logExecute.setLogin(UPDATED_LOGIN);
        logExecute.setStatus(UPDATED_STATUS);
        

        restLogExecuteMockMvc.perform(put("/api/logExecutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(logExecute)))
                .andExpect(status().isOk());

        // Validate the LogExecute in the database
        List<LogExecute> logExecutes = logExecuteRepository.findAll();
        assertThat(logExecutes).hasSize(databaseSizeBeforeUpdate);
        LogExecute testLogExecute = logExecutes.get(logExecutes.size() - 1);
        assertThat(testLogExecute.getCreated().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATED);
        assertThat(testLogExecute.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testLogExecute.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testLogExecute.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testLogExecute.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteLogExecute() throws Exception {
        // Initialize the database
        logExecuteRepository.saveAndFlush(logExecute);

		int databaseSizeBeforeDelete = logExecuteRepository.findAll().size();

        // Get the logExecute
        restLogExecuteMockMvc.perform(delete("/api/logExecutes/{id}", logExecute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LogExecute> logExecutes = logExecuteRepository.findAll();
        assertThat(logExecutes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
