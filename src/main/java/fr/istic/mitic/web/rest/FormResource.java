package fr.istic.mitic.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

import com.codahale.metrics.annotation.Timed;

import fr.istic.mitic.domain.Etudiant;
import fr.istic.mitic.domain.Stage;
import fr.istic.mitic.repository.EtudiantRepository;
import fr.istic.mitic.repository.StageRepository;
import fr.istic.mitic.web.rest.util.PaginationUtil;

/**
 * Created by greg on 12/10/16.
 */

@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

	@Inject
	private StageRepository stageRepository;
	private EtudiantRepository etudiantRepository;

    @RequestMapping(value = "/form/{data}",
        method = RequestMethod.GET,
    	produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Etudiant>> requetePerso(@PathVariable String[] data, Pageable pageable) throws URISyntaxException {

    	System.out.println("Entreprise: " + data[0]);
    	System.out.println("Responsable: " + data[1]);
    	System.out.println("Enseignant: " + data[2]);
    	System.out.println("Filière: " + data[3]);
    	
    	List<Long> stagesIds = null;
    	if (!data[0].equals("*")) {System.out.println("ENT");
    		stagesIds = stageRepository.getStagesByEnt(data[0], pageable);
    		//
    	}
    	
    	if (!data[1].equals("*")) {System.out.println("REP");
    		if (stagesIds == null || stagesIds.isEmpty()) stagesIds = stageRepository.getStagesByRes(data[1], pageable);
    		stagesIds = stageRepository.getStagesByRes(data[1], stagesIds, pageable);
    	}
    	
    	if (!data[2].equals("*")) {System.out.println("ENS");
    		if (stagesIds == null || stagesIds.isEmpty()) stagesIds = stageRepository.getStagesByEns(data[2], pageable);
    		stagesIds = stageRepository.getStagesByEns(data[2], stagesIds, pageable);
    	}
    	
    	if (!data[3].equals("*")) {System.out.println("FIL");
    		if (stagesIds == null || stagesIds.isEmpty()) stagesIds = stageRepository.getStagesByFil(data[3], pageable);
    		stagesIds = stageRepository.getStagesByFil(data[3], stagesIds, pageable);
    	}
    	System.out.println(stagesIds.size());
    	Page<Etudiant> page = stageRepository.getEtuByStagesIds(stagesIds, pageable);
    	
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
