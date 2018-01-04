package models;

/**
 * Created by Amar on 12/18/2017.
 */

public class KorisniciLoginVM {

    private String Email;
    private String Lozinka;

    public String getEmail() {
        return Email;
    }

    public String getLozinka() {
        return Lozinka;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setLozinka(String lozinka) {
        Lozinka = lozinka;
    }
}
