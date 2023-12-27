package Controllers;

import Models.Departement;
import Models.Enseignant;


import Services.EnseignantServices;
import com.sun.tools.javac.Main;

import java.util.Scanner;

import static Controllers.DepartementsController.showDepartements;
import static Services.DepartementServices.*;
import static Services.DepartementServices.deleteDepartementById;
import static Services.EnseignantServices.*;
import static Services.Main.showPrincipalMenu;

public class EnseignantsController {
    public static void showMenu(){
        System.out.println("-------------------------[ Enseignants ]---------------------------");


        System.out.println("1: Pour ajouter un enseignant");
        System.out.println("2: Pour afficher les enseignants");
        System.out.println("3: Pour modifier un enseignant");
        System.out.println("4: Pour supprimer un enseignant");
        System.out.println("5: Pour retourner au menu principal");



        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                createEnseignant();
                break;
            case 2:
                showEnseignants();
                break;
            case 3:
                 editEnseignant();
                break;
            case 4:
                  removeEnseignant();
                break;
            case 5:
                showPrincipalMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }}
    public static void showEnseignants(){
        for (Enseignant enseignant : enseignants) {
            System.out.print("Id : " + enseignant.getId());
            System.out.print(" | Nom : " + enseignant.getNom());
            System.out.print(" | Prenom : " + enseignant.getPrenom());
            System.out.print(" | Grade : " + enseignant.getGrade());
            System.out.print(" | Email : " + enseignant.getEmail());
            System.out.print(" | Departement : " + enseignant.getDepartement().getIntitule());

        System.out.println("");
        }
    }
    public static void createEnseignant(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom :");
        String Nom = scanner.nextLine();
        System.out.println("Entrez le Prenom :");
        String Prenom = scanner.nextLine();
        System.out.println("Entrez le Email :");
        String Email = scanner.nextLine();
        System.out.println("Entrez le Grade :");
        String Grade = scanner.nextLine();
        showDepartements();
        System.out.println("Sélecionnez un departement par id :");
        int id = scanner.nextInt();
        addEnseignant(Nom,Prenom,Email,Grade,getDepartementById(id));
        showEnseignants();
        showMenu();
    }
    public static void editEnseignant(){
        Scanner scanner = new Scanner(System.in);
        showEnseignants();
        System.out.println("Sélecionnez un enseignant par id :");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez le nom :");
        String Nom = scanner.nextLine();
        System.out.println("Entrez le prenom :");
        String Prenom = scanner.nextLine();
        System.out.println("Entrez l'Email :");
        String Email = scanner.nextLine();
        System.out.println("Entrez la grade :");
        String Grade = scanner.nextLine();
        updateEnseignant(id,Nom,Prenom,Email,Grade,getDepartementById(id));
        showEnseignants();
        showMenu();
    }
    public static void removeEnseignant(){
        Scanner scanner = new Scanner(System.in);
        showEnseignants();
        System.out.println("Sélecionnez un enseignant par id :");
        int id = scanner.nextInt();
        deleteEnseignantById(id);
        showEnseignants();
        showMenu();
    }

}
