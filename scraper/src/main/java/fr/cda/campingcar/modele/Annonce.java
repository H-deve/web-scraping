package fr.cda.campingcar.modele;

import java.sql.Date;

public class Annonce {

    private int id;
    private String typeVehicule;
    private String lieuDepart;
    private String lieuArrivee;
    private Date dateDebut;
    private Date dateFin;
    private String prixParJour;
    private String description;
    private String url;
    private int siteId;

    public Annonce(int id, String typeVehicule, String lieuDepart, String lieuArrivee, Date dateDebut, Date dateFin, String prixParJour, String description, String url) {
        this.id = id;
        this.typeVehicule = typeVehicule;
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixParJour = prixParJour;
        this.description = description;
        this.url = url;
//        this.siteId = siteId;
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

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(String lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public String getLieuArrivee() {
        return lieuArrivee;
    }

    public void setLieuArrivee(String lieuArrivee) {
        this.lieuArrivee = lieuArrivee;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(String prixParJour) {
        this.prixParJour = prixParJour;
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

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Annonce{");
        sb.append("id=").append(id);
        sb.append(", typeVehicule='").append(typeVehicule).append('\'');
        sb.append(", lieuDepart='").append(lieuDepart).append('\'');
        sb.append(", lieuArrivee='").append(lieuArrivee).append('\'');
        sb.append(", dateDebut=").append(dateDebut);
        sb.append(", dateFin=").append(dateFin);
        sb.append(", prixParJour=").append(prixParJour);
        sb.append(", description='").append(description).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", siteId=").append(siteId);
        sb.append('}');
        return sb.toString();
    }

}
