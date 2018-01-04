package helper;

import android.content.SharedPreferences;

import models.KorisniciProvjeraVM;

public class Sesija {

    //SharedPreferences da bi korisnik ostao logiran i nakon gasenja aplikacije
    private static final String LOGIN = "DatotekaZaSharedPreferences";

    public static KorisniciProvjeraVM getLogiraniKorisnik() {

        SharedPreferences settings = MyApp.getContext().getSharedPreferences(LOGIN, 0);

        //obzirom da mora biti jedan od definisanih formata, koristi se json string od objekta
        String str = settings.getString("logiraniJson", "");
        if (str.length() == 0)
            return null;

        final KorisniciProvjeraVM kor = MyGson.build().fromJson(str, KorisniciProvjeraVM.class);

        return kor;
    }

    public static void setLogiraniKorisnik(KorisniciProvjeraVM logiraniKorisnik) {

        final String strJson = MyGson.build().toJson(logiraniKorisnik);
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("logiraniJson", strJson);

        editor.commit();
    }
}
