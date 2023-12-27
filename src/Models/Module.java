package Models;

public class Module {
    private int id;
    private String intitule;
    private Enseignant responsable;
    private Filiere filiere;

    public Module() {
    }

    public Module(String intitule, Enseignant responsable, Filiere filiere) {
        this.intitule = intitule;
        this.responsable = responsable;
        this.filiere = filiere;
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

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
