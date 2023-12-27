package Jdbc;

import Models.Departement;
import Models.Enseignant;
import Models.Filiere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import static Jdbc.EnseignantDatabase.enseignantExists;
import static Jdbc.EnseignantDatabase.getAllEnseignant;
import static Jdbc.Main2.showPrincipalMenu;
import static Services.EnseignantServices.getEnseignantById;

public class DepartementDatabase {
    public static void showMenu(){
System.out.println("-------------------------[ Departements ]---------------------------");


        System.out.println("1: Pour ajouter un departement");
        System.out.println("2: Pour modifier un departement");
        System.out.println("3: Pour supprimer un departement");
        System.out.println("4: Pour afficher les departements");
        System.out.println("0: Pour retourner au menu principal");

        System.out.println("Veuillez sélectionner une option : ");
    Scanner scanner=new Scanner(System.in);
    int option =scanner.nextInt();
        switch(option) {
        case 1:
            ajouterDepartement();
            break;
        case 2:
            modifierDepartement();
            break;
        case 3:
            supprimerDepartement();
            break;
        case 4:
            getAllDepartements();
            break;
        default:
            showPrincipalMenu();
    }}
        private static final String URL = "jdbc:mysql://localhost:3306/DB";
        private static final String USER = "root";
        private static final String PASSWORD = "admin";

        // Méthode pour établir la connexion à la base de données
        public static Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        public static void createDepartementTable() {
            try (Connection connection = connect()) {
                String sql = "CREATE TABLE IF NOT EXISTS departements (id INT AUTO_INCREMENT PRIMARY KEY, intitule VARCHAR(255), responsable_id INT, FOREIGN KEY (responsable_id) REFERENCES enseignants(id))";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                    System.out.println("Table des départements créée avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void insertDepartement(String intitule, int responsableId) {
            try (Connection connection = connect()) {
                String sql = "INSERT INTO departements (intitule, responsable_id) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, intitule);
                    statement.setInt(2, responsableId);
                    statement.executeUpdate();
                    System.out.println("Département ajouté avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void updateDepartement(int departementId, String intitule, int responsableId) {
            try (Connection connection = connect()) {
                String sql = "UPDATE departements SET intitule = ?, responsable_id = ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, intitule);
                    statement.setInt(2, responsableId);
                    statement.setInt(3, departementId);
                    statement.executeUpdate();
                    System.out.println("Departement mis à jour avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void deleteDepartement(int departementId) {
            try (Connection connection = connect()) {
                String sql = "DELETE FROM departements WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, departementId);
                    statement.executeUpdate();
                    System.out.println("Département supprimé avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public static void getAllDepartements() {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM departements";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("il n'existe aucun departement.");
                } else {
                while (resultSet.next()) {
                    System.out.println("Id: " + resultSet.getInt("id"));
                    System.out.println("Intitule : " + resultSet.getString("intitule"));
                    System.out.println("Responsable: " + resultSet.getInt("responsable_id"));
                    System.out.println("------------------------");
                }
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static Departement getDepartementById(int departementId) {
            try (Connection connection = connect()) {
                String sql = "SELECT * FROM departements WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, departementId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            Departement departement = new Departement();
                            departement.setId(resultSet.getInt("id"));
                            departement.setIntitule(resultSet.getString("intitule"));

                            int responsableId = resultSet.getInt("responsable_id");
                            Enseignant responsable = getEnseignantById(responsableId);

                            departement.setResponsable(responsable);
                            return departement;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    public static boolean departementExists(int departementId) {
        try (Connection connection = connect()) {
            String sql = "SELECT id FROM departements WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, departementId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void ajouterDepartement() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez intitule :");
        String intitule = scanner.nextLine();
        int idEns;
        do {
            getAllEnseignant();
            System.out.println("Sélecionnez un enseignant par id :");
            idEns = scanner.nextInt();
            scanner.nextLine();
            if (enseignantExists(idEns)) {
                insertDepartement(intitule, idEns);
                getAllDepartements();
            } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!enseignantExists(idEns));
    }
    public static void modifierDepartement() {
        Scanner scanner = new Scanner(System.in);
        int id;
        int idEns;
        do {
            getAllDepartements();
            System.out.println("Sélecionnez un departement par id :");
            id = scanner.nextInt();
            scanner.nextLine();
            if (departementExists(id)) {
                System.out.println("Entrez intitule :");
                String intitule = scanner.nextLine();
                do {
                    getAllEnseignant();
                    System.out.println("Sélecionnez un enseignant par id :");
                    idEns = scanner.nextInt();
                    if (enseignantExists(idEns)) {
                        updateDepartement(id, intitule, idEns);
                        getAllDepartements();
                    } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                }while (!enseignantExists(idEns));
            } else System.out.println("Id de departement que vous avez  entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!departementExists(id));
    }
    public static void supprimerDepartement() {
        Scanner scanner = new Scanner(System.in);
        int id;
        do {
            getAllDepartements();
            System.out.println("Sélecionnez un departement par id :");
            id = scanner.nextInt();
            if (departementExists(id)) {
                deleteDepartement(id);
                getAllDepartements();
            } else System.out.println("Id de departement que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!departementExists(id));
    }
        }
