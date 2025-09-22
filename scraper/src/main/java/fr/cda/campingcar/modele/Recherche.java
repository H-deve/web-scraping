package fr.cda.campingcar.modele;

import java.sql.Date;

public class Recherche {


    private int id;
    private int clientId;
    private String typeVehicule;
    private String lieuDepart;
    private String lieuArrivee;
    private Date dateDebut;
    private Date dateFin;
    private double prixMax;


    public Recherche(int id, int clientId, String typeVehicule, String lieuDepart, String lieuArrivee, Date dateDebut, Date dateFin, double prixMax) {
        this.id = id;
        this.clientId = clientId;
        this.typeVehicule = typeVehicule;
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixMax = prixMax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public double getPrixMax() {
        return prixMax;
    }

    public void setPrixMax(double prixMax) {
        this.prixMax = prixMax;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Recherche{");
        sb.append("id=").append(id);
        sb.append(", clientId=").append(clientId);
        sb.append(", typeVehicule='").append(typeVehicule).append('\'');
        sb.append(", lieuDepart='").append(lieuDepart).append('\'');
        sb.append(", lieuArrivee='").append(lieuArrivee).append('\'');
        sb.append(", dateDebut=").append(dateDebut);
        sb.append(", dateFin=").append(dateFin);
        sb.append(", prixMax=").append(prixMax);
        sb.append('}');
        return sb.toString();
    }
}
