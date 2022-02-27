package net.codejava;

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

	private boolean alreadypass = false;

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private QcmRepository qcmRepo;
	
	@Autowired
	private QcmReponduRepository qcmReponsesRepo;

	//page d'accueil
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
 
	//page d'inscription ou on ajoute un utilisateur vide pour permettre sa création
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	//enregistrement du nouvel utilisateur dont le mail est unique pour chaque utilisateur
	@PostMapping("/process_register")
	public String processRegister(User user) {

		if (userRepo.findByEmail(user.getEmail()) != null) {
			return "register_failure";
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);

			userRepo.save(user);

			return "register_success";
		}
	}
	
	//page ou on voit tout les utilisateurs ou on donne a la vue la liste de tout les utilisateurs
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	//page des internautes pour faire les QCM ou on affiche les QCM disponible pour l'internaute par rapport au Username
	@GetMapping("/internaute")
	public String viewInternautePage(Model model, @CurrentSecurityContext(expression="authentication?.name")String username) {
		
		User myUser = userRepo.findByEmail2(username);
		List<QCM> listQcm = qcmRepo.findAllStatutValidateForInternaute(myUser.getId());
		//System.out.println("username  : " + username );
		model.addAttribute("listQcm", listQcm);
		return "internaute";
	}

	//page montrant le QCM pour que le responsable puisse ensuite décider d'accepter ou de refusé la mise en ligne d'un QCM
	@GetMapping("/detailQcm/{titre}")
	public String viewDetailQcmPage(@PathVariable("titre") String titre, Model model) {

		QCM myQCM = qcmRepo.findByTitre(titre);

		model.addAttribute("QCM", myQCM);

		return "detailQcm";
	}
	
	//page affichant pour l'internaute le QCM qu'il a choisi de faire
	@GetMapping("/faireQcm/{titre}")
	public String viewfaireQcmPage(@PathVariable("titre") String titre, Model model) {

		QCM myQCM = qcmRepo.findByTitre(titre);
		
		model.addAttribute("QCMRepondu", new QCMRepondu());
		model.addAttribute("QCM", myQCM);

		return "faireQcm";
	}

	//page du responsable pour validé ou refusé les QCM avec un commentaire mais aussi de pouvoir voir le détail d'un QCM
	@GetMapping("/responsable")
	public String viewResponsablePage(Model model) {
		List<QCM> listQcm = qcmRepo.findAllNotStatutCreate();

		model.addAttribute("listQcm", listQcm);
		return "responsable";
	}

	//page des employé pour voir les qcm, les créer, les supprimer et demande leurs mise en ligne on y met la liste des qcm
	@GetMapping("/employe")
	public String viewEmployePage(Model model) {
		List<QCM> listQcm = qcmRepo.findAll();

		model.addAttribute("listQcm", listQcm);
		return "employe";
	}
	
	//page du directeur ou il vérra un résumer de l'application et le détails statistiques des réponses aux questions de chaque QCM
	@GetMapping("/directeur")
	public String viewDirecteurPage() {
		return "directeur";
	}
	
	//page de création d'un QCM ou on passe un qcm vierge a remplir
	@GetMapping("/creation_qcm")
	public String viewCreationQCmPage(Model model) {
		model.addAttribute("qcm", new QCM());
		return "creation_qcm";
	}

	//enregistrement du QCM ou le qcm est unique en fonction de son titre
	@PostMapping("/process_creation")
	public String processCreation(QCM qcm) {

		if (qcmRepo.findByTitre(qcm.getTitre()) != null) {
			return "creation_failure";
		} else {

			qcmRepo.save(qcm);

			return "redirect:/employe";
		}
	}

	//permet de retourner a la page de l'employe
	@PostMapping("/retourEmploye")
	public String processRetourEmploye(Model model) {
		List<QCM> listQcm = qcmRepo.findAll();
		model.addAttribute("listQcm", listQcm);
		return "redirect:/employe";
	}
	
	//permet de retourner a la page du responsable
	@PostMapping("/retourResponsable")
	public String processRetourResponsable() {
		
		return "redirect:/responsable";
	}
	
	//permet de retourner a la page de l'internaute
	@PostMapping("/retourInternaute")
	public String processRetourInternaute() {
		
		return "redirect:/internaute";
	}
	
	//permet a l'employe de supprimer un QCM en fonction de son idQCM
	@PostMapping("/deleteData")
	public String processDeleteData(@RequestParam("idQCM") Long id, Model model) {

		qcmRepo.deleteById(id);
		List<QCM> listQcm = qcmRepo.findAll();
		model.addAttribute("listQcm", listQcm);
		return "redirect:/employe";
	}
	
	//enregistre la décision du responsable d'un QCM
	@PostMapping("/decision")
	public String processDecisionUpload(@RequestParam("action") String action,
			@RequestParam("commentaire") String commentaire, @RequestParam("titre") String titre, Model model) {

		// System.out.println("action : " + action +" commentaire : "+ commentaire
		// +"titre : " + titre);
		QCM myUpdateQCM = qcmRepo.findByTitre(titre);
		myUpdateQCM.setStatut(action);
		myUpdateQCM.setCommentaire(commentaire);
		qcmRepo.save(myUpdateQCM);

		return "redirect:/responsable";
	}

	//quand l'employe demande la mise en ligne d'un QCM cela change l'état du QCM en : Demande de mise en ligne
	@RequestMapping("/processOnline")
	public String processUpdateData(@RequestParam("titre") String titre, Model model) {
		QCM myUpdateQCM = qcmRepo.findByTitreAndCreateStatut(titre);
		myUpdateQCM.setStatut("Demande de mise en ligne");

		qcmRepo.save(myUpdateQCM);
		List<QCM> listQcm = qcmRepo.findAll();
		model.addAttribute("listQcm", listQcm);
		viewEmployePage(model);
		return "redirect:/employe";
	}
	
	//enregistre les réponses de l'internaute a un QCM en gardant l'idQCM et id de l'utilisateur 
	@PostMapping("/registerResponses")
	public String processresgisterResponses(Model model,  @CurrentSecurityContext(expression="authentication?.name")String username , @RequestParam("titre") String titre, QCMRepondu QCMRepondu) {
		User myUser = userRepo.findByEmail2(username);
		QCM myQCM = qcmRepo.findByTitre(titre);
		QCMRepondu.setIdUser(myUser.getId().toString());
		QCMRepondu.setIdQCM(myQCM.getIdQCM().toString());
		qcmReponsesRepo.save(QCMRepondu);
		
		//System.out.println("ID internaute : " +myUser.getId()+  "Réponses de l'internaute : ");
		return "redirect:/internaute";
	}
	
	
}
