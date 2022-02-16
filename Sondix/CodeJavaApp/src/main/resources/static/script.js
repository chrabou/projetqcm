//-------------------------------------------------------------------------------------------

// PARTIE JAVASCRIPT Concernat le coulissement des panneaux sur le menu d'accueil
//	Fonctionnel le 08/02/2022
/*
On récupere trois éléments simples pour l'animation JavaScript de la homepage :
	- le conteneur (élement qui contient les panneaux qui 'slide')
	-  les deux boutons qui gerent l'action de faire bouger les panneaux mobiles
*/
const boutonInscription = document.getElementById('boutonTransiInscription');
const boutonConnexion = document.getElementById('boutonTransiConnexion');
const conteneur = document.getElementById('conteneur');

/*
Quand on clique sur le boutton d'inscirption (partie droite) : 
			--> on fait apparaitre le panneau a gauche
*/
boutonInscription.addEventListener('click', () => {
	conteneur.classList.add("panneau-droite-actif");
});


/*
Quand on clique sur le boutton de connexion (partie gauche) : 
			1 --> on fait disparaitre le panneau a gauche et donc apparaitre a droite
			2 --> on fait  apparaitre le panneau à droite
*/	
boutonConnexion.addEventListener('click', () => {
	conteneur.classList.remove("panneau-droite-actif");
});

//-------------------------------------------------------------------------------------------
