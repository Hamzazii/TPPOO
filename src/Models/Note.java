package Models;

public class Note {


        private float note;
        private Etudiant etudiant;
        private Module module;

        public Note() {
        }

        public Note(float note, Etudiant etudiant, Module module) {
            this.note = note;
            this.etudiant = etudiant;
            this.module = module;
        }

        public float getNote() {
            return note;
        }

        public void setNote(float note) {
            this.note = note;
        }

        public Etudiant getEtudiant() {
            return etudiant;
        }

        public void setEtudiant(Etudiant etudiant) {
            this.etudiant = etudiant;
        }

        public Module getModule() {
            return module;
        }

        public void setModule(Module module) {
            this.module = module;
        }
    }
