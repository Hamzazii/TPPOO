package Jdbc;

import Models.Departement;
import Models.Enseignant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Jdbc.DepartementDatabase.*;
import static Jdbc.Main2.showPrincipalMenu;
import static Services.EnseignantServices.getEnseignantById;

public class EnseignantDatabase {
        public static void showMenu() {
            System.out.println("-------------------------[ Enseignants ]---------------------------");


            System.out.println("1: Pour ajouter un enseignant");
            System.out.println("2: Pour modifier un enseignant");
            System.out.println("3: Pour supprimer un enseignant");
            System.out.println("4: Pour afficher les enseignants");
            System.out.println("0: Pour retourner au menu principal");

            System.out.println("Veuillez sélectionner une option : ");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    ajouterEnseignant();
                    break;
                case 2:
                    modifierEnseignant();
                    break;
                case 3:
                    supprimerEnseignant();
                    break;
                case 4:
                    getAllEnseignant();
                    break;
                default:
                    showPrincipalMenu();
            }
        }

        private static final String URL = "jdbc:mysql://localhost:3306/DB";
        private static final String USER = "root";
        private static final String PASSWORD = "admin";

        // Method to establish a connection to the database
        public static Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        public static void createEnseignantTable() {
            try (Connection connection = connect()) {
                String sql = "CREATE TABLE IF NOT EXISTS enseignants (id INT AUTO_INCREMENT PRIMARY KEY, nom VARCHAR(255), prenom VARCHAR(255), email VARCHAR(255), grade VARCHAR(255), departement_id INT)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                    System.out.println("Table des enseignants créée avec succès.");
                }
            } catch (SQLException e) {
                System.err.println("Error creating enseignants table:");
                e.printStackTrace();
            }
        }

        public static void insertEnseignant(String nom, String prenom, String email, String grade, int departementId) {
            try (Connection connection = connect()) {
                String sql = "INSERT INTO enseignants (nom, prenom, email, grade, departement_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nom);
                    statement.setString(2, prenom);
                    statement.setString(3, email);
                    statement.setString(4, grade);
                    statement.setInt(5, departementId);
                    statement.executeUpdate();
                    System.out.println("Enseignant ajouté avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void updateEnseignant(int enseignantId, String nom, String prenom, String email, String grade, int departementId) {
            try (Connection connection = connect()) {
                String sql = "UPDATE enseignants SET nom = ?, prenom = ?, email = ?, grade = ?, departement_id = ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nom);
                    statement.setString(2, prenom);
                    statement.setString(3, email);
                    statement.setString(4, grade);
                    statement.setInt(5, departementId);
                    statement.setInt(6, enseignantId);
                    statement.executeUpdate();
                    System.out.println("Enseignant mis à jour avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void deleteEnseignant(int enseignantId) {
            try (Connection connection = connect()) {
                String sql = "DELETE FROM enseignants WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, enseignantId);
                    statement.executeUpdate();
                    System.out.println("Enseignant supprimé avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public static void getAllEnseignant() {
            try (Connection connection = connect()) {
                String sql = "SELECT * FROM enseignants";
                try (PreparedStatement statement = connection.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("il n'existe aucun enseignant.");
                    } else {
                    while (resultSet.next()) {
                        System.out.println("ID: " + resultSet.getInt("id"));
                        System.out.println("Nom: " + resultSet.getString("nom"));
                        System.out.println("Prenom: " + resultSet.getString("prenom"));
                        System.out.println("Email: " + resultSet.getString("email"));
                        System.out.println("Grade: " + resultSet.getString("grade"));
                        System.out.println("Departement ID: " + resultSet.getInt("departement_id"));
                        System.out.println("------------------------");
                    }
                }}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static String getEnseignantById(int enseignantId) {
            try (Connection connection = connect()) {
                String sql = "SELECT * FROM enseignants WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, enseignantId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String nom = resultSet.getString("nom");
                            String prenom = resultSet.getString("prenom");
                            String email = resultSet.getString("email");
                            String grade = resultSet.getString("grade");
                            int departementId = resultSet.getInt("departement_id");

                            return String.format("ID: %d, Nom: %s, Prenom: %s, Email: %s, Grade: %s, Departement ID: %d", id, nom, prenom, email, grade, departementId);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Enseignant not found.";
        }
    public static boolean enseignantExists(int enseignantId) {
        try (Connection connection = connect()) {
            String sql = "SELECT id FROM enseignants WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, enseignantId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void ajouterEnseignant() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom :");
        String Nom = scanner.nextLine();
        System.out.println("Entrez le Prenom :");
        String Prenom = scanner.nextLine();
        System.out.println("Entrez le Email :");
        String Email = scanner.nextLine();
        System.out.println("Entrez grade :");
        String grade = scanner.nextLine();
        int idDept;
        do {
            getAllDepartements();
            System.out.println("Sélecionnez un departement par id :");
            idDept = scanner.nextInt();
            if (departementExists(idDept)) {
                insertEnseignant(Nom, Prenom, Email, grade, idDept);
                getAllEnseignant();
            } else System.out.println("Id de departement que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!departementExists(idDept));
    }
        public static void modifierEnseignant() {
            Scanner scanner = new Scanner(System.in);
            int id;
            int idDept;
            do {
                getAllEnseignant();
                System.out.println("Sélecionnez un enseignant par id :");
                 id = scanner.nextInt();
                scanner.nextLine();
                if (enseignantExists(id)) {
                    System.out.println("Entrez le nom :");
                    String Nom = scanner.nextLine();
                    System.out.println("Entrez le Prenom :");
                    String Prenom = scanner.nextLine();
                    System.out.println("Entrez le Email :");
                    String Email = scanner.nextLine();
                    System.out.println("Entrez grade :");
                    String grade = scanner.nextLine();
                    do {
                        getAllDepartements();
                        System.out.println("Sélecionnez un departement par id :");
                        idDept = scanner.nextInt();
                        if (departementExists(idDept)) {
                            updateEnseignant(id, Nom, Prenom, Email, grade, idDept);
                            getAllEnseignant();
                        } else System.out.println("Id de departement que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                    } while (!departementExists(idDept));
                } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
            } while (!enseignantExists(id));
        }

        public static void supprimerEnseignant() {
            Scanner scanner = new Scanner(System.in);
            int id;
            do {
                getAllEnseignant();
                System.out.println("Sélecionnez un enseignant par id :");
                 id = scanner.nextInt();
                if (enseignantExists(id)) {
                    deleteEnseignant(id);
                    getAllEnseignant();
                } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB.");
            } while (enseignantExists(id));
        }
    }
