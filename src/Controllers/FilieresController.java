package Controllers;

import Models.Etudiant;
import Models.Filiere;

import java.util.Scanner;

import static Controllers.DepartementsController.showDepartements;
import static Controllers.EnseignantsController.showEnseignants;
import static Services.DepartementServices.getDepartementById;
import static Services.EnseignantServices.*;
import static Services.EtudiantServices.etudiants;
import static Services.FiliereServices.*;
import static Services.Main.showPrincipalMenu;

public class FilieresController {
    public static void showMenu(){
        System.out.println("-------------------------[ Filieres ]---------------------------");


        System.out.println("1: Pour ajouter un filiere");
        System.out.println("2: Pour afficher les filieres");
        System.out.println("3: Pour modifier un filiere");
        System.out.println("4: Pour supprimer un filiere");
        System.out.println("5: Pour retourner au menu principal");


        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                createFiliere();
                break;
            case 2:
                showFilieres();
                break;
            case 3:
                editFiliere();
                break;
            case 4:
               removeFiliere();
                break;
            case 5:
                showPrincipalMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }}

    public static void showFilieres(){
        for (Filiere filiere : filieres) {
            System.out.print("Id : " + filiere.getId());
            System.out.print(" | Intitule : " + filiere.getIntitule());
            System.out.print(" | Departement : " + filiere.getDepartement().getIntitule());
            System.out.print(" | Responsable : " +filiere.getResponsable().getNom()+ " " +filiere.getResponsable().getPrenom());

        System.out.println("");}
    }
    public static void createFiliere(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'intitulé :");
        String intitule = scanner.nextLine();
        showDepartements();
        System.out.println("Sélecionnez un departement par id :");
        int id = scanner.nextInt();
        showEnseignants();
        System.out.println("Sélecionnez un enseignant par id :");
        int idEns = scanner.nextInt();
        addFiliere(intitule,getEnseignantById(idEns),getDepartementById(id));
        showFilieres();
        showMenu();
    }
    public static void editFiliere(){
        Scanner scanner = new Scanner(System.in);
        showFilieres();
        System.out.println("Sélecionnez un filiere par id :");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez l'intitulé :");
        String intitule = scanner.nextLine();
        showEnseignants();
        System.out.println("Sélecionnez un enseignant par id :");
        int idEns = scanner.nextInt();
        showDepartements();
        System.out.println("Sélecionnez un departement par id :");
        int iddept = scanner.nextInt();
        updateFiliere(id,intitule,getEnseignantById(idEns),getDepartementById(iddept));
        showFilieres();
        showMenu();
    }
    public static void removeFiliere(){
        Scanner scanner = new Scanner(System.in);
        showFilieres();
        System.out.println("Sélecionnez un filiere par id :");
        int id = scanner.nextInt();
        deleteFiliereById(id);
        showFilieres();
        showMenu();
    }
}


