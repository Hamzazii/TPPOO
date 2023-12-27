package Services;

import Models.Departement;
import Models.Enseignant;
import Models.Filiere;
import Models.Module;

import java.util.ArrayList;

public class ModuleServices {
    public static int MOD_ID = 0;
    public  static int getModId(){
        MOD_ID++;
        return MOD_ID;
    }
    public static ArrayList<Module> modules = new ArrayList<Module>();
    public static Module addModule(String intitule, Enseignant responsable, Filiere filiere) {
        Module module = new Module();
        module.setIntitule(intitule);
        module.setId(getModId());
        module.setResponsable(responsable);
        module.setFiliere(filiere);
        modules.add(module);
        return  module;
    }

    public static Module updateModule(int id , String intitule, Enseignant responsable,  Filiere filiere){
        for (Module module : modules) {
            if (module.getId() == id) {
                module.setIntitule(intitule);
                module.setResponsable(responsable);
                module.setFiliere(filiere);

                return module;
            }
        }
        return  new Module();
    }
    public static ArrayList<Module> deleteModuleById(int id){
        modules.remove(getModuleById(id));
        return  modules;
    }

    public static Module getModuleById(int id){
        for (Module module : modules) {
            if (module.getId() == id)
                return  module;
        }
        return  new Module();
    }

    public static ArrayList<Module> getAllModule(){
        return  modules;
    }
}
