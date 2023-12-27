package Services;

import Models.Departement;
import Models.Enseignant;

import java.util.ArrayList;

public class EnseignantServices {
    public static int ENS_ID = 0;
    public  static int getEnsId(){
        ENS_ID++;
        return ENS_ID;
    }
    public static ArrayList<Enseignant> enseignants = new ArrayList<Enseignant>();
    public static Enseignant addEnseignant(String nom,String prenom,String email,String grade, Departement departement) {
        Enseignant enseignant = new Enseignant();
        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setGrade(grade);
        enseignant.setEmail(email);
        enseignant.setId(getEnsId());
        enseignant.setDepartement(departement);
        enseignants.add(enseignant);

        return new Enseignant();
    }

    public static Enseignant updateEnseignant(int id, String nom,String prenom,String email,String grade, Departement departement) {
        for (Enseignant enseignant : enseignants) {
            if (enseignant.getId() == id) {
                enseignant.setNom(nom);
                enseignant.setPrenom(prenom);
                enseignant.setEmail(email);
                enseignant.setGrade(grade);
                enseignant.setDepartement(departement);
                return enseignant;
            }
        }
        return new Enseignant();
    }
    public static ArrayList<Enseignant>  deleteEnseignantById(int id){
        enseignants.remove(getEnseignantById(id));
        return enseignants ;
    }

    public static Enseignant getEnseignantById(int id){
        for (Enseignant enseignant : enseignants) {
            if (enseignant.getId() == id)
                return  enseignant;
        }
        return  new Enseignant();
    }

    public static ArrayList<Enseignant> getAllDept(){

        return  enseignants;
    }
}
