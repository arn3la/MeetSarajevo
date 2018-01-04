package models;

import java.util.Date;

public class OmiljeniAddVM {
    private Date Datum;
    private boolean Active;
    private int KorisniciId;
    private int ObjektiId;

    public void setDatum(Date datum) {
        Datum = datum;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public void setKorisniciId(int korisniciId) {
        KorisniciId = korisniciId;
    }

    public void setObjektiId(int objektiId) {
        ObjektiId = objektiId;
    }
}
