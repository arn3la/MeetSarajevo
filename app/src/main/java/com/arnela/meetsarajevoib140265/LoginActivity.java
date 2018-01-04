package com.arnela.meetsarajevoib140265;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import api.KorisniciApi;
import helper.MyRunnable;
import helper.Sesija;
import models.KorisniciLoginVM;
import models.KorisniciProvjeraVM;

public class LoginActivity extends ActionBarActivity {

    private EditText edtEmail;
    private EditText edtLozinka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Sesija.getLogiraniKorisnik() != null) {
           startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtLozinka = (EditText) findViewById(R.id.edtLozinka);
        TextView btnRegister = (TextView) findViewById(R.id.btnRegistracija);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistracijaActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    do_btnLogin_click();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void do_btnLogin_click() throws IOException {

        if (edtEmail.getText().toString().equals("") || edtLozinka.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Unesite pristupne podatke.", Toast.LENGTH_SHORT).show();
            return;
        }

        final KorisniciLoginVM korisnik = new KorisniciLoginVM();
        korisnik.setEmail(edtEmail.getText().toString());
        korisnik.setLozinka(edtLozinka.getText().toString());

        KorisniciApi.Provjera(this, new MyRunnable<KorisniciProvjeraVM>() {
            @Override
            public void run(KorisniciProvjeraVM result) {

                if (result == null)
                    Toast.makeText(LoginActivity.this, "Pogrešan username ili password.", Toast.LENGTH_SHORT).show();
                else {
                    Sesija.setLogiraniKorisnik(result);
                    Toast.makeText(LoginActivity.this, "Dobrodošli " + result.getEmail()+"!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, korisnik);

    }
}
