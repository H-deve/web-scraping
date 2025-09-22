package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Date;
@Entity
@Table(name = "\"Annonce\"")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String type_vehicule;
    @Column
    private String lieu_Depart;
    @Column
    private String lieu_Arrivee;
    @Column
    private Date date_Debut;
    @Column
    private Date date_Fin;
    @Column
    private String prix_par_jour;
    @Column
    private String description;
    @Column
    private String url;
    @Column(name = "site_id")
    private Integer siteId; //


    @OneToOne(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)
    private annonceDetails annonceDetails;


    public Annonce(int id, String type_vehicule, String lieu_Depart, String lieu_Arrivee, Date date_Debut, Date date_Fin, String prix_par_jour, String description, String url) {
        this.id = id;
        this.type_vehicule = type_vehicule;
        this.lieu_Depart = lieu_Depart;
        this.lieu_Arrivee = lieu_Arrivee;
        this.date_Debut = date_Debut;
        this.date_Fin = date_Fin;
        this.prix_par_jour = prix_par_jour;
        this.description = description;
        this.url = url;
//        this.siteId = siteId;
    }

    public Annonce(String type_vehicule, String lieu_Depart, String lieu_Arrivee, Date date_Debut, Date date_Fin, String prix_par_jour, String description, String url, Integer siteId) {
        this.type_vehicule = type_vehicule;
        this.lieu_Depart = lieu_Depart;
        this.lieu_Arrivee = lieu_Arrivee;
        this.date_Debut = date_Debut;
        this.date_Fin = date_Fin;
        this.prix_par_jour = prix_par_jour;
        this.description = description;
        this.url = url;
        this.siteId = siteId;
    }

    public Annonce() {

    }


    //    public Annonce(int id, String type, String location, String location1, Date dateDebut, Date dateFin, double prixParJour, String url, int id1) {
//            this.id = id;
//            this.typeVehicule = type;
//            this.lieuDepart = location;
//            this.lieuArrivee = location1;
//            this.dateDebut = dateDebut;
//            this.dateFin = dateFin;
//            this.prixParJour = prixParJour;
//            this.url = url;
//            this.id = id;
//            this.siteId = id1;
//    }

//    public Annonce(int i, String type, String location, String location1, Date s, Date e, double v, String url) {
//        this.id = id;
//        this.typeVehicule = type;
//        this.lieuDepart = location;
//        this.lieuArrivee = location1;
//        this.dateDebut = dateDebut;
//        this.dateFin = dateFin;
//        this.prixParJour = prixParJour;
//        this.url = url;
//        this.id = id;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_vehicule() {
        return type_vehicule;
    }

    public void setType_vehicule(String type_vehicule) {
        this.type_vehicule = type_vehicule;
    }

    public String getLieu_Depart() {
        return lieu_Depart;
    }

    public void setLieu_Depart(String lieu_Depart) {
        this.lieu_Depart = lieu_Depart;
    }

    public String getLieu_Arrivee() {
        return lieu_Arrivee;
    }

    public void setLieu_Arrivee(String lieu_Arrivee) {
        this.lieu_Arrivee = lieu_Arrivee;
    }

    public Date getDate_Debut() {
        return date_Debut;
    }

    public void setDate_Debut(Date date_Debut) {
        this.date_Debut = date_Debut;
    }

    public Date getDate_Fin() {
        return date_Fin;
    }

    public void setDate_Fin(Date date_Fin) {
        this.date_Fin = date_Fin;
    }

    public String getPrix_par_jour() {
        return prix_par_jour;
    }

    public void setPrix_par_jour(String prix_par_jour) {
        this.prix_par_jour = prix_par_jour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Annonce{");
        sb.append("id=").append(id);
        sb.append(", type_vehicule='").append(type_vehicule).append('\'');
        sb.append(", lieu_Depart='").append(lieu_Depart).append('\'');
        sb.append(", lieu_Arrivee='").append(lieu_Arrivee).append('\'');
        sb.append(", date_Debut=").append(date_Debut);
        sb.append(", date_Fin=").append(date_Fin);
        sb.append(", prix_par_jour='").append(prix_par_jour).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", siteId=").append(siteId);
        sb.append('}');
        return sb.toString();
    }
}
