package com.example.alisgram;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class KesfetAdapter extends RecyclerView.Adapter<KesfetAdapter.MyViewHolder> {

    ArrayList<ModelAliskanlik> mAliskanlikList;
    LayoutInflater inflater;
    private Context mcon;

    public KesfetAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar) {
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
        mcon = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.kesfet_list_item, parent, false);
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

        TextView aliskanlikEtiket;
        RatingBar aliskanlikSeviye;

        public MyViewHolder(View itemView) {
            super(itemView);
            aliskanlikEtiket = (TextView) itemView.findViewById(R.id.kesfetAliskanlikAdi);
            aliskanlikSeviye = (RatingBar) itemView.findViewById(R.id.kesfetRatingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelAliskanlik paliskanlik = mAliskanlikList.get(getAdapterPosition());
                    String aliskanlikId = paliskanlik.getAliskanlikId();
                    String aliskanlikAdi = paliskanlik.getAliskanlikEtiket();
                    String aliskanlikKullanici = paliskanlik.getAliskanlikKullaniciId();
                    float paliskanlikSeviye = paliskanlik.getAliskanlikSeviye();

                    Intent intent = new Intent(mcon, AnasayfaPopupActivity.class);
                    intent.putExtra("aliskanlikId",aliskanlikId);
                    intent.putExtra("aliskanlikAdi",aliskanlikAdi);
                    intent.putExtra("aliskanlikSeviye",paliskanlikSeviye);
                    intent.putExtra("aliskanlikKullanici",aliskanlikKullanici);
                    mcon.startActivity(intent);
                }
            });
        }

        public void setData(ModelAliskanlik selectedProduct, int position) {
            this.aliskanlikEtiket.setText(selectedProduct.getAliskanlikEtiket());
            this.aliskanlikSeviye.setRating(selectedProduct.getAliskanlikSeviye());
        }


        @Override
        public void onClick(View v) {


        }


    }
}
