package api;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;

import helper.MyApp;
import helper.MyRunnable;
import models.KorisniciLoginVM;
import models.KorisniciProvjeraVM;
import models.KorisniciRegistracijaVM;
import volley.MyVolley;

public class KorisniciApi {
    public static void Provjera(final Context context, final MyRunnable<KorisniciProvjeraVM> onSuccess, KorisniciLoginVM k) throws IOException {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Pristup podacima", "U toku");
        progressDialog.show();

        MyVolley.post(Config.Url + "api/korisnici/provjera", KorisniciProvjeraVM.class, new Response.Listener<KorisniciProvjeraVM>() {
                    // Ukoliko se uspjesno pozove servis
                    @Override
                    public void onResponse(KorisniciProvjeraVM response) {
                        progressDialog.dismiss();
                        onSuccess.run(response);
                    }
                },
                // Ukoliko se NEuspjesno pozove servis
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 409 || error.networkResponse.statusCode == 404) {
                                Toast.makeText(MyApp.getContext(), "Pristupni podaci nisu validani. ", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(MyApp.getContext(), "Dogodila se greška. " , Toast.LENGTH_SHORT).show();
                    }
                }, k
        );
    }


    public static void Registracija(final Context context, final MyRunnable<KorisniciRegistracijaVM> onSuccess, KorisniciRegistracijaVM korisnik) throws IOException {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Registracija korisnika", "U toku");
        progressDialog.show();

        MyVolley.post(Config.Url + "api/korisnici/registracija", KorisniciRegistracijaVM.class, new Response.Listener<KorisniciRegistracijaVM>() {
                    // Ukoliko se uspjesno pozove servis
                    @Override
                    public void onResponse(KorisniciRegistracijaVM response) {
                        progressDialog.dismiss();
                        onSuccess.run(response);
                    }
                },
                // Ukoliko se NEuspjesno pozove servis
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error.networkResponse.statusCode == 409) {
                            Toast.makeText(MyApp.getContext(), "Trenutni email već postoji. ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyApp.getContext(), "Dogodila se greška. " , Toast.LENGTH_SHORT).show();
                        }

                    }
                }, korisnik
        );
    }

    public static void PodesavanjeProfila(final Context context, final MyRunnable<KorisniciRegistracijaVM> onSuccess, int KorisnikId, KorisniciRegistracijaVM korisnik) throws IOException {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Podešavanje profila", "U toku");
        progressDialog.show();

        MyVolley.put(Config.Url + "api/korisnici/PodesavanjeProfila/" + KorisnikId, KorisniciRegistracijaVM.class, new Response.Listener<KorisniciRegistracijaVM>() {
                    // Ukoliko se uspjesno pozove servis
                    @Override
                    public void onResponse(KorisniciRegistracijaVM response) {
                        progressDialog.dismiss();
                        onSuccess.run(response);
                    }
                },
                // Ukoliko se NEuspjesno pozove servis
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error.networkResponse.statusCode == 409) {
                            Toast.makeText(MyApp.getContext(), "Trenutni email već postoji. ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyApp.getContext(), "Dogodila se greška. " , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, korisnik
        );
    }

}
