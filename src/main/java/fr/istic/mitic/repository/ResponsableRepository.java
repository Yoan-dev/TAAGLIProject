package fr.istic.mitic.repository;

import fr.istic.mitic.domain.Responsable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Responsable entity.
 */
@SuppressWarnings("unused")
public interface ResponsableRepository extends JpaRepository<Responsable,Long> {

}
