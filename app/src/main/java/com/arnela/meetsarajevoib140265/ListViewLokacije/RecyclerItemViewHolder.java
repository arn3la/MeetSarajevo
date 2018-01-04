package com.arnela.meetsarajevoib140265.ListViewLokacije;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.arnela.meetsarajevoib140265.R;

import java.lang.ref.WeakReference;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView ObjektNaziv;
    private TextView ObjektKategorija;
    private TextView ObjektOcjena;
    private ImageView ObjektSlika;
    private RatingBar ProsjecnaOcjena;
    private WeakReference<IClickItem> listenerRef;
    private View parent;
    private Activity mActivity;

    public RecyclerItemViewHolder(Activity activity, final View parent, IClickItem listener) {
        super(parent);

        ObjektNaziv = (TextView) parent.findViewById(R.id.location_name);
        ObjektKategorija = (TextView) parent.findViewById(R.id.location_category);
        ObjektOcjena = (TextView) parent.findViewById(R.id.star_ocjena);
        ObjektSlika = (ImageView) parent.findViewById(R.id.img_location);
        ProsjecnaOcjena = (RatingBar) parent.findViewById(R.id.starProsjecna);
        listenerRef = new WeakReference<>(listener);
        parent.setOnClickListener(this);
        this.parent = parent;
        mActivity = activity;
    }

    public void setObjektNaziv(String objektNaziv) {
        ObjektNaziv.setText(objektNaziv);
    }

    public void setObjektKategorija(String objektKategorija) {
        ObjektKategorija.setText(objektKategorija);
    }

    public void setObjektOcjena(String objektOcjena) {
        ObjektOcjena.setText(objektOcjena);
    }

    public void setObjektSlika(final String objektSlika) {

        if (objektSlika != null) {
            byte[] picture = android.util.Base64.decode(objektSlika, objektSlika.length());
            Bitmap bm = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            ObjektSlika.setImageBitmap(bm);
        } else {
            ObjektSlika.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.no_image_place_holder));
        }
    }

    public void setProsjecnaOcjena(float prosjecnaOcjena) {
        ProsjecnaOcjena.setRating(prosjecnaOcjena);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == parent.getId()) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
