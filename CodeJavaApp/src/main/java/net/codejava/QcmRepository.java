package net.codejava;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QcmRepository extends JpaRepository<QCM, Long> {
	@Query("SELECT u FROM QCM u WHERE u.titre = ?1")
	public QCM findByTitre(String titre);

	// Test de méthode pour la recherche de catégorie d'un QCM
	@Query("SELECT u FROM QCM u WHERE u.categorie = ?1")
	public List<QCM> findByCategorie(String categorie);


	/*
	 * @Query("SELECT u FROM QCM u WHERE u.categorie = LIKE ?1%") public QCM
	 * findByCategorie(String categorie);
	 */

}
