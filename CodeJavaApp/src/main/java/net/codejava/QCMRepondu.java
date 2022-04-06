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
@Table(name = "QCMRepondu")
public class QCMRepondu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idQCMRepondu;

	@Column(name = "idQCM", nullable = false, length = 50)
	private String idQCM;

	@Column(name = "idUser", nullable = false, length = 50)
	private String idUser;

	public Long getIdQCMRepondu() {
		return idQCMRepondu;
	}

	public void setIdQCMRepondu(Long idQCMRepondu) {
		this.idQCMRepondu = idQCMRepondu;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	@Column(name = "reponses", nullable = false, length = 255)
	@Convert(converter = StringListConverter.class)
	private List<String> reponses;

	public String getIdQCM() {
		return idQCM;
	}

	public void setIdQCM(String idQCM) {
		this.idQCM = idQCM;
	}

	public List<String> getReponses() {
		return reponses;
	}

	public void setReponses(List<String> reponses) {
		this.reponses = reponses;
	}

}