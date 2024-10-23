package com.example.tp1;

public interface Sujet {

    public void ajouterObservateur(ObservateurChangement obs);
    public void enleverObservateur(ObservateurChangement obs);
    public void avertirObservateurs();
}
