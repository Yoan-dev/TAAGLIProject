package fr.istic.mitic.repository;

import fr.istic.mitic.domain.Stage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
public interface StageRepository extends JpaRepository<Stage,Long> {

    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.entreprise = :ent")
    Page<Stage> getEtuByEnt(@Param("ent") String ent, Pageable pageable);

    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.entreprise = :ent AND s.responsable = :res")
    Page<Stage> getEtuByEntAndRes(@Param("ent") String ent, @Param("res") String res, Pageable pageable);

    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.entreprise = :ent AND s.enseignant = :ens")
    Page<Stage> getEtuByEntAndEns(@Param("ent") String ent, @Param("ens") String ens, Pageable pageable);

    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.entreprise = :ent AND s.filiere = :fil")
    Page<Stage> getEtuByEntAndFil(@Param("ent") String ent, @Param("fil") String fil, Pageable pageable);

    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.entreprise = :ent AND s.responsable = :res AND s.enseignant = :ens")
    Page<Stage> getEtuByEntAndResAndEns(@Param("ent") String ent, @Param("res") String res, @Param("ens") String ens, Pageable pageable);

    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.entreprise = :ent AND s.responsable = :res AND s.filiere = :fil")
    Page<Stage> getEtuByEntAndResAndFil(@Param("ent") String ent, @Param("res") String res, @Param("fil") String fil, Pageable pageable);

}
