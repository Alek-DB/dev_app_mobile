package com.example.annexe1;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SingletonMemo {

    private static SingletonMemo instance;
    private ArrayList<Memo> liste;
    private Context contexte;

    public static SingletonMemo getInstance(Context ctx){
        if(instance == null){
            instance = new SingletonMemo(ctx);
        }
        return instance;
    }

    private SingletonMemo(Context ctx){
        liste = new ArrayList<>();
        this.contexte = ctx;
    }

    public void ajouter_memo(Memo memo){
        liste.add(memo);
    }

    public ArrayList<Memo> get_memo(){
        return liste;
    }

    public void serialiserListe() throws Exception{

        //buffer special pour objet
        // objectOutputStream
        try(
            FileOutputStream fos =  contexte.openFileOutput("fichier.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
                oos.writeObject(liste);
        }

    }

    public void deserialiser() throws Exception{

        try(
                FileInputStream fis =  contexte.openFileInput("fichier.ser");
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            liste = (ArrayList<Memo>) ois.readObject();
        }
    }

}
