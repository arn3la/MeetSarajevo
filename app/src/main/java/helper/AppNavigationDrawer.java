package helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.arnela.meetsarajevoib140265.DetaljiActivity;
import com.arnela.meetsarajevoib140265.HomeActivity;
import com.arnela.meetsarajevoib140265.ListViewLokacije.IClickItem;
import com.arnela.meetsarajevoib140265.ListViewLokacije.RecyclerAdapter;
import com.arnela.meetsarajevoib140265.LoginActivity;
import com.arnela.meetsarajevoib140265.PodesavanjeProfilaActivity;
import com.arnela.meetsarajevoib140265.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.Serializable;
import java.util.List;

import models.ObjektiVM;


public class AppNavigationDrawer {

    private Activity mActivity = null;
    private Toolbar mToolbar = null;
    private IDrawerListener mListener = null;
    private ProfileDrawerItem profile = null;
    private AccountHeader headerResult;

    public AppNavigationDrawer(Activity activity, Toolbar toolbar, IDrawerListener<Integer> onClickMenuItem) {

        mActivity = activity;
        mToolbar = toolbar;
        mListener = onClickMenuItem;
        profile = new ProfileDrawerItem().withName(Sesija.getLogiraniKorisnik().getIme()).withEmail(Sesija.getLogiraniKorisnik().getEmail()).withIdentifier(1);
    }

    public void InitLeftDrawer() {

        headerResult = new AccountHeaderBuilder()
                .withActivity(mActivity)
                .withHeaderBackground(R.color.primary)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .withProfileImagesVisible(false)
                .build();

        new DrawerBuilder()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.NavHome).withIcon(R.drawable.ic_home_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavHoteli).withIcon(R.drawable.ic_hotel_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavCentri).withIcon(R.drawable.ic_local_mall_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavRestorani).withIcon(R.drawable.ic_restaurant_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavZnamenitosti).withIcon(R.drawable.ic_location_city_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavDvorane).withIcon(R.drawable.ic_fitness_center_24dp),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.NavOmiljeni).withIcon(R.drawable.ic_favorite_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavPodesavanje).withIcon(R.drawable.ic_build_24dp),
                        new PrimaryDrawerItem().withName(R.string.NavOdjava).withIcon(R.drawable.ic_logout)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 8:
                                mListener.on(position);
                                break;
                            case 7:
                                break;
                            case 9:
                                mActivity.startActivity(new Intent(mActivity, PodesavanjeProfilaActivity.class));
                                break;
                            case 10:
                                Sesija.setLogiraniKorisnik(null);
                                mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
                                mActivity.finish();

                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    // poslije podesavanja profila, da se u draweru promjene detalji o profilu
    public ProfileDrawerItem getProfile() {
        return profile;
    }

    public void setProfile(String email, String name) {

        profile = new ProfileDrawerItem().withEmail(email).withName(name).withIdentifier(1);
        headerResult.updateProfileByIdentifier(profile);
    }


}
