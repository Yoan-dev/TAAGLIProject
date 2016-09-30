package fr.istic.mitic.web.rest;

import fr.istic.mitic.TaagliProjectApp;

import fr.istic.mitic.domain.Adresse;
import fr.istic.mitic.repository.AdresseRepository;

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
 * Test class for the AdresseResource REST controller.
 *
 * @see AdresseResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaagliProjectApp.class)

public class AdresseResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;
    private static final String DEFAULT_LIBELLE = "AAAAA";
    private static final String UPDATED_LIBELLE = "BBBBB";

    private static final Integer DEFAULT_CODE_POSTAL = 1;
    private static final Integer UPDATED_CODE_POSTAL = 2;
    private static final String DEFAULT_VILLE = "AAAAA";
    private static final String UPDATED_VILLE = "BBBBB";

    @Inject
    private AdresseRepository adresseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdresseResource adresseResource = new AdresseResource();
        ReflectionTestUtils.setField(adresseResource, "adresseRepository", adresseRepository);
        this.restAdresseMockMvc = MockMvcBuilders.standaloneSetup(adresseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse()
                .numero(DEFAULT_NUMERO)
                .libelle(DEFAULT_LIBELLE)
                .codePostal(DEFAULT_CODE_POSTAL)
                .ville(DEFAULT_VILLE);
        return adresse;
    }

    @Before
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresses.get(adresses.size() - 1);
        assertThat(testAdresse.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdresse.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAdresse.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setLibelle(null);

        // Create the Adresse, which fails.

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodePostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setCodePostal(null);

        // Create the Adresse, which fails.

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setVille(null);

        // Create the Adresse, which fails.

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresses
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
                .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
                .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
                .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
                .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())));
    }

    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findOne(adresse.getId());
        updatedAdresse
                .numero(UPDATED_NUMERO)
                .libelle(UPDATED_LIBELLE)
                .codePostal(UPDATED_CODE_POSTAL)
                .ville(UPDATED_VILLE);

        restAdresseMockMvc.perform(put("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAdresse)))
                .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresses.get(adresses.size() - 1);
        assertThat(testAdresse.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresse.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAdresse.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Get the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
