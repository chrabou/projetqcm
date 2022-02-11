package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private QcmRepository qcmRepo;

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

	//page des internautes pour faire les QCM
	@GetMapping("/internaute")
	public String viewInternautePage() {
		return "internaute";
	}

	//page du responsable pour validé ou refusé les QCM
	@GetMapping("/responsable")
	public String viewResponsablePage() {
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

	//enregistrement du QCM ou le qcm est unique
	@PostMapping("/process_creation")
	public String processCreation(QCM qcm) {

		if (qcmRepo.findByTitre(qcm.getTitre()) != null) {
			return "creation_failure";
		} else {

			qcmRepo.save(qcm);

			return "creation_success";
		}
	}
}
