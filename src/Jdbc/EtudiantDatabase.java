package Jdbc;
import Models.Departement;
import Models.Enseignant;
import Models.Etudiant;
import Models.Filiere;
import Services.EtudiantServices;
import Services.FiliereServices;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Jdbc.FiliereDatabase.filiereExists;
import static Jdbc.FiliereDatabase.getAllFilieres;
import static Jdbc.Main2.showPrincipalMenu;
import static Services.EtudiantServices.*;
import static Services.FiliereServices.*;

public class EtudiantDatabase {
    public static void showMenu(){
        System.out.println("-------------------------[ Etudiants ]---------------------------");


        System.out.println("1: Pour ajouter un étudiant");
        System.out.println("2: Pour modifier un étudiant");
        System.out.println("3: Pour supprimer un étudiant");
        System.out.println("4: Pour afficher les étudiants");
        System.out.println("0: Pour retourner au menu principal");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                ajouterETD();
                break;
            case 2:
                modifierETD();
                break;
            case 3:
                supprimerETD();
                break;
            case 4:
             getAllEtudiants();
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

       public static void createEtudiantsTable() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "CREATE TABLE IF NOT EXISTS etudiants ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nom VARCHAR(255) NOT NULL,"
                    + "prenom VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) NOT NULL,"
                    + "apogee INT NOT NULL,"
                    + "filiere_id INT NOT NULL,"
                    + "FOREIGN KEY (filiere_id) REFERENCES filieres(id)"
                    + ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
                System.out.println("Table des etudiants créée avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertEtudiant(String nom, String prenom, String email, int apogee, int filiereId) {
        try (Connection connection = connect()) {
            String sql = "INSERT INTO etudiants (nom, prenom, email, apogee, filiere_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1,nom);
                statement.setString(2,prenom);
                statement.setString(3, email);
                statement.setInt(4, apogee);
                statement.setInt(5, filiereId);
                statement.executeUpdate();
                System.out.println("Étudiant ajouté avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEtudiant(int etudiantId, String nom, String prenom, String email, int apogee, int filiereId) {
        try (Connection connection = connect()) {
            String sql = "UPDATE etudiants SET nom = ?, prenom = ?, email = ?, apogee = ?, filiere_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1,nom);
                statement.setString(2, prenom);
                statement.setString(3, email);
                statement.setInt(4, apogee);
                statement.setInt(5, filiereId);
                statement.setInt(6, etudiantId);
                statement.executeUpdate();
                System.out.println("Étudiant mis à jour avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEtudiant(int etudiantId) {
        try (Connection connection = connect()) {
            String sql = "DELETE FROM etudiants WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, etudiantId);
                statement.executeUpdate();
                System.out.println("Étudiant supprimé avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllEtudiants() {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM etudiants";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("il n'existe aucun etudiant.");
                } else {
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Nom: " + resultSet.getString("nom"));
                    System.out.println("Prenom: " + resultSet.getString("prenom"));
                    System.out.println("Email: " + resultSet.getString("email"));
                    System.out.println("Apogee: " + resultSet.getInt("apogee"));
                    System.out.println("Filiere ID: " + resultSet.getInt("filiere_id"));
                    System.out.println("------------------------");
                }
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Etudiant getEtudiantById(int etudiantId) {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM etudiants WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, etudiantId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Etudiant etudiant = new Etudiant();
                        etudiant.setId(resultSet.getInt("id"));
                        etudiant.setNom(resultSet.getString("nom"));
                        etudiant.setPrenom(resultSet.getString("prenom"));
                        etudiant.setEmail(resultSet.getString("email"));
                        etudiant.setApogee(resultSet.getInt("apogee"));

                        int filiereId = resultSet.getInt("filiere_id");
                        Filiere filiere = getFiliereById(filiereId);
                        etudiant.setFiliere(filiere);
                        return etudiant;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean etudiantExists(int etudiantId) {
        try (Connection connection = connect()) {
            String sql = "SELECT id FROM etudiants WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, etudiantId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void ajouterETD(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom :");
        String Nom = scanner.nextLine();
        System.out.println("Entrez le Prenom :");
        String Prenom = scanner.nextLine();
        System.out.println("Entrez le Email :");
        String Email = scanner.nextLine();
        System.out.println("Entrez le apogee :");
        int apogee = scanner.nextInt();
        scanner.nextLine();
        int id;

        do {
            getAllFilieres();
            System.out.println("Sélectionnez un filiere par id :");
            id = scanner.nextInt();

            if (filiereExists(id)) {
                insertEtudiant(Nom, Prenom, Email, apogee, id);
                getAllEtudiants();
            } else {
                System.out.println("Id de filiere que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
            }
        } while (!filiereExists(id));
    }
    public static void modifierETD() {
        Scanner scanner = new Scanner(System.in);

        int etudiantId;
        int filiereId;

        do {
            getAllEtudiants();
            System.out.println("Sélectionnez un étudiant par id :");
            etudiantId = scanner.nextInt();
            scanner.nextLine();

            if (etudiantExists(etudiantId)) {
                System.out.println("Entrez le nom :");
                String Nom = scanner.nextLine();

                System.out.println("Entrez le prenom :");
                String Prenom = scanner.nextLine();

                System.out.println("Entrez l'Email :");
                String Email = scanner.nextLine();

                System.out.println("Entrez l'apogee :");
                int apogee = scanner.nextInt();
                scanner.nextLine();

                do {
                    getAllFilieres();
                    System.out.println("Sélectionnez un filière par id :");
                    filiereId = scanner.nextInt();

                    if (filiereExists(filiereId)) {
                        updateEtudiant(etudiantId, Nom, Prenom, Email, apogee, filiereId);
                        getAllEtudiants();
                    } else {
                        System.out.println("ID de filière que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                    }
                } while (!filiereExists(filiereId));
            } else {
                System.out.println("ID d'étudiant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
            }
        } while (!etudiantExists(etudiantId));
    }

    public static void supprimerETD(){
        Scanner scanner = new Scanner(System.in);
        int etudiantId;
        do{
        getAllEtudiants();
        System.out.println("Sélecionnez un etudiant par id :");
        etudiantId =scanner.nextInt();
        if(etudiantExists(etudiantId)){
        deleteEtudiant(etudiantId);
        getAllEtudiants();}
        else {System.out.println("Id d'étudiant que vous avez entré n'existe pas dans DB.");}
    }while (etudiantExists(etudiantId));

      }}



