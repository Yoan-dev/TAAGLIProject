package fr.istic.mitic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.istic.mitic.domain.Etudiant;
import fr.istic.mitic.domain.Stage;

/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
public interface StageRepository extends JpaRepository<Stage,Long> {
	
    // Entreprise
    @Query(value = "SELECT s.id FROM Stage s WHERE s.entreprise.id = :ent")
    List<Long> getStagesByEnt(@Param("ent") Long ent, Pageable pageable);
    //

    // Enseignant
    @Query(value = "SELECT s.id FROM Stage s WHERE s.enseignant.id = :ens")
    List<Long> getStagesByEns(@Param("ens") Long ens, Pageable pageable);

    @Query(value = "SELECT s.id FROM Stage s WHERE s.enseignant.id = :ens AND s.id IN (:ids)")
    List<Long> getStagesByEns(@Param("ens") Long ens, @Param("ids") List<Long> ids, Pageable pageable);
    //

    // Responsable
    @Query(value = "SELECT s.id FROM Stage s WHERE s.responsable.id = :res")
    List<Long> getStagesByRes(@Param("res") Long res, Pageable pageable);

    @Query(value = "SELECT s.id FROM Stage s WHERE s.responsable.id = :res AND s.id IN (:ids)")
    List<Long> getStagesByRes(@Param("res") Long res, @Param("ids") List<Long> ids, Pageable pageable);
    //

    // Filiere
    @Query(value = "SELECT s.id FROM Stage s WHERE s.filiere.id = :fil")
    List<Long> getStagesByFil(@Param("fil") Long fil, Pageable pageable);

    @Query(value = "SELECT s.id FROM Stage s WHERE s.filiere.id = :fil AND s.id IN (:ids)")
    List<Long> getStagesByFil(@Param("fil") Long fil, @Param("ids") List<Long> ids, Pageable pageable);
    //

    // Etudiants
    @Query(value = "SELECT s.etudiant FROM Stage s WHERE s.id IN (:ids)")
    Page<Etudiant> getEtuByStagesIds(@Param("ids") List<Long> ids, Pageable pageable);
    //
}
