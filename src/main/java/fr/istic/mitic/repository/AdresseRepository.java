package fr.istic.mitic.repository;

import fr.istic.mitic.domain.Adresse;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Adresse entity.
 */
@SuppressWarnings("unused")
public interface AdresseRepository extends JpaRepository<Adresse,Long> {

}
