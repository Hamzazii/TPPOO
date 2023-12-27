package Models;

import java.util.ArrayList;

public class Filiere {
    private int id;
    private String intitule;
    private Enseignant responsable;
    private Departement departement;

    ArrayList<Module> modules = new ArrayList<Module>();

    public Filiere() {
    }

    public Filiere(String intitule, Enseignant responsable, Departement departement) {
        this.intitule = intitule;
        this.responsable = responsable;
        this.departement = departement;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }


    public Enseignant getResponsable() {
        return responsable;
    }

    public void setResponsable(Enseignant responsable) {
        this.responsable = responsable;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
