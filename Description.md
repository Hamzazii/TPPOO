
Ce programme est basée sur le dévellopement d'un système de gestion du parcours éducatif des étudiants,qui gère les départements, les filières, les enseignants, les modules, les étudiants et les notes.

Description des classes:
•Departement (intitule : texte, responsable : Enseignant).
•Enseignant (nom : texte, prénom : texte, email : texte, grade : texte, departement : Departement).
•Filiere (intitule : texte, responsable : Enseignant, departement : Departement).
•Module (intitule : texte, filiere : Filiere, professeur : Enseignant).
•Etudiant (nom : texte, prenom : texte, email : texte, apogee : int, filiere : Filiere).
•Note (note : float, etudiant : Etudiant, module : Module).

La réalisation de ce programme en deux Phases:
   *Phase1:La création d'un programme console respectant toutes les spécifications, basé sur les collections pour enregistrer les données.
   *Phase2:L'évolution de l'enregistrement des données vers une base de données SQL.

Dans ce programme il y a 4 packages:
Controllers:DepartementsController,EnseignantController,FilieresController,EtudiantsController,ModulesController.
Models:Departement,Enseignant,Filiere,Etudiant,Module,Note.
Services:DepartementServices,EnseignantServices,FiliereServices,EtudiantServices,ModuleServices,Main:pour l'exécution du programme Phase 1.
Jdbc:DepartementDatabase,EnseignantDatabase,FiliereDatabase,EtudiantDatabase,ModuleDatabase,NoteDatabase,Main2:pour l'exécution du programme Phase 2.
