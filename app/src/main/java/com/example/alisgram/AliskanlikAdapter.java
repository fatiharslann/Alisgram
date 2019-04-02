package com.example.alisgram;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AliskanlikAdapter extends RecyclerView.Adapter<AliskanlikAdapter.MyViewHolder> {

    ArrayList<ModelAliskanlik> mAliskanlikList;
    LayoutInflater inflater;

    public AliskanlikAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar) {
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.aliskanlik_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelAliskanlik selectedProduct = mAliskanlikList.get(position);
        holder.setData(selectedProduct, position);

    }

    @Override
    public int getItemCount() {
        return mAliskanlikList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView aliskanlikEtiket, aliskanlikAciklama;
        ImageView deleteAliskanlik,editAliskanlik,gizlilikAliskanlik;

        public MyViewHolder(View itemView) {
            super(itemView);
            aliskanlikEtiket = (TextView) itemView.findViewById(R.id.profilAliskanlikAdi);
            aliskanlikAciklama = (TextView) itemView.findViewById(R.id.profilAliskanlikAciklama);
            deleteAliskanlik = (ImageView) itemView.findViewById(R.id.deleteAliskanlik);
            editAliskanlik = (ImageView) itemView.findViewById(R.id.editAliskanlik);
            gizlilikAliskanlik = (ImageView) itemView.findViewById(R.id.gizlilikAliskanlik);

        }

        public void setData(ModelAliskanlik selectedProduct, int position) {
            this.aliskanlikEtiket.setText(selectedProduct.getAliskanlikEtiket());
            this.aliskanlikAciklama.setText(selectedProduct.getAliskanlikDetay());
        }


        @Override
        public void onClick(View v) {


        }


    }
}
