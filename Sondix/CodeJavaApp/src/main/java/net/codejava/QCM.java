package net.codejava;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QCM")
public class QCM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idQCM;

	@Column(name = "titre", nullable = false, length = 50)
	private String titre;

	@Column(name = "categorie", nullable = false, length = 50)
	private String categorie;

	@Column(name = "statut", nullable = false, length = 50)
	private String statut="Créer";

	public Long getIdQCM() {
		return idQCM;
	}

	public void setIdQCM(Long idQCM) {
		this.idQCM = idQCM;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public List<String> getReponses1() {
		return reponses1;
	}

	public void setReponses1(List<String> reponses1) {
		this.reponses1 = reponses1;
	}

	@Column(name = "questions", nullable = false, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> questions;
	
	@Column(name = "reponses1", nullable = false, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses1;
	
	
}