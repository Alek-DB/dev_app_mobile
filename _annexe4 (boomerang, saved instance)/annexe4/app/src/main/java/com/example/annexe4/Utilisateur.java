package com.example.annexe4;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    public String prenom;
    public String nom;
    public Utilisateur(String prenom, String nom){
        this.nom = nom;
        this.prenom = prenom;
    }
}
