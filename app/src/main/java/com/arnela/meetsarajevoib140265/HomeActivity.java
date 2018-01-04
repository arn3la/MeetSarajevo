package com.arnela.meetsarajevoib140265;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.Toast;
import com.arnela.meetsarajevoib140265.ListViewLokacije.IClickItem;
import com.arnela.meetsarajevoib140265.ListViewLokacije.RecyclerAdapter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import api.ObjektiApi;
import helper.AppNavigationDrawer;
import helper.Constants;
import helper.IDrawerListener;
import helper.MyRunnable;
import helper.Sesija;
import models.ObjektiVM;

public class HomeActivity extends ActionBarActivity {

    private List<ObjektiVM.ObjektiStavkaVM> lokacije = new ArrayList<>();
    private List<ObjektiVM.ObjektiStavkaVM> lokacijeSearchSave = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private String mSelectedCategory = "ne";
    private AppNavigationDrawer drawer;
    private SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        recyclerView = (RecyclerView) findViewById(R.id.listaLokacija);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setLayoutParams(new Toolbar.LayoutParams(Gravity.END));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                lokacije.clear();

                if (newText == null || newText.equals("")) {
                    lokacije.addAll(lokacijeSearchSave);
                } else {
                    lokacije.addAll(getLokacijeBySearch(lokacijeSearchSave, newText));
                }

                recyclerAdapter.notifyDataSetChanged();

                return false;
            }
        });

        initRecyclerView();
        pullDataFromServer(this, Sesija.getLogiraniKorisnik().getKorisnikId(), "ne");

        drawer = new AppNavigationDrawer(this, toolbar, new IDrawerListener<Integer>() {
            @Override
            public void on(Integer arg) {

                switch (arg) {
                    case 1:
                        mSelectedCategory = "ne";
                        toolbar.setTitle(R.string.app_name);
                        break;
                    case 2: // HOTELI
                        mSelectedCategory = Constants.CATEGORY_HOTELS;
                        toolbar.setTitle(R.string.NavHoteli);
                        break;
                    case 3: // SOPING
                        mSelectedCategory = Constants.CATEGORY_SHOPPING;
                        toolbar.setTitle(R.string.NavCentri);
                        break;
                    case 4: // RESTORANI
                        mSelectedCategory = Constants.CATEGORY_RESTAURANTS;
                        toolbar.setTitle(R.string.NavRestorani);
                        break;
                    case 5: // ZNAMENITOSTI
                        mSelectedCategory = Constants.CATEGORY_ZNAMENITOSTI;
                        toolbar.setTitle(R.string.NavZnamenitosti);
                        break;
                    case 6: // SPORTSKE DVORANE
                        mSelectedCategory = Constants.CATEGORY_DVORANE;
                        toolbar.setTitle(R.string.NavDvorane);
                        break;
                    case 8:
                        mSelectedCategory = Constants.CATEGORY_FAVORITE;
                        toolbar.setTitle(R.string.NavOmiljeni);
                        break;
                }

                pullDataFromServer(HomeActivity.this, Sesija.getLogiraniKorisnik().getKorisnikId(), mSelectedCategory);
            }
        });

        drawer.InitLeftDrawer();
    }

    /**
     * Search method to filter locations by name
     *
     * @param source      list
     * @param searchQuery by what you want to search
     * @return will return new list source
     */
    private List<ObjektiVM.ObjektiStavkaVM> getLokacijeBySearch(List<ObjektiVM.ObjektiStavkaVM> source, String searchQuery) {

        List<ObjektiVM.ObjektiStavkaVM> result = new ArrayList<>();

        for (ObjektiVM.ObjektiStavkaVM item : source) {
            if (item.getNaziv() != null && item.getNaziv().toLowerCase().contains(searchQuery.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Get data from server
     */
    private void pullDataFromServer(final Activity activity, int KorisnikId, String category) {

        //dodjeliti iz baze
        try {
            ObjektiApi.Objekti(this, String.valueOf(KorisnikId), category, new MyRunnable<ObjektiVM>() {
                @Override
                public void run(ObjektiVM result) {
                    if (result == null)
                        Toast.makeText(activity, "Trenutno nema objekata za prikaz.", Toast.LENGTH_SHORT).show();
                    else {

                        lokacije.clear();
                        lokacije.addAll(result.listaObjekti);

                        lokacijeSearchSave.clear();
                        lokacijeSearchSave.addAll(lokacije);

                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize adapter for recyler view
     */
    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(this, lokacije, new IClickItem() {
            @Override
            public void onPositionClicked(int position) {

                ObjektiVM.ObjektiStavkaVM o = lokacije.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("stavkaObjekt", (Serializable) o);
                Intent in = new Intent(HomeActivity.this, DetaljiActivity.class);
                in.putExtras(bundle);
                startActivity(in);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!searchView.isIconified())
            searchView.onActionViewCollapsed();

        pullDataFromServer(HomeActivity.this, Sesija.getLogiraniKorisnik().getKorisnikId(), mSelectedCategory);

        // ukoliko dode do promjene prilikom podesavanja profila
        if (!Sesija.getLogiraniKorisnik().getIme().equals(drawer.getProfile().getName().toString())
                || !Sesija.getLogiraniKorisnik().getEmail().equals(drawer.getProfile().getEmail().toString())) {

            drawer.setProfile(Sesija.getLogiraniKorisnik().getEmail(), Sesija.getLogiraniKorisnik().getIme());
        }
    }
}
