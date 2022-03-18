Pour commencer, il faut installer MySQL, pour cela regardez cette vidéo : https://www.youtube.com/watch?v=rr4bXOZ5TyY&t=1s.

Une fois la base de données MySQL installée, il faut créer la database sondixdb comme ceci : 'CREATE DATABASE "sondixdb";'.

Ensuite, il faut installer Spring Tool Suite 4, pour cela regardez cette vidéo : https://www.youtube.com/watch?v=DKW6IlUrxG8.

Pour lancer le projet Spring :

- Étape 1 : Récupérer le dossier Sondix/CodeJavaApp depuis le Git.

- Étape 2 : Dans l'IDE SpringToolSuite4, aller dans "File" > "Open Projects from File System" puis Import source choisir "Directory...". Sélectionnez le dossier CodeJavaApp qui est dans le dossier Sondix. Enfin Cliquez sur "Finish". Cela va ouvrir le projet. Laisser l'IDE build le projet, cela peut prendre 1 - 5 minutes. Normalement, il ne devrait y avoir aucune erreur.

- Étape 3 : Dans le fichier application.properties qui est dans le dossier "src/main/resources" renseigner le username et password de son serveur MySQL. De plus pour le premier lancement de l'application mettre à la ligne 1 du fichier application.properties : "spring.jpa.hibernate.ddl-auto=create". Cela permet de créer la base de données. De plus, dans le fichier AppController.java du dossier 'CodeJavaApp/src/main/java' dans la méthode viewHomePage enlever le premier commentaire ligne 35 pour appeler la fonction autoAlimUserDB. 

- Étape 4 : Lancer le Projet. Dans l'onglet en bas à gauche de l'IDE du nom de "Boot DashBoard" ouvrir le local avec la flèche "v" puis clique droit sur "CodeJavaApp [devtools] [:8080]" choisir "(Re)start". Cela lancera le projet. Puis ouvrir, son navigateur, à l'adresse : localhost:8080. Une fois sur la page d'accueil la base de données aura été créée ainsi que les utilisateurs. Vous pouvez donc mettre le paramètre à none comme ceci : "spring.jpa.hibernate.ddl-auto=none" pour ne pas qu'à chaque fois que vous lancer le projet la base de données soit vidée et recréée. Enfin, remettez le commentaire de la ligne 35 dans le fichier AppController.java afin de ne pas créer les mêmes utilisateurs à chaque fois que vous arrivez sur la page d'accueil de l'application. Vous pouvez maintenant utiliser pleinement l'application Sondix. 
