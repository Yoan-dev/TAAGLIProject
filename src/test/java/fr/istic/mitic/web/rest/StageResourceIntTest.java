package fr.istic.mitic.web.rest;

import fr.istic.mitic.TaagliProjectApp;

import fr.istic.mitic.domain.Stage;
import fr.istic.mitic.domain.Adresse;
import fr.istic.mitic.domain.Entreprise;
import fr.istic.mitic.domain.Responsable;
import fr.istic.mitic.domain.Enseignant;
import fr.istic.mitic.domain.Filiere;
import fr.istic.mitic.domain.Etudiant;
import fr.istic.mitic.repository.StageRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StageResource REST controller.
 *
 * @see StageResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaagliProjectApp.class)

public class StageResourceIntTest {
    private static final String DEFAULT_INTITULE = "AAAAA";
    private static final String UPDATED_INTITULE = "BBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private StageRepository stageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStageMockMvc;

    private Stage stage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StageResource stageResource = new StageResource();
        ReflectionTestUtils.setField(stageResource, "stageRepository", stageRepository);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage()
                .intitule(DEFAULT_INTITULE)
                .dateDebut(DEFAULT_DATE_DEBUT)
                .dateFin(DEFAULT_DATE_FIN);
        // Add required entity
        Adresse adresse = AdresseResourceIntTest.createEntity(em);
        em.persist(adresse);
        em.flush();
        stage.setAdresse(adresse);
        // Add required entity
        Entreprise entreprise = EntrepriseResourceIntTest.createEntity(em);
        em.persist(entreprise);
        em.flush();
        stage.setEntreprise(entreprise);
        // Add required entity
        Responsable responsable = ResponsableResourceIntTest.createEntity(em);
        em.persist(responsable);
        em.flush();
        stage.setResponsable(responsable);
        // Add required entity
        Enseignant enseignant = EnseignantResourceIntTest.createEntity(em);
        em.persist(enseignant);
        em.flush();
        stage.setEnseignant(enseignant);
        // Add required entity
        Filiere filiere = FiliereResourceIntTest.createEntity(em);
        em.persist(filiere);
        em.flush();
        stage.setFiliere(filiere);
        // Add required entity
        Etudiant etudiant = EtudiantResourceIntTest.createEntity(em);
        em.persist(etudiant);
        em.flush();
        stage.setEtudiant(etudiant);
        return stage;
    }

    @Before
    public void initTest() {
        stage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getIntitule()).isEqualTo(DEFAULT_INTITULE);
        assertThat(testStage.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testStage.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void checkIntituleIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setIntitule(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setDateDebut(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setDateFin(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stages
        restStageMockMvc.perform(get("/api/stages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
                .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE.toString())))
                .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
                .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    @Transactional
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findOne(stage.getId());
        updatedStage
                .intitule(UPDATED_INTITULE)
                .dateDebut(UPDATED_DATE_DEBUT)
                .dateFin(UPDATED_DATE_FIN);

        restStageMockMvc.perform(put("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStage)))
                .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testStage.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testStage.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Get the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
