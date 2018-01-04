package api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.IOException;

import helper.MyApp;
import helper.MyRunnable;
import models.OmiljeniAddVM;
import volley.MyVolley;

public class OmiljeniApi {
    public static void OmiljeniAdd(final Context context, final MyRunnable<OmiljeniAddVM> onSuccess, OmiljeniAddVM omiljeni) throws IOException {


        MyVolley.post(Config.Url + "api/omiljeni/omiljeniAdd", OmiljeniAddVM.class, new Response.Listener<OmiljeniAddVM>() {
                    // Ukoliko se uspjesno pozove servis
                    @Override
                    public void onResponse(OmiljeniAddVM response) {
                        onSuccess.run(response);
                    }
                },
                // Ukoliko se NEuspjesno pozove servis
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage().contains("Expected BEGIN_OBJECT but was STRING at line 1 column 2"))
                            Toast.makeText(MyApp.getContext(), "Objekt je već snimljen u omiljene. ", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MyApp.getContext(), "Dogodila se greška. " , Toast.LENGTH_SHORT).show();
                    }
                }, omiljeni
        );
    }
}

