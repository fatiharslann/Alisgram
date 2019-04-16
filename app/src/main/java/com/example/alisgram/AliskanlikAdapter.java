package com.example.alisgram;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AliskanlikAdapter extends RecyclerView.Adapter<AliskanlikAdapter.MyViewHolder> {

    private ArrayList<ModelAliskanlik> mAliskanlikList;
    private LayoutInflater inflater;
    private DatabaseReference mDatabase;

    public AliskanlikAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar) {
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aliskanliklar");;
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

            /*deleteAliskanlik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String aliskanlikId = mAliskanlikList.get(getAdapterPosition()).getAliskanlikId();
                    mDatabase.child(aliskanlikId).removeValue();
                }
            });/*/
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
