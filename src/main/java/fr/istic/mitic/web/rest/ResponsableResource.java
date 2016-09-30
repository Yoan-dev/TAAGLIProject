package fr.istic.mitic.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.mitic.domain.Responsable;

import fr.istic.mitic.repository.ResponsableRepository;
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
 * REST controller for managing Responsable.
 */
@RestController
@RequestMapping("/api")
public class ResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableResource.class);
        
    @Inject
    private ResponsableRepository responsableRepository;

    /**
     * POST  /responsables : Create a new responsable.
     *
     * @param responsable the responsable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responsable, or with status 400 (Bad Request) if the responsable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/responsables",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Responsable> createResponsable(@Valid @RequestBody Responsable responsable) throws URISyntaxException {
        log.debug("REST request to save Responsable : {}", responsable);
        if (responsable.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("responsable", "idexists", "A new responsable cannot already have an ID")).body(null);
        }
        Responsable result = responsableRepository.save(responsable);
        return ResponseEntity.created(new URI("/api/responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("responsable", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsables : Updates an existing responsable.
     *
     * @param responsable the responsable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responsable,
     * or with status 400 (Bad Request) if the responsable is not valid,
     * or with status 500 (Internal Server Error) if the responsable couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/responsables",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Responsable> updateResponsable(@Valid @RequestBody Responsable responsable) throws URISyntaxException {
        log.debug("REST request to update Responsable : {}", responsable);
        if (responsable.getId() == null) {
            return createResponsable(responsable);
        }
        Responsable result = responsableRepository.save(responsable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("responsable", responsable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsables : get all the responsables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of responsables in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/responsables",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Responsable>> getAllResponsables(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Responsables");
        Page<Responsable> page = responsableRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /responsables/:id : get the "id" responsable.
     *
     * @param id the id of the responsable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responsable, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/responsables/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Responsable> getResponsable(@PathVariable Long id) {
        log.debug("REST request to get Responsable : {}", id);
        Responsable responsable = responsableRepository.findOne(id);
        return Optional.ofNullable(responsable)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /responsables/:id : delete the "id" responsable.
     *
     * @param id the id of the responsable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/responsables/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        log.debug("REST request to delete Responsable : {}", id);
        responsableRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("responsable", id.toString())).build();
    }

}
