package Services;

import Controllers.*;
import Models.Departement;
import Models.Enseignant;

import java.util.Scanner;

import static Services.EnseignantServices.enseignants;
import static Services.EnseignantServices.getEnsId;


public class Main {
    public static void showPrincipalMenu() {
        System.out.println("-------------------------[ Bienvenu ]---------------------------");


        System.out.println("1: Pour gérer les départements");
        System.out.println("2: Pour gérer les filières");
        System.out.println("3: Pour gérer les enseignants");
        System.out.println("4: Pour gérer les modules");
        System.out.println("5: Pour gérer les étudiants");
        System.out.println("0: Pour sortir");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner = new Scanner(System.in);

        int option = scanner.nextInt();
        switch (option) {
            case 1:
                DepartementsController.showMenu();
                break;
            case 2:
                FilieresController.showMenu();
                break;
            case 3:
                EnseignantsController.showMenu();
                break;
            case 4:
                ModulesController.showMenu();
                break;
            case 5:
                EtudiantsController.showMenu();

                break;
            default:
                System.out.println("Sortir");
        }}
        public static void main (String[]args) {
            Departement departement=new Departement();
            departement.setIntitule("Math");
            Enseignant enseignant = new Enseignant();
            enseignant.setNom("Achraf");
            enseignant.setPrenom("ben");
            enseignant.setEmail("tst@gmail.com");
            enseignant.setGrade("chef");
            enseignant.setDepartement(departement);
            enseignant.setId(getEnsId());
            enseignants.add(enseignant);
            showPrincipalMenu();




        }

    }

