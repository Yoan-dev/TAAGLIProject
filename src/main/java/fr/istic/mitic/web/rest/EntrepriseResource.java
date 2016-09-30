package fr.istic.mitic.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.mitic.domain.Entreprise;

import fr.istic.mitic.repository.EntrepriseRepository;
import fr.istic.mitic.web.rest.util.HeaderUtil;
import fr.istic.mitic.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entreprise.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);
        
    @Inject
    private EntrepriseRepository entrepriseRepository;

    /**
     * POST  /entreprises : Create a new entreprise.
     *
     * @param entreprise the entreprise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreprise, or with status 400 (Bad Request) if the entreprise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entreprises",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entreprise> createEntreprise(@Valid @RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entreprise);
        if (entreprise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entreprise", "idexists", "A new entreprise cannot already have an ID")).body(null);
        }
        Entreprise result = entrepriseRepository.save(entreprise);
        return ResponseEntity.created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entreprise", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entreprises : Updates an existing entreprise.
     *
     * @param entreprise the entreprise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entreprise,
     * or with status 400 (Bad Request) if the entreprise is not valid,
     * or with status 500 (Internal Server Error) if the entreprise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entreprises",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entreprise> updateEntreprise(@Valid @RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}", entreprise);
        if (entreprise.getId() == null) {
            return createEntreprise(entreprise);
        }
        Entreprise result = entrepriseRepository.save(entreprise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entreprise", entreprise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entreprises : get all the entreprises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entreprises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/entreprises",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Entreprise>> getAllEntreprises(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Entreprises");
        Page<Entreprise> page = entrepriseRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entreprises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entreprises/:id : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entreprise, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/entreprises/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get Entreprise : {}", id);
        Entreprise entreprise = entrepriseRepository.findOne(id);
        return Optional.ofNullable(entreprise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entreprises/:id : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/entreprises/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entreprise", id.toString())).build();
    }

}
