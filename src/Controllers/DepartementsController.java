package Controllers;
import Models.Departement;
import Services.DepartementServices;
import com.sun.tools.javac.Main;


import java.util.Scanner;

import static Controllers.EnseignantsController.showEnseignants;
import static Services.DepartementServices.*;
import static Services.EnseignantServices.getEnseignantById;
import static Services.Main.showPrincipalMenu;

public class DepartementsController {
    public static void showMenu() {
        System.out.println("-------------------------[ Départements ]---------------------------");


        System.out.println("1: Pour ajouter un département");
        System.out.println("2: Pour afficher les départements");
        System.out.println("3: Pour modifier un département");
        System.out.println("4: Pour supprimer un département");
        System.out.println("5: Pour retourner au menu principal");


        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner = new Scanner(System.in);

        int option = scanner.nextInt();
        switch (option) {
            case 1:
                createDepartement();
                break;
            case 2:
                showDepartements();
                break;
            case 3:
                 editDepartement();
                break;
            case 4:
                  removeDepartement();
                break;
            case 5:
                showPrincipalMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }




    public static void showDepartements() {
        for (Departement departement : departements) {
            System.out.print("Id : " + departement.getId());
            System.out.print(" | Intitulé : " + departement.getIntitule());
            System.out.print(" | Responsable : " + departement.getResponsable().getNom() + " " + departement.getResponsable().getPrenom());

            System.out.println("");
        }
    }
        public static void createDepartement(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez l'intitulé :");
            String intitule = scanner.nextLine();
            showEnseignants();
            System.out.println("Sélecionnez un enseignant par id :");
            int id = scanner.nextInt();
            addDepartement(intitule,getEnseignantById(id));
            showDepartements();
            showMenu();
        }
        public static void editDepartement(){
            Scanner scanner = new Scanner(System.in);
            showDepartements();
            System.out.println("Sélecionnez un departement par id :");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Entrez l'intitulé :");
            String intitule = scanner.nextLine();
            showEnseignants();
            System.out.println("Sélecionnez un enseignant par id :");
            int idEns = scanner.nextInt();
            updateDepartement(id,intitule,getEnseignantById(idEns));
            showDepartements();
            showMenu();
        }
        public static void removeDepartement(){
            Scanner scanner = new Scanner(System.in);
            showDepartements();
            System.out.println("Sélecionnez un departement par id :");
            int id = scanner.nextInt();
            deleteDepartementById(id);
            showDepartements();
           showMenu();
        }

        }





