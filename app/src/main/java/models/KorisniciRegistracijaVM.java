package models;

public class KorisniciRegistracijaVM {

    private int Id;
    private String Ime;
    private String Email;
    private String Lozinka;

    public int getKorisnikId() {
        return Id;
    }

    public String getIme() {
        return Ime;
    }

    public String getEmail() {
        return Email;
    }

    public String getLozinka() {
        return Lozinka;
    }


    public void setIme(String ime) {
        Ime = ime;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setLozinka(String lozinka) {
        Lozinka = lozinka;
    }

    public void setKorisnikId(int korisnikId) {
        Id = korisnikId;
    }

}
