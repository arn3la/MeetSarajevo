package com.arnela.meetsarajevoib140265.ListViewLokacije;

import android.app.Activity;
import android.opengl.Visibility;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arnela.meetsarajevoib140265.R;

import java.util.List;

import models.ObjektiVM;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ObjektiVM.ObjektiStavkaVM> mItemList;
    private IClickItem listener;
    private Activity mActivity;

    public RecyclerAdapter(Activity activity ,List<ObjektiVM.ObjektiStavkaVM> itemList, IClickItem listener) {
        mItemList = itemList;
        this.listener = listener;
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewitem, parent, false);
        return new RecyclerItemViewHolder(mActivity, view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        ObjektiVM.ObjektiStavkaVM itemObject = mItemList.get(position);

        holder.setObjektNaziv(itemObject.getNaziv());
        holder.setObjektKategorija(itemObject.getKategorija());
        holder.setObjektDescription(itemObject.getOpis());
        holder.setObjektSlika(itemObject.getSlika());
        holder.setProsjecnaOcjena(itemObject.getProsjecnaOcjena());
        holder.setObjektProsjecna(String.valueOf(itemObject.getProsjecnaOcjena()));

        if(itemObject.getProsjecnaOcjena() > 4.0) {
            holder.setObjektOcjenaVisibility(View.VISIBLE);
            holder.setObjektOcjena("Top preporuka");
        }
        else {
            holder.setObjektOcjenaVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}