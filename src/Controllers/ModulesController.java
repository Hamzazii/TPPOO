package Controllers;

import Models.Departement;
import Models.Module;

import java.util.Scanner;

import static Controllers.EnseignantsController.showEnseignants;
import static Controllers.FilieresController.showFilieres;
import static Services.DepartementServices.*;
import static Services.DepartementServices.deleteDepartementById;
import static Services.EnseignantServices.getEnseignantById;
import static Services.FiliereServices.getFiliereById;
import static Services.Main.showPrincipalMenu;
import static Services.ModuleServices.*;

public class ModulesController {
    public static void showMenu(){
        System.out.println("-------------------------[ Modules ]---------------------------");


        System.out.println("1: Pour ajouter un module");
        System.out.println("2: Pour afficher les modules");
        System.out.println("3: Pour modifier un module");
        System.out.println("4: Pour supprimer un module");
        System.out.println("5: Pour retourner au menu principal");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
              createModule();
                break;
            case 2:
                showModules();
                break;
            case 3:
             editModule();
                break;
            case 4:
                removeModule();
                break;
            case 5:
                showPrincipalMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }}



    private static void showModules(){
        for (Module module : modules) {
            System.out.print("Id : " + module.getId());
            System.out.print(" | Intitulé : " + module.getIntitule());
            System.out.print(" | Filiere : " + module.getFiliere().getIntitule());
            System.out.print(" | Responsable : " + module.getResponsable().getId());

        System.out.println("");}
    }
    public static void createModule(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'intitulé :");
        String intitule = scanner.nextLine();
        showEnseignants();
        System.out.println("Sélecionnez un enseignant par id :");
        int idEns = scanner.nextInt();
        showFilieres();
        System.out.println("Sélecionnez un filiere par id :");
        int idfil = scanner.nextInt();
        addModule(intitule,getEnseignantById(idEns),getFiliereById(idfil));
        showModules();
        showMenu();
    }
    public static void editModule(){
        Scanner scanner = new Scanner(System.in);
        showModules();
        System.out.println("Sélecionnez un module par id :");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez l'intitulé :");
        String intitule = scanner.nextLine();
        showEnseignants();
        System.out.println("Sélecionnez un enseignant par id :");
        int idEns = scanner.nextInt();
        showFilieres();
        System.out.println("Sélecionnez un filiere par id :");
        int idfil = scanner.nextInt();
        updateModule(id,intitule,getEnseignantById(idEns),getFiliereById(idfil));
        showModules();
        showMenu();
    }
    public static void removeModule(){
        Scanner scanner = new Scanner(System.in);
        showModules();
        System.out.println("Sélecionnez un module par id :");
        int id = scanner.nextInt();
        deleteModuleById(id);
        showModules();
        showMenu();
    }


}
