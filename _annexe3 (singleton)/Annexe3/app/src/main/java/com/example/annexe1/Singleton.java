package com.example.annexe1;

import android.content.Context;

import java.util.ArrayList;

public class Singleton {

    private static Singleton instance;
    private ArrayList<Memo> liste;

    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton(){
        liste = new ArrayList<>();
    }

    public void ajouter_memo(Memo memo){
        liste.add(memo);
    }

    public ArrayList<Memo> get_memo(){
        return liste;
    }


}
