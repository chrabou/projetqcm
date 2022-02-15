Pour commencer, il faut installer MySQL, pour cela regarder cette vidéo : https://www.youtube.com/watch?v=rr4bXOZ5TyY&t=1s.

Une fois la base de données MySQL installé, il faut créer la database sondixdb comme ceci : 'CREATE DATABASE "sondixdb";'.

Ensuite, il faut installer Spring Tool Suite 4, pour cela regarder cette vidéo : https://www.youtube.com/watch?v=DKW6IlUrxG8.

Pour lancer le projet Spring :

- Étape 1 : récupérer le dossier Sondix/CodeJavaApp depuis le Git.

- Étape 2 : dans l'IDE SpringToolSuite4, aller dans "File" > "Open Projects from File System" puis Import source choisir "Directory...". Sélectionnez le dossier CodeJavaApp qui est dans le dossier Sondix. Enfin Cliquez sur "Finish". Cela va ouvrir le projet. Laisser l'IDE build le projet, cela peut prendre 1 - 5 minutes. Normalement, il ne devrait y avoir aucune erreur.

- Étape 3 : dans le fichier application.properties qui est dans le dossier "src/main/resources" renseigner le username et password de son serveur MySQL. De plus pour le premier lancement de l'application mettre à la ligne 1 du fichier application.properties : "spring.jpa.hibernate.ddl-auto=create". Cela permet de créer la base de données. Ensuite, vous devez mettre ce paramètre à none comme ceci : "spring.jpa.hibernate.ddl-auto=none" pour ne pas qu'a chaque fois que vous lancer le projet la base de données soit vidé et recréer.

- Étape 4 : lancer le Projet. Dans l'onglet en bas à gauche de l'IDE du nom de "Boot DashBoard" ouvrir le local avec la flèche "v" puis clique droit sur "CodeJavaApp [devtools] [:8080]" choisir "(Re)start". Cela lancera le projet. Puis ouvrir, son navigateur, a l'adresse : localhost:8080.
