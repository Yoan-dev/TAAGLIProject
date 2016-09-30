package fr.istic.mitic.repository;

import fr.istic.mitic.domain.Stage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
public interface StageRepository extends JpaRepository<Stage,Long> {

}
