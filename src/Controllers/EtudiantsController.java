package Controllers;

import Jdbc.EtudiantDatabase;
import Models.Etudiant;
import Models.Filiere;

import java.util.Scanner;

import static Controllers.FilieresController.showFilieres;
import static Services.DepartementServices.getDepartementById;
import static Services.EnseignantServices.*;
import static Services.EnseignantServices.deleteEnseignantById;
import static Services.EtudiantServices.*;
import static Services.FiliereServices.getFiliereById;
import static Services.Main.showPrincipalMenu;

public class EtudiantsController {
    public static void showMenu(){
        System.out.println("-------------------------[ Etudiants ]---------------------------");


        System.out.println("1: Pour ajouter un étudiant");
        System.out.println("2: Pour afficher les étudiants");
        System.out.println("3: Pour modifier un étudiant");
        System.out.println("4: Pour supprimer un étudiant");
        System.out.println("5: Pour retourner au menu principal");


        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                createEtudiants();
                break;
            case 2:
                showEtudiant();
                break;
            case 3:
                 editEtudiant();
                break;
            case 4:
                removeEtudiant();
                break;
            case 5:
                showPrincipalMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }}

    public static void showEtudiant(){
        for (Etudiant etudiant : etudiants) {
            System.out.print("Id : " + etudiant.getId());
            System.out.print(" | Nom : " + etudiant.getNom());
            System.out.print(" | Prenom : " + etudiant.getPrenom());
            System.out.print(" | Email : " + etudiant.getEmail());
            System.out.print(" | Apoge : " + etudiant.getApogee());
            System.out.print(" | Filiere : " +etudiant.getFiliere().getIntitule());

            System.out.println("");}
        }
    public static void createEtudiants(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom :");
        String Nom = scanner.nextLine();
        System.out.println("Entrez le Prenom :");
        String Prenom = scanner.nextLine();
        System.out.println("Entrez le Email :");
        String Email = scanner.nextLine();
        System.out.println("Entrez le apogee :");
        int apogee = scanner.nextInt();
        showFilieres();
        System.out.println("Sélecionnez un filiere par id :");
        int id = scanner.nextInt();
        addEtd(Nom,Prenom,Email,apogee,getFiliereById(id));
        showEtudiant();
        showMenu();
    }
    public static void editEtudiant(){
        Scanner scanner = new Scanner(System.in);
        showEtudiant();
        System.out.println("Sélecionnez un etudiant par id :");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez le nom :");
        String Nom = scanner.nextLine();
        System.out.println("Entrez le prenom :");
        String Prenom = scanner.nextLine();
        System.out.println("Entrez l'Email :");
        String Email = scanner.nextLine();
        System.out.println("Entrez l'apogee :");
        int apogee = scanner.nextInt();
        updateEtd(id,Nom,Prenom,Email,apogee,getFiliereById(id));
        showEtudiant();
        showMenu();
    }
    public static void removeEtudiant(){
        Scanner scanner = new Scanner(System.in);
        showEtudiant();
        System.out.println("Sélecionnez un etudiant par id :");
        int id = scanner.nextInt();
        /*getEtudiantdById(id);*/
        deleteEtudiantById(id);
        showEtudiant();
        showMenu();
    }
    }

