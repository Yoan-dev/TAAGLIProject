package fr.istic.mitic.web.rest;

import fr.istic.mitic.TaagliProjectApp;

import fr.istic.mitic.domain.Filiere;
import fr.istic.mitic.domain.Enseignant;
import fr.istic.mitic.repository.FiliereRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FiliereResource REST controller.
 *
 * @see FiliereResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaagliProjectApp.class)

public class FiliereResourceIntTest {
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    @Inject
    private FiliereRepository filiereRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFiliereMockMvc;

    private Filiere filiere;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FiliereResource filiereResource = new FiliereResource();
        ReflectionTestUtils.setField(filiereResource, "filiereRepository", filiereRepository);
        this.restFiliereMockMvc = MockMvcBuilders.standaloneSetup(filiereResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filiere createEntity(EntityManager em) {
        Filiere filiere = new Filiere()
                .nom(DEFAULT_NOM);
        // Add required entity
        Enseignant enseignant = EnseignantResourceIntTest.createEntity(em);
        em.persist(enseignant);
        em.flush();
        filiere.setEnseignant(enseignant);
        return filiere;
    }

    @Before
    public void initTest() {
        filiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiliere() throws Exception {
        int databaseSizeBeforeCreate = filiereRepository.findAll().size();

        // Create the Filiere

        restFiliereMockMvc.perform(post("/api/filieres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filiere)))
                .andExpect(status().isCreated());

        // Validate the Filiere in the database
        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeCreate + 1);
        Filiere testFiliere = filieres.get(filieres.size() - 1);
        assertThat(testFiliere.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = filiereRepository.findAll().size();
        // set the field null
        filiere.setNom(null);

        // Create the Filiere, which fails.

        restFiliereMockMvc.perform(post("/api/filieres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filiere)))
                .andExpect(status().isBadRequest());

        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilieres() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        // Get all the filieres
        restFiliereMockMvc.perform(get("/api/filieres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(filiere.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        // Get the filiere
        restFiliereMockMvc.perform(get("/api/filieres/{id}", filiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filiere.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFiliere() throws Exception {
        // Get the filiere
        restFiliereMockMvc.perform(get("/api/filieres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);
        int databaseSizeBeforeUpdate = filiereRepository.findAll().size();

        // Update the filiere
        Filiere updatedFiliere = filiereRepository.findOne(filiere.getId());
        updatedFiliere
                .nom(UPDATED_NOM);

        restFiliereMockMvc.perform(put("/api/filieres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFiliere)))
                .andExpect(status().isOk());

        // Validate the Filiere in the database
        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeUpdate);
        Filiere testFiliere = filieres.get(filieres.size() - 1);
        assertThat(testFiliere.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void deleteFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);
        int databaseSizeBeforeDelete = filiereRepository.findAll().size();

        // Get the filiere
        restFiliereMockMvc.perform(delete("/api/filieres/{id}", filiere.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
