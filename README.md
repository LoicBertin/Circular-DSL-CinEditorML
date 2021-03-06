# CinEditorML

## Dépendances :

Pour exécuter ce projet, il vous faudra : Java 8+, Python 3 et ImageMagik.

Mais également les librairies python suivantes :

- Moviepy : `pip install moviepy`
- Numpy : `pip install numpy`



## Utilisation :

Dans le dossier : `bin`, il y a l'ensemble des fichiers pour exécuter le projet.

Pour lancer l'éditeur vidéo, il faut exécuter le fichier : `CinEditorML-App-1.0-SNAPSHOT.jar`



L'éditeur de gauche permet d'écrire votre scénario, vous pouvez ensuite utiliser les bouton "Visualize" pour obtenir le code Python ou "Run" pour générer directement la vidéo. 



## Compiler soit même :

Pour compiler l'application, rendez vous dans le dossier `app` et exécutez : `mvn clean package`. Vous pouvez déplacer le .jar compilé (situé à `app/target/CinEditorML-App-1.0-SNAPSHOT.jar`) dans le dossier `bin`.

Pour compiler le kernel, rendez vous dans le dossier `dsl/jvm` et exécutez : `mvn install`

Pour compiler le dsl, rendez vous dans le dossier `dsl/CinEditorML` et exécutez : `mvn clean compile assembly:single`. Vous pouvez déplacer le .jar compilé (situé à `dsl/CinEditorML/target/dsl-groovy-1.0-jar-with-dependencies.jar`) dans le dossier `bin/resources`.



## Rajouter un nouveau mot clef :

Il suffit de rajouter le mot clef dans le fichier : `bin\resources\keywords.txt`



