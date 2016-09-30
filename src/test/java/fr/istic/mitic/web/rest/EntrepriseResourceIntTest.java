package fr.istic.mitic.web.rest;

import fr.istic.mitic.TaagliProjectApp;

import fr.istic.mitic.domain.Entreprise;
import fr.istic.mitic.domain.Adresse;
import fr.istic.mitic.repository.EntrepriseRepository;

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
 * Test class for the EntrepriseResource REST controller.
 *
 * @see EntrepriseResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaagliProjectApp.class)

public class EntrepriseResourceIntTest {
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";
    private static final String DEFAULT_SIRET = "AAAAA";
    private static final String UPDATED_SIRET = "BBBBB";
    private static final String DEFAULT_TELEPHONE = "AAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBB";
    private static final String DEFAULT_MAIL = "AAAAA";
    private static final String UPDATED_MAIL = "BBBBB";

    @Inject
    private EntrepriseRepository entrepriseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrepriseResource entrepriseResource = new EntrepriseResource();
        ReflectionTestUtils.setField(entrepriseResource, "entrepriseRepository", entrepriseRepository);
        this.restEntrepriseMockMvc = MockMvcBuilders.standaloneSetup(entrepriseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
                .nom(DEFAULT_NOM)
                .siret(DEFAULT_SIRET)
                .telephone(DEFAULT_TELEPHONE)
                .mail(DEFAULT_MAIL);
        // Add required entity
        Adresse adresse = AdresseResourceIntTest.createEntity(em);
        em.persist(adresse);
        em.flush();
        entreprise.setAdresse(adresse);
        return entreprise;
    }

    @Before
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // Create the Entreprise

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entreprises.get(entreprises.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntreprise.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testEntreprise.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEntreprise.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setNom(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiretIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setSiret(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setTelephone(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setMail(null);

        // Create the Entreprise, which fails.

        restEntrepriseMockMvc.perform(post("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entreprise)))
                .andExpect(status().isBadRequest());

        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entreprises
        restEntrepriseMockMvc.perform(get("/api/entreprises?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
                .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
                .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())));
    }

    @Test
    @Transactional
    public void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findOne(entreprise.getId());
        updatedEntreprise
                .nom(UPDATED_NOM)
                .siret(UPDATED_SIRET)
                .telephone(UPDATED_TELEPHONE)
                .mail(UPDATED_MAIL);

        restEntrepriseMockMvc.perform(put("/api/entreprises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise)))
                .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entreprises.get(entreprises.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntreprise.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testEntreprise.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEntreprise.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);
        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Get the entreprise
        restEntrepriseMockMvc.perform(delete("/api/entreprises/{id}", entreprise.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeDelete - 1);
    }
}
