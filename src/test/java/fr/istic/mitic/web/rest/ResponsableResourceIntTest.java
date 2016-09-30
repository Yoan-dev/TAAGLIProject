package fr.istic.mitic.web.rest;

import fr.istic.mitic.TaagliProjectApp;

import fr.istic.mitic.domain.Responsable;
import fr.istic.mitic.domain.Entreprise;
import fr.istic.mitic.repository.ResponsableRepository;

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
 * Test class for the ResponsableResource REST controller.
 *
 * @see ResponsableResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaagliProjectApp.class)

public class ResponsableResourceIntTest {
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";
    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";
    private static final String DEFAULT_POSTE = "AAAAA";
    private static final String UPDATED_POSTE = "BBBBB";
    private static final String DEFAULT_TELEPHONE = "AAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBB";
    private static final String DEFAULT_MAIL = "AAAAA";
    private static final String UPDATED_MAIL = "BBBBB";

    @Inject
    private ResponsableRepository responsableRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restResponsableMockMvc;

    private Responsable responsable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResponsableResource responsableResource = new ResponsableResource();
        ReflectionTestUtils.setField(responsableResource, "responsableRepository", responsableRepository);
        this.restResponsableMockMvc = MockMvcBuilders.standaloneSetup(responsableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsable createEntity(EntityManager em) {
        Responsable responsable = new Responsable()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .poste(DEFAULT_POSTE)
                .telephone(DEFAULT_TELEPHONE)
                .mail(DEFAULT_MAIL);
        // Add required entity
        Entreprise entreprise = EntrepriseResourceIntTest.createEntity(em);
        em.persist(entreprise);
        em.flush();
        responsable.setEntreprise(entreprise);
        return responsable;
    }

    @Before
    public void initTest() {
        responsable = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsable() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isCreated());

        // Validate the Responsable in the database
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeCreate + 1);
        Responsable testResponsable = responsables.get(responsables.size() - 1);
        assertThat(testResponsable.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testResponsable.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testResponsable.getPoste()).isEqualTo(DEFAULT_POSTE);
        assertThat(testResponsable.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testResponsable.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setNom(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setPrenom(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPosteIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setPoste(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setTelephone(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsableRepository.findAll().size();
        // set the field null
        responsable.setMail(null);

        // Create the Responsable, which fails.

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isBadRequest());

        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsables() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsables
        restResponsableMockMvc.perform(get("/api/responsables?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].poste").value(hasItem(DEFAULT_POSTE.toString())))
                .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
                .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())));
    }

    @Test
    @Transactional
    public void getResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", responsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsable.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.poste").value(DEFAULT_POSTE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsable() throws Exception {
        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable
        Responsable updatedResponsable = responsableRepository.findOne(responsable.getId());
        updatedResponsable
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .poste(UPDATED_POSTE)
                .telephone(UPDATED_TELEPHONE)
                .mail(UPDATED_MAIL);

        restResponsableMockMvc.perform(put("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResponsable)))
                .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsables.get(responsables.size() - 1);
        assertThat(testResponsable.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testResponsable.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testResponsable.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testResponsable.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testResponsable.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void deleteResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);
        int databaseSizeBeforeDelete = responsableRepository.findAll().size();

        // Get the responsable
        restResponsableMockMvc.perform(delete("/api/responsables/{id}", responsable.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeDelete - 1);
    }
}
