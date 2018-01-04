package models;

/**
 * Created by Amar on 12/18/2017.
 */

public class StarRatingVM {
    private int KorisnikId;
    private int ObjektiId;
    private int Ocjena;

    public int getKorisnikId() {
        return KorisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        KorisnikId = korisnikId;
    }

    public int getObjektiId() {
        return ObjektiId;
    }

    public void setObjektiId(int objektiId) {
        ObjektiId = objektiId;
    }

    public int getOcjena() {
        return Ocjena;
    }

    public void setOcjena(int ocjena) {
        Ocjena = ocjena;
    }
}
