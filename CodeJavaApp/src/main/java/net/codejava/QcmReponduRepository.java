package net.codejava;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QcmReponduRepository extends JpaRepository<QCMRepondu, Long> {
	@Query("SELECT u FROM QCM u WHERE u.titre = ?1 ")
	public QCMRepondu findByTitre(String titre);
	
	@Query( value = "SELECT count(*) FROM QCMRepondu ;", 
			  nativeQuery = true)
	public String countTotalQCMRepondu();
	
	@Query( value = "SELECT reponses FROM QCMRepondu where idQCM = ?1", 
			  nativeQuery = true)
	public List<String> findAllById(Long id);
	
	@Query( value = "SELECT reponses FROM QCMRepondu where idQCM = ?1 and id_user = ?2", 
			  nativeQuery = true)
	public List<String> findByIdAndUser(Long idQcm, Long idUser);
	
}
