package Jdbc;

import Models.Departement;
import Models.Enseignant;

import java.util.Scanner;

import static Jdbc.DepartementDatabase.createDepartementTable;
import static Jdbc.DepartementDatabase.insertDepartement;
import static Jdbc.EnseignantDatabase.createEnseignantTable;
import static Jdbc.EnseignantDatabase.insertEnseignant;
import static Jdbc.EtudiantDatabase.createEtudiantsTable;
import static Jdbc.FiliereDatabase.createFiliereTable;
import static Jdbc.ModuleDatabase.createModuleTable;
import static Jdbc.NoteDatabase.createNoteTable;

public class Main2 {
    public static void showPrincipalMenu() {
        System.out.println("-------------------------[ Bienvenu ]---------------------------");


        System.out.println("1: Pour gérer les départements");
        System.out.println("2: Pour gérer les filières");
        System.out.println("3: Pour gérer les enseignants");
        System.out.println("4: Pour gérer les modules");
        System.out.println("5: Pour gérer les étudiants");
        System.out.println("6: Pour gérer les notes");
        System.out.println("0: Pour sortir");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner = new Scanner(System.in);

        int option = scanner.nextInt();
        switch (option) {
            case 1:
                DepartementDatabase.showMenu();
                break;
            case 2:
                FiliereDatabase.showMenu();
                break;
            case 3:
                EnseignantDatabase.showMenu();
                break;
            case 4:
                ModuleDatabase.showMenu();
                break;
            case 5:
                EtudiantDatabase.showMenu();
                break;
            case 6:
                NoteDatabase.showMenu();
                break;
            default:
                System.out.println("sortir");
        }}
    public static void main(String[] args) {
        createEnseignantTable();
        createDepartementTable();
        createFiliereTable();
        createModuleTable();
        createEtudiantsTable();
        createNoteTable();
        insertEnseignant("mehdi","ben","ben@gmail.com","chef",1);
        showPrincipalMenu();
    }
}
