package Services;

import Models.Enseignant;
import Models.Etudiant;
import Models.Filiere;

import java.util.ArrayList;

public class EtudiantServices {
    public static int ETD_ID = 0;
    public  static int getEtdId(){
        ETD_ID++;
        return ETD_ID;
    }
    public static ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
    public static Etudiant addEtd(String nom, String prenom, String email, int apogee,Filiere filiere){
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setApogee(apogee);
        etudiant.setEmail(email);
        etudiant.setId(getEtdId());
        etudiant.setFiliere(filiere);
        etudiants.add(etudiant);
        return etudiant;
    }

    public static Etudiant updateEtd(int id,String nom, String prenom, String email, int apogee, Filiere filiere){
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId() == id) {
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setEmail(email);
                etudiant.setApogee(apogee);
                etudiant.setFiliere(filiere);
                return etudiant;
            }
        }

        return  new Etudiant();
    }
    public static ArrayList<Etudiant> deleteEtudiantById(int id){
        etudiants.remove(getEtudiantdById(id));
        return  etudiants;
    }

    public static Etudiant getEtudiantdById(int id){
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId() == id)
                return  etudiant;
        }
        return  new Etudiant();
    }

    public static ArrayList<Etudiant> getAllEtd(){
        return  etudiants;
    }
}
