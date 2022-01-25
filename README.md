Pour la base de données MySQL, il faut créer la database sondixdb comme ceci : CREATE DATABASE "sondixdb";

Pour lancer le projet Spring :

-Étape 1 : récupérer le dossier Sondix/CodeJavaApp depuis le Git

-Étape 2 : dans l'IDE SpringToolSuite4, aller dans "File" > "Open Projects from File System" puis Import source choisir "Directory...". Sélectionnez le dossier CodeJavaApp qui est dans le dossier Sondix. Enfin Cliquez sur "Finish". Cela va ouvrir le projet. Laisser l'IDE build le projet, cela peut prendre 1 - 5 minutes. Normalement, il ne devrait y avoir aucune erreur.

-Étape 3 : dans le fichier application.properties qui est dans le dossier "src/main/resources" renseigner le username et password de son serveur MySQL.

Étape 4 : lancer le Projet. Dans l'onglet en bas à gauche de l'IDE du nom de "Boot DashBoard" ouvrir le local avec la flèche "v" puis clique droit sur "CodeJavaApp [devtools] [:8080]" choisir "(Re)start)". Cela lancera le projet. Puis ouvrir, son navigateur, a l'adresse : localhost:8080
