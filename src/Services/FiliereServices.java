package Services;

import Models.Departement;
import Models.Enseignant;
import Models.Filiere;

import java.util.ArrayList;

public class FiliereServices {
    public static int FIL_ID = 0;
    public  static int getFilId(){
        FIL_ID++;
        return FIL_ID;
    }
    public static ArrayList<Filiere> filieres = new ArrayList<Filiere>();
    public static Filiere addFiliere(String intitule, Enseignant responsable, Departement departement) {
        Filiere filiere = new Filiere();
        filiere.setIntitule(intitule);
        filiere.setId(getFilId());
        filiere.setResponsable(responsable);
        filiere.setDepartement(departement);
         filieres.add(filiere);
        return  filiere;
    }

    public static Filiere updateFiliere(int id , String intitule, Enseignant responsable, Departement departement){
        for (Filiere filiere : filieres) {
            if (filiere.getId() == id) {
                filiere.setIntitule(intitule);
                filiere.setResponsable(responsable);
                filiere.setDepartement(departement);

                return filiere;
            }
        }
        return  new Filiere();
    }
    public static ArrayList<Filiere> deleteFiliereById(int id){
        filieres.remove(getFiliereById(id));
        return  filieres;
    }

    public static Filiere getFiliereById(int id){
        for (Filiere filiere : filieres) {
            if (filiere.getId() == id)
                return  filiere;
        }
        return  new Filiere();
    }

    public static ArrayList<Filiere> getAllFiliere(){
        return  filieres;
    }
}
