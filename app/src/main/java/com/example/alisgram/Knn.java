package com.example.alisgram;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Knn {

    public Knn() {
    }

    public  int[][] uzaklikHesapla(int[] aliskanlikDizisi, int[][] aliskanliklar, int k){
        int[] kAliskanlik=new int[k] ;
        int[][] donenAliskanlikDegeri=new int[k][aliskanlikDizisi.length];
        ArrayList<Integer> yerBelirleyici=new ArrayList<>();

        Map<Integer, Double> map=new HashMap<>();
        for(int i=0;i< aliskanliklar.length;i++){
            double uzaklik=0;
            for(int j=0;j<aliskanlikDizisi.length;j++){
                uzaklik+=Math.pow(aliskanliklar[i][j]-aliskanlikDizisi[j],2);
            }
            uzaklik=Math.sqrt(uzaklik);

            map.put(i, uzaklik);
        }


        for(int i=0;i<k;i++){
            double enk=1000;
            int sayac=0;

            for(int j=0;j<map.size();j++){

                if(yerBelirleyici.contains(j)){}
                else{
                    if(enk>map.get(j) && !yerBelirleyici.contains(j)){
                        enk=map.get(j);
                        sayac=j;
                    }
                }
            }
            yerBelirleyici.add(sayac);


            for(int j=0;j<aliskanlikDizisi.length;j++){
                donenAliskanlikDegeri[i][j]=aliskanliklar[yerBelirleyici.get(i)][j];
            }

        }
        return donenAliskanlikDegeri;

    }
}