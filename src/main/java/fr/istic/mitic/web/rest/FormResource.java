package fr.istic.mitic.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.istic.mitic.domain.Adresse;
import fr.istic.mitic.domain.Etudiant;
import fr.istic.mitic.repository.AdresseRepository;
import fr.istic.mitic.repository.EtudiantRepository;
import fr.istic.mitic.repository.FiliereRepository;
import fr.istic.mitic.web.rest.util.PaginationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by greg on 12/10/16.
 */

@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

	//@Inject
	//private FiliereRepository filiereRepository;

	@Inject
	private EtudiantRepository etudiantRepository;

    @RequestMapping(value = "/form/{data}",
        method = RequestMethod.GET,
    	produces = MediaType.APPLICATION_JSON_VALUE/*,
        consumes = MediaType.APPLICATION_JSON_VALUE*/)
    @Timed
    public ResponseEntity<List<Etudiant>> requetePerso(@PathVariable String[] data, Pageable pageable) throws URISyntaxException {
        System.out.println("resource : " +data[0]);
        String ent = data[0];
        String res = data[1];
        String ens = data[2];
        String fil = data[3];
        Page<Etudiant> page = etudiantRepository.queryTest(ent, res, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
