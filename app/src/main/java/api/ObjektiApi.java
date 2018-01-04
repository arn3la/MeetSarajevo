package api;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;

import helper.MyApp;
import helper.MyRunnable;
import models.ObjektiVM;
import models.StarRatingVM;
import volley.MyVolley;

public class ObjektiApi {

    public static void Objekti(final Context context, final String KorisnikId, final String kategorija, final MyRunnable<ObjektiVM> onSuccess) throws IOException {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Pristup podacima", "U toku");
        progressDialog.show();

        MyVolley.get(Config.Url + "api/objekti/objekti", ObjektiVM.class, new Response.Listener<ObjektiVM>() {
                    // Ukoliko se uspjesno pozove servis
                    @Override
                    public void onResponse(ObjektiVM response) {
                        progressDialog.dismiss();
                        onSuccess.run(response);
                    }
                },
                // Ukoliko se NEuspjesno pozove servis
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MyApp.getContext(), "Dogodila se greška. ", Toast.LENGTH_SHORT).show();
                    }
                },
                new BasicNameValuePair("KorisnikId", KorisnikId),
                new BasicNameValuePair("kategorija", kategorija)
        );
    }

    //STAR SYSTEM
    public static void OcjeniObjekt(final Context context, final MyRunnable<StarRatingVM> onSuccess, StarRatingVM object) throws IOException {

        MyVolley.post(Config.Url + "api/objekti/OcjeniObjekt", StarRatingVM.class, new Response.Listener<StarRatingVM>() {
                    // Ukoliko se uspjesno pozove servis
                    @Override
                    public void onResponse(StarRatingVM response) {
                        onSuccess.run(response);
                    }
                },
                // Ukoliko se NEuspjesno pozove servis
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyApp.getContext(), "Dogodila se greška. ", Toast.LENGTH_SHORT).show();
                    }
                }, object
        );
    }
}
