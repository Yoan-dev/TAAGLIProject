package fr.istic.mitic.repository;

import fr.istic.mitic.domain.Etudiant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Etudiant entity.
 */
@SuppressWarnings("unused")
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
	
	@Query("SELECT e.mail from Etudiant as e, Filiere as f WHERE e.nom = :etu AND f.nom = :fil")
	Page<Etudiant> queryTest (String etu, String fil, Pageable pageable);

}
