package com.example.alisgram;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;



public class AliskanlikAdapter extends RecyclerView.Adapter<AliskanlikAdapter.MyViewHolder> {

    private ArrayList<ModelAliskanlik> mAliskanlikList;
    private LayoutInflater inflater;
    private DatabaseReference mDatabase;
    private Context context;

    public AliskanlikAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
        mDatabase = FirebaseDatabase.getInstance().getReference("aliskanliklar");
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

                                }
                            })
                            .setNegativeButton("İptal", null)
                            .show();
                }
            });

            editAliskanlik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
