package net.codejava;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QcmRepository extends JpaRepository<QCM, Long> {
	@Query("SELECT u FROM QCM u WHERE u.titre = ?1 ")
	public QCM findByTitre(String titre);
	
	@Query("delete FROM QCM WHERE idQCM = ?1")
	public QCM deleteById(int id);
	
	@Query("SELECT u FROM QCM u WHERE u.titre = ?1 and u.statut ='Créé'")
	public QCM findByTitreAndCreateStatut(String titre);
	
	@Query( value = "SELECT * FROM QCM u  WHERE  u.statut !='Créé'", 
			  nativeQuery = true)
	public List<QCM> findAllNotStatutCreate();
	
	@Query( value = "select * from QCM where idqcm not in (select idqcm from QCMRepondu where id_user = ?1) and  statut ='Validé'", 
			  nativeQuery = true)
	public List<QCM> findAllStatutValidateForInternaute(Long id);
	
	@Query( value = "select * from QCM where idqcm  in (select idqcm from QCMRepondu where id_user = ?1) and  statut ='Validé'", 
			  nativeQuery = true)
	public List<QCM> findAllStatutNotValidateForInternaute(Long id);
	
	
	@Query( value = "select * from QCM where  statut ='Validé' or statut='Archivé'", 
			  nativeQuery = true)
	public List<QCM> findAllStatutValidate();
	
	@Query( value = "select COUNT(*) from QCM where statut ='Validé'", 
			  nativeQuery = true)
	public String findCountQCMValidate();
	
	
	@Query( value = "SELECT titre FROM QCM natural join QCMRepondu GROUP BY titre ORDER BY COUNT(*) DESC limit 1 ;", 
			  nativeQuery = true)
	public String findMostPopular();
		
	@Query( value = "SELECT titre FROM QCM natural join QCMRepondu GROUP BY titre ORDER BY COUNT(*) ASC limit 1 ;", 
			  nativeQuery = true)
	public String findWorstPopular();
	
	
}
