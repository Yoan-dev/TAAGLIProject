package fr.istic.mitic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.mitic.domain.Etudiant;

/**
 * Spring Data JPA repository for the Etudiant entity.
 */

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

}
