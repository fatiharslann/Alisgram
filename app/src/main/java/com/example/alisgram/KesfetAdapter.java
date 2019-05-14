package com.example.alisgram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class KesfetAdapter extends RecyclerView.Adapter<KesfetAdapter.MyViewHolder> {

    ArrayList<ModelAliskanlik> mAliskanlikList;
    ArrayList<ModelKullanici> mKullaniciList;
    LayoutInflater inflater;
    private Context mcon;

    public KesfetAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar, ArrayList<ModelKullanici> kullanicilar) {
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
        this.mKullaniciList = kullanicilar;
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

    public void updateList(ArrayList newList) {
        mAliskanlikList = new ArrayList<>();
        mAliskanlikList.addAll(newList);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView aliskanlikEtiket, kesfetKullaniciAdi, aliskanlikDetay;
        RatingBar aliskanlikSeviye;
        String kullaniciAdi = null;
        CircleImageView ivProfile;

        public MyViewHolder(View itemView) {
            super(itemView);
            aliskanlikEtiket = itemView.findViewById(R.id.kesfetAliskanlikAdi);
            aliskanlikDetay = itemView.findViewById(R.id.kesfetAliskanlikDetay);
            aliskanlikSeviye = itemView.findViewById(R.id.kesfetRatingBar);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            kesfetKullaniciAdi = itemView.findViewById(R.id.kesfetKullaniciAdi);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelAliskanlik paliskanlik = mAliskanlikList.get(getAdapterPosition());
                    String aliskanlikId = paliskanlik.getAliskanlikId();
                    String aliskanlikAdi = paliskanlik.getAliskanlikEtiket();
                    String aliskanlikDetay = paliskanlik.getAliskanlikDetay();
                    String aliskanlikKullanici = paliskanlik.getAliskanlikKullaniciId();
                    float paliskanlikSeviye = paliskanlik.getAliskanlikSeviye();

                    Intent intent = new Intent(mcon, AnasayfaPopupActivity.class);
                    intent.putExtra("aliskanlikId", aliskanlikId);
                    intent.putExtra("aliskanlikAdi", aliskanlikAdi);
                    intent.putExtra("aliskanlikDetay", aliskanlikDetay);
                    intent.putExtra("aliskanlikSeviye", paliskanlikSeviye);
                    intent.putExtra("aliskanlikKullanici", aliskanlikKullanici);
                    intent.putExtra("aliskanlikKullaniciAdi", kullaniciAdi);
                    mcon.startActivity(intent);
                }
            });
        }

        public void setData(ModelAliskanlik selectedProduct, int position) {

            String aliskanlikKullaniciId = selectedProduct.getAliskanlikKullaniciId();

            for (int i = 0; i < mKullaniciList.size(); i++) {
                String uuid = mKullaniciList.get(i).getUuid();
                if (aliskanlikKullaniciId.equals(uuid)) {
                    kullaniciAdi = mKullaniciList.get(i).getIsim() + " " + mKullaniciList.get(i).getSoyisim();
                    break;
                }
            }
            kesfetKullaniciAdi.setText(kullaniciAdi);

            this.aliskanlikEtiket.setText(selectedProduct.getAliskanlikEtiket());
            this.aliskanlikDetay.setText(selectedProduct.getAliskanlikDetay());
            this.aliskanlikSeviye.setRating(selectedProduct.getAliskanlikSeviye());
            Constants.profilResminiYukle(this.ivProfile, selectedProduct.getAliskanlikKullaniciId());
        }

        @Override
        public void onClick(View v) {

        }

    }
}
