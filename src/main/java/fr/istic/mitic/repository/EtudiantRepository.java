package fr.istic.mitic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.istic.mitic.domain.Etudiant;

/**
 * Spring Data JPA repository for the Etudiant entity.
 */

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

}
