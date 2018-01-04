package models;

public class KorisniciProvjeraVM {

    private int KorisnikId;
    private String Ime;
    private String Email;

    public void setKorisnikId(int korisnikId) {
        KorisnikId = korisnikId;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public int getKorisnikId() {
        return KorisnikId;
    }

    public String getIme() {
        return Ime;
    }

    public String getEmail() {
        return Email;
    }

}
