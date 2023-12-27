package Services;

import Models.Departement;
import Models.Enseignant;

import java.util.ArrayList;

public class DepartementServices {
    public static ArrayList<Departement> departements = new ArrayList<Departement>();
    public static int DEPT_ID = 0;

    public static int getDeptId() {
        DEPT_ID++;
        return DEPT_ID;
    }

    public static Departement addDepartement(String intitule, Enseignant responsable) {
        Departement departement = new Departement();
        departement.setIntitule(intitule);
        departement.setId(getDeptId());
        departement.setResponsable(responsable);
        departements.add(departement);

        return departement;
    }

    public static Departement updateDepartement(int id, String intitule, Enseignant responsable) {
        for (Departement departement : departements) {
            if (departement.getId() == id) {
                departement.setIntitule(intitule);
                departement.setResponsable(responsable);

                return departement;
            }
        }
        return new Departement();
    }
    public static ArrayList<Departement>  deleteDepartementById(int id){
        departements.remove(getDepartementById(id));
        return departements ;
    }

    public static Departement getDepartementById(int id){
        for (Departement departement : departements) {
            if (departement.getId() == id)
                return  departement;
        }
        return  new Departement();
    }

    public static ArrayList<Departement> getAllDept(){

        return  departements;
    }
}