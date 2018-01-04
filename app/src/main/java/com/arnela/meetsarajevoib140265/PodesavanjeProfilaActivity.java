package com.arnela.meetsarajevoib140265;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

import api.KorisniciApi;
import helper.MyRunnable;
import helper.Sesija;
import models.KorisniciProvjeraVM;
import models.KorisniciRegistracijaVM;

public class PodesavanjeProfilaActivity extends ActionBarActivity {

    private EditText edtEmail;
    private EditText edtLozinka;
    private EditText edtIme;
    private KorisniciProvjeraVM k;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podesavanjeprofila);

        edtIme = (EditText) findViewById(R.id.edtpodesavanjeIme);
        edtEmail = (EditText) findViewById(R.id.edtpodesavanjeEmail);
        edtLozinka = (EditText) findViewById(R.id.edtpodesavanjeLozinka);
        toolbar = (Toolbar) findViewById(R.id.toolbarPodesavanje);
        toolbar.setTitle("Podešavanje profila");

        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageButton btnSave = (ImageButton) findViewById(R.id.btnConfirm);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Gravity.END);
        params.setMargins(10, 10, 20, 10);
        btnSave.setLayoutParams(params);

        k = Sesija.getLogiraniKorisnik();
        if (k != null) {
            edtIme.setText(k.getIme().toString());
            edtEmail.setText(k.getEmail().toString());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    do_Podesavanje_Click();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void do_Podesavanje_Click() throws IOException {
        final KorisniciRegistracijaVM kor = new KorisniciRegistracijaVM();
        if (edtIme.getText().toString().equals("") || edtIme.getText().toString().length() < 2) {
            Toast.makeText(PodesavanjeProfilaActivity.this, "Ime je obavezno polje.", Toast.LENGTH_SHORT).show();
            return;
        } else kor.setIme(edtIme.getText().toString());
        if (edtEmail.getText().toString().equals("")) {
            Toast.makeText(PodesavanjeProfilaActivity.this, "Email je obavezno polje.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            Toast.makeText(PodesavanjeProfilaActivity.this, "Email nije u validnom formatu.", Toast.LENGTH_SHORT).show();
            return;
        } else kor.setEmail(edtEmail.getText().toString());

        if (edtLozinka.getText().toString().equals("")) kor.setLozinka("");
        else {
            if (!edtLozinka.getText().toString().equals("") && edtLozinka.getText().toString().length() < 6) {
                Toast.makeText(PodesavanjeProfilaActivity.this, "Lozinka mora sadržavati minimalno 6 karaktera.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!edtLozinka.getText().toString().equals("") && edtLozinka.getText().toString().length() > 6)
                kor.setLozinka(edtLozinka.getText().toString());
        }

        kor.setKorisnikId(k.getKorisnikId());


        KorisniciApi.PodesavanjeProfila(this, new MyRunnable<KorisniciRegistracijaVM>() {
            @Override
            public void run(KorisniciRegistracijaVM result) {

                KorisniciProvjeraVM sharedUpdateKorisnik = new KorisniciProvjeraVM();
                sharedUpdateKorisnik.setKorisnikId(kor.getKorisnikId());
                sharedUpdateKorisnik.setEmail(kor.getEmail());
                sharedUpdateKorisnik.setIme(kor.getIme());
                Sesija.setLogiraniKorisnik(sharedUpdateKorisnik);

                Toast.makeText(PodesavanjeProfilaActivity.this, "Uspješno ste izmijenili podatke.", Toast.LENGTH_SHORT).show();

            }
        }, kor.getKorisnikId(), kor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}




