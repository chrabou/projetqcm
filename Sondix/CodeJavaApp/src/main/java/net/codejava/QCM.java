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
	private String statut="Créé";
	
	@Column(name = "commentaire", nullable = true, length = 50)
	private String commentaire;

	public Long getIdQCM() {
		return idQCM;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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

	@Column(name = "questions", nullable = false, length = 512)
	@Convert(converter = StringListConverter.class)
	private List<String> questions;
	
	@Column(name = "reponses1", nullable = false, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses1;
	
	public List<String> getReponses2() {
		return reponses2;
	}

	public void setReponses2(List<String> reponses2) {
		this.reponses2 = reponses2;
	}

	public List<String> getReponses3() {
		return reponses3;
	}

	public void setReponses3(List<String> reponses3) {
		this.reponses3 = reponses3;
	}

	public List<String> getReponses4() {
		return reponses4;
	}

	public void setReponses4(List<String> reponses4) {
		this.reponses4 = reponses4;
	}

	public List<String> getReponses5() {
		return reponses5;
	}

	public void setReponses5(List<String> reponses5) {
		this.reponses5 = reponses5;
	}

	public List<String> getReponses6() {
		return reponses6;
	}

	public void setReponses6(List<String> reponses6) {
		this.reponses6 = reponses6;
	}

	public List<String> getReponses7() {
		return reponses7;
	}

	public void setReponses7(List<String> reponses7) {
		this.reponses7 = reponses7;
	}

	public List<String> getReponses8() {
		return reponses8;
	}

	public void setReponses8(List<String> reponses8) {
		this.reponses8 = reponses8;
	}

	public List<String> getReponses9() {
		return reponses9;
	}

	public void setReponses9(List<String> reponses9) {
		this.reponses9 = reponses9;
	}

	public List<String> getReponses10() {
		return reponses10;
	}

	public void setReponses10(List<String> reponses10) {
		this.reponses10 = reponses10;
	}

	@Column(name = "reponses2", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses2;
	
	@Column(name = "reponses3", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses3;
	
	@Column(name = "reponses4", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses4;
	
	@Column(name = "reponses5", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses5;
	
	@Column(name = "reponses6", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses6;
	
	@Column(name = "reponses7", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses7;
	
	@Column(name = "reponses8", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses8;
	
	@Column(name = "reponses9", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses9;
	
	@Column(name = "reponses10", nullable = true, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses10;

	
}