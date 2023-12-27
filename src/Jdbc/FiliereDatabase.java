package Jdbc;

import Models.Filiere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import static Jdbc.DepartementDatabase.*;
import static Jdbc.EnseignantDatabase.enseignantExists;
import static Jdbc.EnseignantDatabase.getAllEnseignant;
import static Jdbc.Main2.showPrincipalMenu;
import static Services.EnseignantServices.getEnseignantById;


public class FiliereDatabase {

    public static void showMenu(){
        System.out.println("-------------------------[ Filieres ]---------------------------");


        System.out.println("1: Pour ajouter un filiere");
        System.out.println("2: Pour modifier un filiere");
        System.out.println("3: Pour supprimer un filire");
        System.out.println("4: Pour afficher les filieres");
        System.out.println("0: Pour retourner au menu principal");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                ajouterFiliere();
                break;
            case 2:
                modifierFiliere();
                break;
            case 3:
                supprimerFiliere();
                break;
            case 4:
                getAllFilieres();
                break;
            default:
                showPrincipalMenu();
        }}
    private static final String URL = "jdbc:mysql://localhost:3306/DB";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void createFiliereTable() {
        try (Connection connection = connect()) {
            String sql = "CREATE TABLE IF NOT EXISTS filieres (id INT AUTO_INCREMENT PRIMARY KEY, intitule VARCHAR(255), responsable_id INT, departement_id INT, FOREIGN KEY (responsable_id) REFERENCES enseignants(id), FOREIGN KEY (departement_id) REFERENCES departements(id))";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                System.out.println("Table des filieres créée avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertFiliere(String intitule, int responsableId, int departementId) {
        try (Connection connection = connect()) {
            String sql = "INSERT INTO filieres (intitule, responsable_id, departement_id) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, intitule);
                statement.setInt(2, responsableId);
                statement.setInt(3, departementId);
                statement.executeUpdate();
                System.out.println("Filiere ajouté avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateFiliere(int filiereId, String intitule, int responsableId, int departementId) {
        try (Connection connection = connect()) {
            String sql = "UPDATE filieres SET intitule = ?, responsable_id = ?, departement_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, intitule);
                statement.setInt(2, responsableId);
                statement.setInt(3, departementId);
                statement.setInt(4, filiereId);
                statement.executeUpdate();
                System.out.println("Étudiant mis à jour avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFiliere(int filiereId) {
        try (Connection connection = connect()) {
            String sql = "DELETE FROM filieres WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, filiereId);
                statement.executeUpdate();
                System.out.println("Filiere supprimé avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllFilieres() {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM filieres";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("il n'existe aucun filiere.");
                } else {
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Intitule: " + resultSet.getString("intitule"));
                    System.out.println("Responsable: " + resultSet.getInt("responsable_id"));
                    System.out.println("Departement ID: " + resultSet.getInt("departement_id"));
                    System.out.println("------------------------");
                }
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getFiliereById(int filiereId) {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM filieres WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, filiereId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String intitule = resultSet.getString("intitule");
                        int responsableId = resultSet.getInt("responsable_id");
                        int departementId = resultSet.getInt("departement_id");

                        return String.format("ID: %d, Intitule: %s, Responsable ID: %d, Departement ID: %d", id, intitule, responsableId, departementId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Filiere not found.";
    }
    public static boolean filiereExists(int filiereId) {
        try (Connection connection = connect()) {
            String sql = "SELECT id FROM filieres WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, filiereId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void ajouterFiliere() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez intitule :");
        String intitule = scanner.nextLine();
        int idEns;
        int idDept;
        do {
            getAllEnseignant();
            System.out.println("Sélecionnez un enseignant par id :");
            idEns = scanner.nextInt();
            if (enseignantExists(idEns)) {
                do {
                    getAllDepartements();
                    System.out.println("Sélecionnez un departement par id :");
                    idDept = scanner.nextInt();
                    scanner.nextLine();
                    if (departementExists(idDept)) {
                        insertFiliere(intitule, idEns, idDept);
                        getAllFilieres();
                    } else System.out.println("Id de departement que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                }while (!enseignantExists(idEns));
            } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!enseignantExists(idEns));
    }
    public static void modifierFiliere() {
        Scanner scanner = new Scanner(System.in);
        int id;
        int idEns;
        int idDept;
        do {
            getAllFilieres();
            System.out.println("Sélecionnez un filiere par id :");
            id = scanner.nextInt();
            scanner.nextLine();
            if (filiereExists(id)) {
                System.out.println("Entrez intitule :");
                String intitule = scanner.nextLine();
                do {
                    getAllEnseignant();
                    System.out.println("Sélecionnez un enseignant par id :");
                    idEns = scanner.nextInt();
                    if (enseignantExists(idEns)) {
                        do {
                            getAllDepartements();
                            System.out.println("Sélecionnez un departement par id :");
                            idDept = scanner.nextInt();
                            if (departementExists(idDept)) {
                                updateFiliere(id, intitule, idEns, idDept);
                                getAllFilieres();
                            } else System.out.println("Id de departement que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                        }while (!departementExists(idDept));
                    } else System.out.println("Id d'enseignant que vous avez n'existe pas dans DB. Veuillez entrer un autre ID.");
                }while (!enseignantExists(idEns));
            } else System.out.println("Id de filiere que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");

        } while (!filiereExists(id));

    }
    public static void supprimerFiliere(){
        Scanner scanner = new Scanner(System.in);
        int filiereId;
        do{
        getAllFilieres();
        System.out.println("Sélecionnez un filiere par id :");
         filiereId =scanner.nextInt();
        if (filiereExists(filiereId)){
        deleteFiliere(filiereId);
        getAllFilieres();}
        else {System.out.println("Id de filiere que vous avez entré n'existe pas dans DB. ");}
    }while (filiereExists(filiereId));

}}







