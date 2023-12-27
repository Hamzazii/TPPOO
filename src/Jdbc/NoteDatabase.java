package Jdbc;


    import java.sql.*;
    import java.util.Scanner;

    import static Jdbc.EtudiantDatabase.etudiantExists;
    import static Jdbc.EtudiantDatabase.getAllEtudiants;
    import static Jdbc.FiliereDatabase.getAllFilieres;
    import static Jdbc.Main2.showPrincipalMenu;
    import static Jdbc.ModuleDatabase.getAllModules;
    import static Jdbc.ModuleDatabase.moduleExists;

public class NoteDatabase {
    public static void showMenu(){
        System.out.println("-------------------------[ Notes ]---------------------------");


        System.out.println("1: Pour ajouter un note");
        System.out.println("2: Pour modifier un note");
        System.out.println("3: Pour supprimer un note");
        System.out.println("4: Pour afficher les notes");
        System.out.println("0: Pour retourner au menu principal");

        System.out.println("Veuillez sélectionner une option : ");
        Scanner scanner=new Scanner(System.in);
        int option =scanner.nextInt();
        switch(option) {
            case 1:
                ajouterNote();
                break;
            case 2:
                modifierNote();
                break;
            case 3:
                supprimerNote();
                break;
            case 4:
                getAllNotes();
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

        public static void createNoteTable(){
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String query = "CREATE TABLE IF NOT EXISTS notes (" +
                        "note FLOAT," +
                        "etudiant_id INT," +
                        "module_id INT," +
                        "PRIMARY KEY (etudiant_id, module_id)," +
                        "FOREIGN KEY (etudiant_id) REFERENCES etudiants(id)," +
                        "FOREIGN KEY (module_id) REFERENCES modules(id)" +
                        ")";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.executeUpdate();
                    System.out.println("Table des notes créée avec succès.");
                }
            }  catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        public static void insertNote(float note, int etudiantId, int moduleId) {
            try (Connection connection = connect()) {
                String sql  = "INSERT INTO notes (note, etudiant_id, module_id) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setFloat(1, note);
                statement.setInt(2, etudiantId);
                statement.setInt(3, moduleId);
                statement.executeUpdate();
                System.out.println("Note ajouté avec succès.");
            }
        }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void updateNote(int etudiantId, int moduleId, float newNote){
            try (Connection connection = connect()) {
            String sql = "UPDATE notes SET note = ? WHERE etudiant_id = ? AND module_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setFloat(1, newNote);
                statement.setInt(2, etudiantId);
                statement.setInt(3, moduleId);
                statement.executeUpdate();
                    System.out.println("Note mis à jour avec succès.");
            }
        }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void deleteNote(int etudiantId, int moduleId) {
            try (Connection connection = connect()) {
            String sql = "DELETE FROM notes WHERE etudiant_id = ? AND module_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, etudiantId);
                statement.setInt(2, moduleId);
                statement.executeUpdate();
                    System.out.println("Note supprimé avec succès.");
            }
        }catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public static void getAllNotes() {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM notes";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("il n'existe aucun note.");
                } else {
                while (resultSet.next()) {
                    System.out.println("EtudiantID: " + resultSet.getInt("etudiant_id"));
                    System.out.println("ModuleID: " + resultSet.getInt("module_id"));
                    System.out.println("Note: " + resultSet.getFloat("note"));
                    System.out.println("------------------------");
                }
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean noteExists(int etudiantId, int moduleId) {
        try (Connection connection = connect()) {
            String sql = "SELECT etudiant_id, module_id FROM notes WHERE etudiant_id = ? AND module_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, etudiantId);
                statement.setInt(2, moduleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void ajouterNote() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez la note :");
        Float Note = scanner.nextFloat();
        int idetd;
        int idmod;
        do {
            getAllEtudiants();
            System.out.println("Sélecionnez un etudiant par id :");
            idetd = scanner.nextInt();
            if (etudiantExists(idetd)) {
                do {
                    getAllModules();
                    System.out.println("Sélecionnez un module par id :");
                    idmod = scanner.nextInt();
                    if (moduleExists(idmod)) {
                        insertNote(Note, idetd, idmod);
                        getAllNotes();
                    } else System.out.println("Id de module que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
                }while (!moduleExists(idmod));
            } else System.out.println("Id d'etudiant que vous avez entré n'existe pas dans DB. Veuillez entrer un autre ID.");
        } while (!etudiantExists(idetd));
    }
    public static void modifierNote() {
        Scanner scanner = new Scanner(System.in);
        int etudiantId;
        int moduleId;
        do {
            getAllNotes();
            System.out.println("Sélecionnez un etudiant par id :");
            etudiantId = scanner.nextInt();
            System.out.println("Sélecionnez un module par id :");
            moduleId = scanner.nextInt();
            if (noteExists(etudiantId, moduleId)) {
                System.out.println("Entrez la note :");
                Float Note = scanner.nextFloat();
                updateNote(etudiantId, moduleId, Note);
                getAllNotes();
            } else System.out.println("Il n'y a aucune note lié avec ces deux Id dans DB. Veuillez entrer un autre ID.");
        } while (!noteExists(etudiantId, moduleId));
    }
    public static void supprimerNote() {
        Scanner scanner = new Scanner(System.in);
        int etudiantId;
        int moduleId;
        do {
            getAllNotes();
            System.out.println("Sélecionnez un etudiant par id :");
            etudiantId = scanner.nextInt();
            System.out.println("Sélecionnez un module par id :");
            moduleId = scanner.nextInt();
            if (noteExists(etudiantId, moduleId)) {
                deleteNote(etudiantId, moduleId);
                getAllNotes();
            } else System.out.println("Il n'y a aucune note lié avec ces deux Id dans DB. ");
        } while (noteExists(etudiantId, moduleId));
    }
    }


