package fr.istic.mitic.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by greg on 12/10/16.
 */

@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    @RequestMapping(value = "/form/{data}",
        method = RequestMethod.GET)
    // produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void toto(@PathVariable Long[] data) {
        for (Long i : data)
            System.out.println("je suis ici :"+i);
    }
}
