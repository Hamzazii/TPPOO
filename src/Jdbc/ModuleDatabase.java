package Jdbc;

import Models.Etudiant;
import Models.Module;
import Services.EnseignantServices;
import Services.FiliereServices;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Jdbc.EnseignantDatabase.*;
import static Jdbc.FiliereDatabase.filiereExists;
import static Jdbc.FiliereDatabase.getAllFilieres;
import static Jdbc.Main2.showPrincipalMenu;

public class ModuleDatabase {
    public static void showMenu(){
        System.out.println("-------------------------[ Modules ]---------------------------");


        System.out.println("1: Pour ajouter un module");
        System.out.println("2: Pour modifier un module");
        System.out.println("3: Pour supprimer un module");
        System.out.println("4: Pour afficher les modules");
        System.out.println("0: Pour retourner au menu principal");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                ajouterModule();
                break;
            case 2:
                modifierModule();
                break;
            case 3:
                supprimerModule();
                break;
            case 4:
                getAllModules();
                break;
            default:
                showPrincipalMenu();
        }}
    private static final String URL = "jdbc:mysql://localhost:3306/DB";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    // Method to establish a connection to the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createModuleTable() {
        try (Connection connection = connect()) {
            String sql = "CREATE TABLE IF NOT EXISTS modules (id INT AUTO_INCREMENT PRIMARY KEY, intitule VARCHAR(255), filiere_id INT, professeur_id INT, FOREIGN KEY (filiere_id) REFERENCES filieres(id), FOREIGN KEY (professeur_id) REFERENCES enseignants(id))";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                System.out.println("Table des modules créée avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertModule(String intitule, int filiereId, int professeurId) {
        try (Connection connection = connect()) {
            String sql = "INSERT INTO modules (intitule, filiere_id, professeur_id) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, intitule);
                statement.setInt(2,filiereId );
                statement.setInt(3,professeurId);
                statement.executeUpdate();
                System.out.println("Module ajouté avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateModule(int moduleId, String intitule, int filiereId, int professeurId) {
        try (Connection connection = connect()) {
            String sql = "UPDATE modules SET intitule = ?, filiere_id = ?, professeur_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, intitule);
                statement.setInt(2, filiereId);
                statement.setInt(3, professeurId);
                statement.setInt(4, moduleId);
                statement.executeUpdate();
                System.out.println("Module mis à jour avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteModule(int moduleId) {
        try (Connection connection = connect()) {
            String sql = "DELETE FROM modules WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, moduleId);
                statement.executeUpdate();
                System.out.println("Module supprimé avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getAllModules() {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM modules";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("il n'existe aucun module.");
                } else {
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Intitule: " + resultSet.getString("intitule"));
                    System.out.println("Filiere ID: " + resultSet.getInt("filiere_id"));
                    System.out.println("ProfId: " + resultSet.getInt("professeur_id"));
                    System.out.println("------------------------");
                }
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getModuleById(int moduleId) {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM modules WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, moduleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String intitule = resultSet.getString("intitule");
                        int filiereId = resultSet.getInt("filiere_id");
                        int professeurId = resultSet.getInt("professeur_id");

                        return String.format("ID: %d, Intitule: %s, Filiere ID: %d, Professeur ID: %d", id, intitule, filiereId, professeurId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Module not found.";
    }
    public static boolean moduleExists(int moduleId) {
        try (Connection connection = connect()) {
            String sql = "SELECT id FROM modules WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, moduleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void ajouterModule() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez intitule :");
        String intitule = scanner.nextLine();
        int idFil;
        int idEns;
        do {
            getAllFilieres();
            System.out.println("Sélecionnez un filiere par id :");
            idFil = scanner.nextInt();
            if (filiereExists(idFil)) {
                do {
                    getAllEnseignant();
                    System.out.println("Sélecionnez un enseignant par id :");
                    idEns = scanner.nextInt();
                    if (enseignantExists(idEns)) {
                        insertModule(intitule, idFil, idEns);
                        getAllModules();
                    } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                }while (!enseignantExists(idEns));
            }else System.out.println("Id de filiere que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!filiereExists(idFil));
    }
    public static void modifierModule() {
        Scanner scanner = new Scanner(System.in);
        int id;
        int idFil;
        int idEns;
        do {
            getAllModules();
            System.out.println("Sélecionnez un module par id :");
             id = scanner.nextInt();
            scanner.nextLine();
            if (moduleExists(id)) {
                System.out.println("Entrez intitule :");
                String intitule = scanner.nextLine();
                do{
                getAllFilieres();
                System.out.println("Sélecionnez un filiere par id :");
                idFil = scanner.nextInt();
                if (filiereExists(idFil)) {
                    do {
                        getAllEnseignant();
                        System.out.println("Sélecionnez un enseignent par id :");
                        idEns = scanner.nextInt();
                        if (enseignantExists(idEns)) {
                            updateModule(id, intitule, idFil, idEns);
                            getAllModules();
                        } else System.out.println("Id d'enseignant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                    }while (!enseignantExists(idEns));
                } else System.out.println("Id de filiere que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
            }while (!filiereExists(idFil));
            } else System.out.println("Id de module que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!moduleExists(id));
    }
    public static void supprimerModule() {
        Scanner scanner = new Scanner(System.in);
        int id;
        do {
            getAllModules();
            System.out.println("Sélecionnez un module par id :");
            id = scanner.nextInt();
            if (moduleExists(id)) {
                deleteModule(id);
                getAllModules();
            } else System.out.println("Id de module que vous avez entré n'existe pas dans DB. ");
        } while (moduleExists(id));
    }
}
