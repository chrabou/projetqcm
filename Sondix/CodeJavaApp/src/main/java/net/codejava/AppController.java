package net.codejava;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private QcmRepository qcmRepo;

	private User myUser;

	@Autowired
	private QcmReponduRepository qcmReponsesRepo;

	// page d'accueil
	@GetMapping("")
	public String viewHomePage() {
		// autoAlimUserDB();//enlever le commentaire de cette ligne quand dans le
		// fichier application.properties spring.jpa.hibernate.ddl-auto=create pour
		// créer la base de données
		return "index";
	}

	// méthode qui alimente automatique la base quand elle est créer
	public void autoAlimUserDB() {
		User employe = new User();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		employe.setEmail("vincent@live.fr");
		employe.setPassword(passwordEncoder.encode("0123456"));
		employe.setFirstName("Vincent");
		employe.setLastName("Pisano");
		employe.setPseudo("Empl");
		employe.setPhone("0645597135");
		employe.setSocio("Employés");
		employe.setBirth("07/10/1998");
		employe.setSexe("Homme");
		employe.setFidelite(0);
		employe.setRole("Employe");

		userRepo.save(employe);

		User responsable = new User();

		responsable.setEmail("bouchra@live.fr");
		responsable.setPassword(passwordEncoder.encode("0123456"));
		responsable.setFirstName("Bouchra");
		responsable.setLastName("Bnik");
		responsable.setPseudo("Respon");
		responsable.setPhone("0488975136");
		responsable.setSocio("Employés");
		responsable.setBirth("07/02/1999");
		responsable.setSexe("Femme");
		responsable.setFidelite(0);
		responsable.setRole("Responsable");

		userRepo.save(responsable);

		User internaute = new User();

		internaute.setEmail("jean@live.fr");
		internaute.setPassword(passwordEncoder.encode("0123456"));
		internaute.setFirstName("Jean");
		internaute.setLastName("Gautier");
		internaute.setPseudo("Inter");
		internaute.setPhone("0459905036");
		internaute.setSocio("Employés");
		internaute.setBirth("07/12/2001");
		internaute.setSexe("Homme");
		internaute.setFidelite(0);
		internaute.setRole("Internaute");

		userRepo.save(internaute);

		User directeur = new User();

		directeur.setEmail("hacene@live.fr");
		directeur.setPassword(passwordEncoder.encode("0123456"));
		directeur.setFirstName("Hacene");
		directeur.setLastName("Menguelti");
		directeur.setPseudo("Boss");
		directeur.setPhone("0959605033");
		directeur.setSocio("Employés");
		directeur.setBirth("01/01/1970");
		directeur.setSexe("Homme");
		directeur.setFidelite(0);
		directeur.setRole("Directeur");

		userRepo.save(directeur);
	}

	// page d'inscription ou on ajoute un utilisateur vide pour permettre sa
	// création
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	// enregistrement du nouvel utilisateur dont le mail est unique pour chaque
	// utilisateur
	@PostMapping("/process_register")
	public String processRegister(User user) {

		if (userRepo.findByEmail(user.getEmail()) != null) {
			return "register_failure";
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			user.setFidelite(0);

			userRepo.save(user);

			return "redirect:/login";
		}
	}

	// page ou on voit tout les utilisateurs ou on donne a la vue la liste de tout
	// les utilisateurs
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	// page des internautes pour faire les QCM ou on affiche les QCM disponible pour
	// l'internaute par rapport au Username
	@GetMapping("/internaute")
	public String viewInternautePage(Model model,
			@CurrentSecurityContext(expression = "authentication?.name") String username) {

		myUser = userRepo.findByEmail2(username);

		// Recupération des informations de l'utilisateurs actuellement connecté
		int fidelite = myUser.getFidelite();
		String email = myUser.getEmail();
		String first_name = myUser.getFirstName();
		String last_name = myUser.getLastName();
		String pseudo = myUser.getPseudo();
		String phone = myUser.getPhone();
		String socio = myUser.getSocio();
		String birth = myUser.getBirth();
		String sexe = myUser.getSexe();
		String role = myUser.getRole();

		// On ajoute toutes les informations précedemmet récupérer et on les ajoutent au
		// modele de la page
		// cela nous permettra de les afficher et plus facilement.
		model.addAttribute("fidelite", fidelite);
		model.addAttribute("email", email);
		model.addAttribute("first_name", first_name);
		model.addAttribute("last_name", last_name);
		model.addAttribute("pseudo", pseudo);
		model.addAttribute("phone", phone);
		model.addAttribute("socio", socio);
		model.addAttribute("birth", birth);
		model.addAttribute("sexe", sexe);
		model.addAttribute("role", role);

		/*
		 * De la même maniere que nous sommes aller chercher les informations de
		 * l'utilisateurs; cette fois-ci on récupere : une liste de QCM qui sont
		 * diponibles pour lui une liste de QCM que l'utilisateurs a déja réalisé.
		 */
		List<QCM> listQcm = qcmRepo.findAllStatutValidateForInternaute(myUser.getId());
		List<QCM> listQcmFait = qcmRepo.findAllStatutNotValidateForInternaute(myUser.getId());
		model.addAttribute("listQcmFait", listQcmFait);
		model.addAttribute("listQcm", listQcm);

		return "internaute";
	}

	// page montrant le QCM pour que le responsable puisse ensuite décider
	// d'accepter ou de refusé la mise en ligne d'un QCM
	@GetMapping("/detailQcm/{titre}")
	public String viewDetailQcmPage(@PathVariable("titre") String titre, Model model) {

		QCM myQCM = qcmRepo.findByTitre(titre);

		model.addAttribute("QCM", myQCM);

		return "detailQcm";
	}

	// page affichant pour l'internaute voir les réponses qu'il a mis a un ancien
	// QCM que il a fait
	@GetMapping("/voirQcm/{titre}")
	public String viewVoirQcmPage(@PathVariable("titre") String titre, Model model) {

		QCM myQCM = qcmRepo.findByTitre(titre);

		List<String> listQcmReponses = qcmReponsesRepo.findByIdAndUser(myQCM.getIdQCM(), myUser.getId());
		// System.out.println(listQcmReponses);

		String[] Reponse = StringListConverter.convertToEntityAttribute2(listQcmReponses.get(0)).toArray(new String[0]);

		model.addAttribute("Reponse", Reponse);
		model.addAttribute("QCM", myQCM);

		return "voirQcm";
	}

	// méthode qui calcule les statistiques d'un QCM
	@GetMapping("/detailStatQcm/{titre}")
	public String viewDetailStatQcmPage(@PathVariable("titre") String titre, Model model) {
		// On récupère le QCM lier au titre dont on veut avoir les statistiques
		QCM myQCM = qcmRepo.findByTitre(titre);

		// On récupère les réponses des internautes à ce QCM sous forme de liste de
		// String
		List<String> listQcmReponses = qcmReponsesRepo.findAllById(myQCM.getIdQCM());

		// On créer un tableau de : [nombre de d'internaute ayant répondu][le nombre de
		// question du QCM]
		String[][] StatReponses = new String[listQcmReponses.size()][myQCM.getQuestions().size()];

		// On alimente le tableau avec les réponses des internautes
		for (int i = 0; i < listQcmReponses.size(); i++) {
			StatReponses[i] = StringListConverter.convertToEntityAttribute2(listQcmReponses.get(i))
					.toArray(new String[0]);
		}

		// Nombre total de réponses a ce QCM
		int totalNumberQcmRepondu = StatReponses.length;

		// Compteur pour pouvoir faire des statistique par rapport au nombre total de
		// réponses
		double cumulReponse1 = 0;
		double cumulReponse2 = 0;
		double cumulReponse3 = 0;
		double cumulReponse4 = 0;

		double[][] TotalStat = new double[myQCM.getQuestions().size()][4];
		// System.out.println(StatReponses.toString());
		// On parcours toutes les questions du question
		for (int col = 0; col < myQCM.getQuestions().size(); col++) {
			// pour chaque question je parcours toutes les réponses des interntaites
			for (int i = 0; i < StatReponses.length; i++) {
				// On augmente les compteurs qui correspond aux réponses des internautes
				if (StatReponses[i][col].equals("1")) {
					cumulReponse1++;
				} else if (StatReponses[i][col].equals("2")) {
					cumulReponse2++;
				} else if (StatReponses[i][col].equals("3")) {
					cumulReponse3++;
				} else if (StatReponses[i][col].equals("4")) {
					cumulReponse4++;
				}

			}

			// On divise le cumule puis le divise par 100, l'arrondi et le stocke dans la
			// variable TotalStat
			if (cumulReponse1 != 0) {
				TotalStat[col][0] = Math.round(((cumulReponse1 / totalNumberQcmRepondu) * 100) * 100.0) / 100.0;
			} else {
				TotalStat[col][0] = 0;
			}

			if (cumulReponse2 != 0) {
				TotalStat[col][1] = Math.round(((cumulReponse2 / totalNumberQcmRepondu) * 100) * 100.0) / 100.0;
			} else {
				TotalStat[col][1] = 0;
			}

			if (cumulReponse3 != 0) {
				TotalStat[col][2] = Math.round(((cumulReponse3 / totalNumberQcmRepondu) * 100) * 100.0) / 100.0;
			} else {
				TotalStat[col][2] = 0;
			}

			if (cumulReponse4 != 0) {
				TotalStat[col][3] = Math.round(((cumulReponse4 / totalNumberQcmRepondu) * 100) * 100.0) / 100.0;
			} else {
				TotalStat[col][3] = 0;
			}

			// On remets les compteurs à zeros
			cumulReponse1 = 0;
			cumulReponse2 = 0;
			cumulReponse3 = 0;
			cumulReponse4 = 0;
		}
		// On passe le nombre de personne ayant répondue a ce QCM a la vue
		model.addAttribute("NbReponse", totalNumberQcmRepondu);
		// On passe les statistiques du QCM a la vue
		model.addAttribute("TotalStat", TotalStat);
		// On passe le QCM en question a la vue
		model.addAttribute("QCM", myQCM);

		// On redirige vers la vue detailStatQcm
		return "detailStatQcm";
	}

	// page permettant de modifier un QCM
	@GetMapping("/modifierQcm/{titre}")
	public String viewModifierQcmPage(@PathVariable("titre") String titre, Model model) {

		QCM myQCM = qcmRepo.findByTitre(titre);

		model.addAttribute("QCM", myQCM);

		return "modifierQcm";
	}

	// page affichant pour l'internaute le QCM qu'il a choisi de faire
	@GetMapping("/faireQcm/{titre}")
	public String viewfaireQcmPage(@PathVariable("titre") String titre, Model model) {

		QCM myQCM = qcmRepo.findByTitre(titre);

		model.addAttribute("QCMRepondu", new QCMRepondu());
		model.addAttribute("QCM", myQCM);

		return "faireQcm";
	}

	// page du responsable pour validé ou refusé les QCM avec un commentaire mais
	// aussi de pouvoir voir le détail d'un QCM
	@GetMapping("/responsable")
	public String viewResponsablePage(Model model) {
		List<QCM> listQcm = qcmRepo.findAllNotStatutCreate();

		model.addAttribute("listQcm", listQcm);
		return "responsable";
	}

	// page des employé pour voir les qcm, les créer, les supprimer et demande leurs
	// mise en ligne on y met la liste des qcm
	@GetMapping("/employe")
	public String viewEmployePage(Model model) {
		List<QCM> listQcm = qcmRepo.findAll();

		model.addAttribute("listQcm", listQcm);
		return "employe";
	}

	// page du directeur ou il vérra un résumer de l'application et le détails
	// statistiques des réponses aux questions de chaque QCM
	@GetMapping("/directeur")
	public String viewDirecteurPage(Model model) {

		String totalQCMDispo = qcmRepo.findCountQCMValidate();
		if (totalQCMDispo.equals(null)) {
			totalQCMDispo = "0";
		}
		String mostPopular = qcmRepo.findMostPopular();
		if (mostPopular == null) {
			mostPopular = "Aucun QCM";
		}

		String worstPopular = qcmRepo.findWorstPopular();
		if (worstPopular == null) {
			worstPopular = "Aucun QCM";
		}

		String countInternaute = userRepo.countTotalInternaute();
		if (countInternaute.equals(null)) {
			worstPopular = "Aucun Internaute";
		}

		String countTotalQCMRepondu = qcmReponsesRepo.countTotalQCMRepondu();
		if (countTotalQCMRepondu.equals(null)) {
			countTotalQCMRepondu = "0";
		}

		List<QCM> listQcm = qcmRepo.findAllStatutValidate();

		model.addAttribute("listQcm", listQcm);

		model.addAttribute("countTotalQCMRepondu", countTotalQCMRepondu);
		model.addAttribute("countInternaute", countInternaute);
		model.addAttribute("worstPopular", worstPopular);
		model.addAttribute("mostPopular", mostPopular);
		model.addAttribute("totalQCMDispo", totalQCMDispo);
		return "directeur";
	}

	// page de création d'un QCM ou on passe un qcm vierge a remplir
	@PostMapping("/creation_qcm")
	public String viewCreationQCmPage(Model model, @RequestParam("nbqts") String nbQuestions) {

		// Création d'une liste pour récupérer le nombre de question a afficher
		List<String> nbq = new ArrayList<String>();

		// a partir du nombre de question donner par l'employer dans l'interface employe
		// j'augmente la taille de ma liste
		for (int i = 0; i < Integer.valueOf(nbQuestions); i++) {
			nbq.add("");
		}

		// je passe ma liste a la vue pour savoir combien de question créer
		model.addAttribute("nbqts", nbq);

		// je passe l'objet QCM a ma vue qui s'occupera de le remplir
		model.addAttribute("qcm", new QCM());

		// je redirige vers la vue creation_qcm
		return "creation_qcm";
	}

	// enregistrement du QCM ou le qcm est unique en fonction de son titre
	@PostMapping("/process_creation")
	public String processCreation(QCM qcm) {

		if (qcmRepo.findByTitre(qcm.getTitre()) != null) {
			return "creation_failure";
		} else {

			qcmRepo.save(qcm);

			return "redirect:/employe";
		}
	}

	// permet de retourner a la page de l'employe
	@PostMapping("/retourEmploye")
	public String processRetourEmploye(Model model) {
		List<QCM> listQcm = qcmRepo.findAll();
		model.addAttribute("listQcm", listQcm);
		return "redirect:/employe";
	}

	// permet de retourner a la page du responsable
	@PostMapping("/retourResponsable")
	public String processRetourResponsable() {

		return "redirect:/responsable";
	}

	// permet de retourner a la page du directeur
	@PostMapping("/retourDirecteur")
	public String processRetourDirecteur() {

		return "redirect:/directeur";
	}

	// permet de retourner a la page de l'internaute
	@PostMapping("/retourInternaute")
	public String processRetourInternaute() {

		return "redirect:/internaute";
	}

	// permet a l'employe de supprimer un QCM en fonction de son idQCM
	@PostMapping("/deleteData")
	public String processDeleteData(@RequestParam("idQCM") Long id, Model model) {
		// Suppression du QCM
		qcmRepo.deleteById(id);
		// Récupération de tout les QCM après la suppression
		List<QCM> listQcm = qcmRepo.findAll();
		// Passage des QCM à la vue
		model.addAttribute("listQcm", listQcm);
		// redirection vers la vue employe
		return "redirect:/employe";
	}

	// enregistre la décision du responsable d'un QCM
	@PostMapping("/decision")
	public String processDecisionUpload(@RequestParam("action") String action,
			@RequestParam("commentaire") String commentaire, @RequestParam("titre") String titre, Model model) {

		QCM myUpdateQCM = qcmRepo.findByTitre(titre);
		myUpdateQCM.setStatut(action);
		myUpdateQCM.setCommentaire(commentaire);
		qcmRepo.save(myUpdateQCM);

		return "redirect:/responsable";
	}

	// enregistre la décision du directeur d'archiver un QCM
	@PostMapping("/archivage")
	public String processArchivageQCMUpload(@RequestParam("action") String action, @RequestParam("titre") String titre,
			Model model) {

		QCM myUpdateQCM = qcmRepo.findByTitre(titre);
		myUpdateQCM.setStatut("Archivé");
		qcmRepo.save(myUpdateQCM);

		return "redirect:/directeur";
	}

	// permet la modification d'un QCM
	@PostMapping("/process_modification")
	public String processModificationUpload(@RequestParam("titreancien") String titreancien,
			@RequestParam("titre") String titre, @RequestParam("categorie") String categorie,
			@RequestParam("questions") List<String> questions, @RequestParam("reponses1") List<String> reponses1,
			@RequestParam(value = "reponses2", required = false, defaultValue = "") List<String> reponses2,
			@RequestParam(value = "reponses3", required = false, defaultValue = "") List<String> reponses3,
			@RequestParam(value = "reponses4", required = false, defaultValue = "") List<String> reponses4,
			@RequestParam(value = "reponses5", required = false, defaultValue = "") List<String> reponses5,
			@RequestParam(value = "reponses6", required = false, defaultValue = "") List<String> reponses6,
			@RequestParam(value = "reponses7", required = false, defaultValue = "") List<String> reponses7,
			@RequestParam(value = "reponses8", required = false, defaultValue = "") List<String> reponses8,
			@RequestParam(value = "reponses9", required = false, defaultValue = "") List<String> reponses9,
			@RequestParam(value = "reponses10", required = false, defaultValue = "") List<String> reponses10,
			Model model) {

		// Récupération du QCM à partir de son ancien Titre
		QCM myModifyQCM = qcmRepo.findByTitre(titreancien);

		// Mise a jour du QCM
		myModifyQCM.setTitre(titre);
		myModifyQCM.setCategorie(categorie);
		myModifyQCM.setQuestions(questions);
		myModifyQCM.setReponses1(reponses1);
		myModifyQCM.setReponses2(reponses2);
		myModifyQCM.setReponses3(reponses3);
		myModifyQCM.setReponses4(reponses4);
		myModifyQCM.setReponses5(reponses5);
		myModifyQCM.setReponses6(reponses6);
		myModifyQCM.setReponses7(reponses7);
		myModifyQCM.setReponses8(reponses8);
		myModifyQCM.setReponses9(reponses9);
		myModifyQCM.setReponses10(reponses10);

		// sauvergarde de la mise a jour dans la base de données
		qcmRepo.save(myModifyQCM);

		// redirection vers la vue employe
		return "redirect:/employe";
	}

	// quand l'employe demande la mise en ligne d'un QCM cela change l'état du QCM
	// en : Demande de mise en ligne
	@RequestMapping("/processOnline")
	public String processUpdateData(@RequestParam("titre") String titre, Model model) {
		// On récupère le QCM a partir de son titre
		QCM myUpdateQCM = qcmRepo.findByTitre(titre);

		// On change son statut pour Demande de mise en ligne
		myUpdateQCM.setStatut("Demande de mise en ligne");

		// On sauvergarde le changement dans la base de données
		qcmRepo.save(myUpdateQCM);

		// On récupère tout les QCM
		List<QCM> listQcm = qcmRepo.findAll();

		// On passe la liste de tout les QCM dans la vue
		model.addAttribute("listQcm", listQcm);
		viewEmployePage(model);

		// On redirige vers la vue employe
		return "redirect:/employe";
	}

	// enregistre les réponses de l'internaute a un QCM en gardant l'idQCM et id de
	// l'utilisateur
	@PostMapping("/registerResponses")
	public String processresgisterResponses(Model model,
			@CurrentSecurityContext(expression = "authentication?.name") String username,
			@RequestParam("titre") String titre, QCMRepondu QCMRepondu) {

		// on récupère l'internaute ayant répondu au QCM
		User myUser = userRepo.findByEmail2(username);

		// on augmente ses points de fidélité par 100
		myUser.setFidelite(myUser.getFidelite() + 100);

		// on récupère le QCM auquel il a répondu grâce a son titre
		QCM myQCM = qcmRepo.findByTitre(titre);

		// on sauvegarde dans l'identifiant du user et du QCM dans le QCMRepondu sachant
		// que ses réponses qu'il a choisi on était sauvergarder par la vue faireQcm
		QCMRepondu.setIdUser(myUser.getId().toString());
		QCMRepondu.setIdQCM(myQCM.getIdQCM().toString());
		qcmReponsesRepo.save(QCMRepondu);

		return "redirect:/internaute";
	}

}
