package com.example.alisgram;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;



public class AliskanlikAdapter extends RecyclerView.Adapter<AliskanlikAdapter.MyViewHolder> {

    private ArrayList<ModelAliskanlik> mAliskanlikList;
    private LayoutInflater inflater;
    private DatabaseReference mDatabase;
    private Context context;
    private int state;
    private FragmentManager manager;

    public AliskanlikAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar, FragmentManager manager,int state) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
        this.manager=manager;
        mDatabase = FirebaseDatabase.getInstance().getReference("aliskanliklar");
        this.state = state;
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

        public MyViewHolder(final View itemView) {
            super(itemView);
            aliskanlikEtiket = (TextView) itemView.findViewById(R.id.profilAliskanlikAdi);
            aliskanlikAciklama = (TextView) itemView.findViewById(R.id.profilAliskanlikAciklama);
            deleteAliskanlik = (ImageView) itemView.findViewById(R.id.deleteAliskanlik);
            editAliskanlik = (ImageView) itemView.findViewById(R.id.editAliskanlik);
            gizlilikAliskanlik = (ImageView) itemView.findViewById(R.id.gizlilikAliskanlik);
            if(state == 0){

                deleteAliskanlik.setVisibility(View.VISIBLE);
                editAliskanlik.setVisibility(View.VISIBLE);
                gizlilikAliskanlik.setVisibility(View.VISIBLE);

                gizlilikAliskanlik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAliskanlikList.get(getAdapterPosition()).isAliskanlikGizlilik()){
                            FirebaseHelper.setAliskanlikGizlilik(mAliskanlikList.get(getAdapterPosition()).getAliskanlikId(),false);
                            gizlilikAliskanlik.setImageResource(R.drawable.eye1);
                        }
                        else{
                            FirebaseHelper.setAliskanlikGizlilik(mAliskanlikList.get(getAdapterPosition()).getAliskanlikId(),true);
                            gizlilikAliskanlik.setImageResource(R.drawable.eye2);
                        }
                    }
                });

                deleteAliskanlik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new LovelyStandardDialog(context, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.red)
                                .setButtonsColorRes(R.color.colorBtn)
                                .setIcon(R.drawable.delete2)
                                .setTitle("Alışkanlık silinecek!")
                                .setMessage("Devam etmek istiyor musunuz?")
                                .setPositiveButton("Tamam", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String aliskanlikId = mAliskanlikList.get(getAdapterPosition()).getAliskanlikId();
                                        mDatabase.child(aliskanlikId).setValue(null);
                                        Toast.makeText(context, "Alışkanlık Silindi", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("İptal", null)
                                .show();
                    }
                });

                editAliskanlik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String aliskanlikId = mAliskanlikList.get(getAdapterPosition()).getAliskanlikId();
                        Intent intent = new Intent(context, ProfilAliskanlikGuncelle.class);
                        intent.putExtra("aliskanlikId",aliskanlikId);
                        context.startActivity(intent);
                    }
                });
            }else {
                deleteAliskanlik.setVisibility(View.INVISIBLE);
                editAliskanlik.setVisibility(View.INVISIBLE);
                gizlilikAliskanlik.setVisibility(View.INVISIBLE);
            }


        }

        public void setData(ModelAliskanlik selectedProduct, int position) {
            this.aliskanlikEtiket.setText(selectedProduct.getAliskanlikEtiket());
            this.aliskanlikAciklama.setText(selectedProduct.getAliskanlikDetay());
            if (mAliskanlikList.get(position).isAliskanlikGizlilik())
                this.gizlilikAliskanlik.setImageResource(R.drawable.eye2);
            else
                this.gizlilikAliskanlik.setImageResource(R.drawable.eye1);
        }


        @Override
        public void onClick(View v) {


        }


    }
}
