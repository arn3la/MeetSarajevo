package com.arnela.meetsarajevoib140265;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import api.ObjektiApi;
import api.OmiljeniApi;
import helper.MyRunnable;
import helper.Sesija;
import models.ObjektiVM;
import models.OmiljeniAddVM;
import models.StarRatingVM;


public class DetaljiActivity extends ActionBarActivity {
    ObjektiVM.ObjektiStavkaVM stavkaObjekt;
    private RatingBar starOcjena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        stavkaObjekt = (ObjektiVM.ObjektiStavkaVM) getIntent().getSerializableExtra("stavkaObjekt");

        TextView ObjektNaziv = (TextView) findViewById(R.id.detalji_location_name);
        TextView ObjektKategorija = (TextView) findViewById(R.id.detalji_location_category);
        starOcjena = (RatingBar) findViewById(R.id.star);
        TextView ObjektOpis = (TextView) findViewById(R.id.detalji_location_Opis);
        //dodavanje scrolla
        ObjektOpis.setMovementMethod(new ScrollingMovementMethod());
        ImageView ObjektSlika = (ImageView) findViewById(R.id.detaljiSlika);
        FloatingActionButton buttonOmiljeni = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarDetalji);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ObjektNaziv.setText(stavkaObjekt.getNaziv());
        ObjektKategorija.setText(stavkaObjekt.getKategorija());
        ObjektOpis.setText(stavkaObjekt.getOpis());
        if (stavkaObjekt.getSlika() != null) {
            byte[] picture = android.util.Base64.decode(stavkaObjekt.getSlika(), stavkaObjekt.getSlika().length());
            Bitmap bm = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            ObjektSlika.setImageBitmap(bm);
        }

        starOcjena.setRating(stavkaObjekt.getOcjenaKorisnika());
        starOcjena.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                StarRatingVM m = new StarRatingVM();
                m.setKorisnikId(Sesija.getLogiraniKorisnik().getKorisnikId());
                m.setObjektiId(stavkaObjekt.getId());
                m.setOcjena((int) rating);

                try {
                    ObjektiApi.OcjeniObjekt(DetaljiActivity.this, new MyRunnable<StarRatingVM>() {
                        @Override
                        public void run(StarRatingVM result) {
                        }
                    }, m);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        buttonOmiljeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    do_omiljeni_Clicked();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void do_omiljeni_Clicked() throws IOException {
        OmiljeniAddVM k = new OmiljeniAddVM();
        k.setActive(true);
        k.setDatum(Calendar.getInstance().getTime());
        k.setKorisniciId(Sesija.getLogiraniKorisnik().getKorisnikId());
        k.setObjektiId(stavkaObjekt.getId());

        OmiljeniApi.OmiljeniAdd(this, new MyRunnable<OmiljeniAddVM>() {
            @Override
            public void run(OmiljeniAddVM result) {
                Toast.makeText(DetaljiActivity.this, "Uspješno ste dodali u omiljene.", Toast.LENGTH_SHORT).show();
            }
        }, k);
    }

    // toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
