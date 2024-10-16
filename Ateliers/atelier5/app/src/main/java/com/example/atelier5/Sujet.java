package com.example.atelier5;

public interface Sujet {

    public void ajouterObservateur(ObservateurChangement obs);
    public void enleverObservateur(ObservateurChangement obs);
    public void avertirObservateurs();
}
