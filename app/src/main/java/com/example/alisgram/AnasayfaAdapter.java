package com.example.alisgram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AnasayfaAdapter extends RecyclerView.Adapter<AnasayfaAdapter.MyViewHolder> {

    ArrayList<ModelAliskanlik> mAliskanlikList;
    ArrayList<ModelKullanici> mKullaniciList;
    LayoutInflater inflater;
    private Context mcon;

    public AnasayfaAdapter(Context context, ArrayList<ModelAliskanlik> aliskanliklar, ArrayList<ModelKullanici> kullanicilar) {
        inflater = LayoutInflater.from(context);
        this.mAliskanlikList = aliskanliklar;
        this.mKullaniciList = kullanicilar;
        mcon = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.anasayfa_list_item, parent, false);
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

        TextView aliskanlikKullaniciAdi;
        TextView aliskanlikEtiket;
        TextView aliskanlikDetay;
        RatingBar aliskanlikSeviye;
        String kullaniciAdi = null;

        CircleImageView ivProfile;

        public MyViewHolder(View itemView) {
            super(itemView);
            aliskanlikEtiket = (TextView) itemView.findViewById(R.id.anasayfaAliskanlikAdi);
            aliskanlikSeviye = (RatingBar) itemView.findViewById(R.id.anasayfaRatingBar);
            aliskanlikKullaniciAdi = (TextView) itemView.findViewById(R.id.anasayfaKullaniciAdi);
            aliskanlikDetay = (TextView) itemView.findViewById(R.id.anasayfaAliskanlikDetay);
            ivProfile = itemView.findViewById(R.id.ivProfile);

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

            this.aliskanlikKullaniciAdi.setText(kullaniciAdi);
            this.aliskanlikEtiket.setText(selectedProduct.getAliskanlikEtiket());
            this.aliskanlikDetay.setText(selectedProduct.getAliskanlikDetay());
            this.aliskanlikSeviye.setRating(selectedProduct.getAliskanlikSeviye());

            Constants.profilResminiYukle(this.ivProfile,selectedProduct.getAliskanlikKullaniciId());
        }

        @Override
        public void onClick(View v) {
        }
    }
}
