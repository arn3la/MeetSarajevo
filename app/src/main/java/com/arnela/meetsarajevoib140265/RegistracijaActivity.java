package com.arnela.meetsarajevoib140265;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import api.KorisniciApi;
import helper.MyRunnable;
import helper.Sesija;
import models.KorisniciProvjeraVM;
import models.KorisniciRegistracijaVM;

public class RegistracijaActivity extends ActionBarActivity {
    private EditText edtEmail;
    private EditText edtLozinka;
    private EditText edtIme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        edtIme = (EditText) findViewById(R.id.edtRegIme);
        edtEmail = (EditText) findViewById(R.id.edtRegEmail);
        edtLozinka = (EditText) findViewById(R.id.edtRegLozinka);
        TextView btnLogin = (TextView) findViewById(R.id.btnLog);
        Button btnRegister = (Button) findViewById(R.id.btnRegistrujse);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    do_Reg_Click();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistracijaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void do_Reg_Click() throws IOException {
        KorisniciRegistracijaVM k = new KorisniciRegistracijaVM();
        if (edtIme.getText().toString().equals("") || edtIme.getText().toString().length() < 2) {
            Toast.makeText(RegistracijaActivity.this, "Ime je obavezno polje.", Toast.LENGTH_SHORT).show();
            return;
        } else k.setIme(edtIme.getText().toString());

        if (edtEmail.getText().toString().equals("")) {
            Toast.makeText(RegistracijaActivity.this, "Email je obavezno polje.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            Toast.makeText(RegistracijaActivity.this, "Email nije u validnom formatu.", Toast.LENGTH_SHORT).show();
            return;
        } else k.setEmail(edtEmail.getText().toString());

        if (edtLozinka.getText().toString().equals("")) {
            Toast.makeText(RegistracijaActivity.this, "Lozinka je obavezno polje.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!edtLozinka.getText().toString().equals("") && edtLozinka.getText().toString().length() < 6) {
            Toast.makeText(RegistracijaActivity.this, "Lozinka mora sadržavati minimalno 6 karaktera.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!edtLozinka.getText().toString().equals("") && edtLozinka.getText().toString().length() > 6)
            k.setLozinka(edtLozinka.getText().toString());

        k.setKorisnikId(0);

        KorisniciApi.Registracija(this, new MyRunnable<KorisniciRegistracijaVM>() {
            @Override
            public void run(KorisniciRegistracijaVM result) {
                KorisniciProvjeraVM k = new KorisniciProvjeraVM();
                k.setIme(result.getIme());
                k.setEmail(result.getEmail());
                k.setKorisnikId(result.getKorisnikId());
                Sesija.setLogiraniKorisnik(k);
                Toast.makeText(RegistracijaActivity.this, "Uspješno ste se registrovali.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistracijaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, k);
    }


}
