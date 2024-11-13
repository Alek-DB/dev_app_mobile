package com.example.annexe9;

// Classe pour représenter l'ensemble du fichier JSON
import java.util.List;

public class VoituresList {
    private List<Voiture> cars;

    // Getter pour accéder à la liste des voitures
    public List<Voiture> getCars() {
        return cars;
    }

    public void setCars(List<Voiture> cars) {
        this.cars = cars;
    }
}