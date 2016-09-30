package fr.istic.mitic.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.mitic.domain.Filiere;

import fr.istic.mitic.repository.FiliereRepository;
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
 * REST controller for managing Filiere.
 */
@RestController
@RequestMapping("/api")
public class FiliereResource {

    private final Logger log = LoggerFactory.getLogger(FiliereResource.class);
        
    @Inject
    private FiliereRepository filiereRepository;

    /**
     * POST  /filieres : Create a new filiere.
     *
     * @param filiere the filiere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filiere, or with status 400 (Bad Request) if the filiere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/filieres",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Filiere> createFiliere(@Valid @RequestBody Filiere filiere) throws URISyntaxException {
        log.debug("REST request to save Filiere : {}", filiere);
        if (filiere.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("filiere", "idexists", "A new filiere cannot already have an ID")).body(null);
        }
        Filiere result = filiereRepository.save(filiere);
        return ResponseEntity.created(new URI("/api/filieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("filiere", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filieres : Updates an existing filiere.
     *
     * @param filiere the filiere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filiere,
     * or with status 400 (Bad Request) if the filiere is not valid,
     * or with status 500 (Internal Server Error) if the filiere couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/filieres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Filiere> updateFiliere(@Valid @RequestBody Filiere filiere) throws URISyntaxException {
        log.debug("REST request to update Filiere : {}", filiere);
        if (filiere.getId() == null) {
            return createFiliere(filiere);
        }
        Filiere result = filiereRepository.save(filiere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("filiere", filiere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filieres : get all the filieres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filieres in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/filieres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Filiere>> getAllFilieres(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Filieres");
        Page<Filiere> page = filiereRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filieres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filieres/:id : get the "id" filiere.
     *
     * @param id the id of the filiere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filiere, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/filieres/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Filiere> getFiliere(@PathVariable Long id) {
        log.debug("REST request to get Filiere : {}", id);
        Filiere filiere = filiereRepository.findOne(id);
        return Optional.ofNullable(filiere)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /filieres/:id : delete the "id" filiere.
     *
     * @param id the id of the filiere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/filieres/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id) {
        log.debug("REST request to delete Filiere : {}", id);
        filiereRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("filiere", id.toString())).build();
    }

}
