package com.example.albert.p7_restaurant_albert;

/**
 * Created by Albert on 04/05/2017.
 */

public class Plato {

    private String id;
    private String nom;
    private String kcal;
    private String tipus;

    public Plato() {
    }

    public Plato(String id, String nom, String kcal, String tipus) {
        this.id = id;
        this.nom = nom;
        this.kcal = kcal;
        this.tipus = tipus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
