package fr.istic.mitic.repository;

import fr.istic.mitic.domain.Etudiant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.JoinTable;
import java.util.List;

/**
 * Spring Data JPA repository for the Etudiant entity.
 */

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

	//@Query(value = "SELECT e FROM Etudiant e WHERE e.nom = :etu AND e.filiere.nom = :fil")
	@Query(value = "SELECT e, fid FROM Etudiant e JOIN e.filiere fid WHERE e.nom = :etu AND fid.nom = :fil")
	Page<Etudiant> queryTest (@Param("etu") String etu, @Param("fil") String fil, Pageable pageable);

}
