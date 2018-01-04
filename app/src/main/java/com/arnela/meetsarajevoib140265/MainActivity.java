package com.arnela.meetsarajevoib140265;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import helper.Sesija;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Sesija.getLogiraniKorisnik() == null){
            startActivity(new Intent(this,LoginActivity.class));
        }
        else{
            startActivity(new Intent(this,HomeActivity.class));
        }
    }
}
