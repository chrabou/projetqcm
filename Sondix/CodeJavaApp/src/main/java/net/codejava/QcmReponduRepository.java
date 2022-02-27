package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QcmReponduRepository extends JpaRepository<QCMRepondu, Long> {
	@Query("SELECT u FROM QCM u WHERE u.titre = ?1 ")
	public QCMRepondu findByTitre(String titre);
	
		
}
