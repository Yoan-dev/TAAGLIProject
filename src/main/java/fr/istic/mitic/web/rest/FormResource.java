package fr.istic.mitic.web.rest;

import java.net.URISyntaxException;
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
import fr.istic.mitic.repository.EtudiantRepository;
import fr.istic.mitic.repository.StageRepository;
import fr.istic.mitic.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

	@Inject
	private StageRepository stageRepository;

    @RequestMapping(value = "/form/{data}",
        method = RequestMethod.GET,
    	produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Etudiant>> requetePerso(@PathVariable String[] data, Pageable pageable) throws URISyntaxException {

    	// on veut récupérer une liste d'étudiants en fonction
    	// des caractéristiques des stages qu'ils ont effectués
    	// (données figées)
    	
    	// on filtre par entreprise
    	List<Long> stagesIds = null;
    	if (!data[0].equals("*")) {
    		stagesIds = stageRepository.getStagesByEnt(Long.parseLong(data[0]), pageable);
    	}

    	// on filtre par responsable
    	if (!data[1].equals("*")) {
    		if (stagesIds == null || stagesIds.isEmpty()) stagesIds = stageRepository.getStagesByRes(Long.parseLong(data[1]), pageable);
    		stagesIds = stageRepository.getStagesByRes(Long.parseLong(data[1]), stagesIds, pageable);
    	}

    	// on filtre par enseignant
    	if (!data[2].equals("*")) {
    		if (stagesIds == null || stagesIds.isEmpty()) stagesIds = stageRepository.getStagesByEns(Long.parseLong(data[2]), pageable);
    		stagesIds = stageRepository.getStagesByEns(Long.parseLong(data[2]), stagesIds, pageable);
    	}

    	// on filtre par filière
    	if (!data[3].equals("*")) {
    		if (stagesIds == null || stagesIds.isEmpty()) stagesIds = stageRepository.getStagesByFil(Long.parseLong(data[3]), pageable);
    		stagesIds = stageRepository.getStagesByFil(Long.parseLong(data[3]), stagesIds, pageable);
    	}
    	
    	// on récupère les étudiants correspondants
    	// aux stages récupérés par filtrage successif
    	Page<Etudiant> page = stageRepository.getEtuByStagesIds(stagesIds, pageable);

    	// on renvoi cette liste d'étudiants
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
