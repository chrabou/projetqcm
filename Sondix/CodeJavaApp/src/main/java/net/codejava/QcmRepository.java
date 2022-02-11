package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QcmRepository extends JpaRepository<QCM, Long> {
	@Query("SELECT u FROM QCM u WHERE u.titre = ?1")
	public QCM findByTitre(String titre);
	
}
