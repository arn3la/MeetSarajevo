package models;

import java.io.Serializable;
import java.util.List;

public class ObjektiVM {

    public class ObjektiStavkaVM implements Serializable {
        private int Id;
        private String Naziv;
        private String Kategorija;
        private float ProsjecnaOcjena;
        private String Opis;
        private String Slika;
        private int OcjenaKorisnika;


        public String getNaziv() {
            return Naziv;
        }

        public String getKategorija() {
            return Kategorija;
        }

        public float getProsjecnaOcjena() {
            return ProsjecnaOcjena;
        }

        public String getOpis() {
            return Opis;
        }

        public String getSlika() {
            return Slika;
        }

        public int getId() {
            return Id;
        }

        public int getOcjenaKorisnika() {
            return OcjenaKorisnika;
        }

    }

    public List<ObjektiStavkaVM> listaObjekti;
}
