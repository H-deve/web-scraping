package fr.cda.campingcar.modele;

public class Site {
    private int id;
    private String nom_site;
    private String url_site;

    public Site(int id , String nom_site, String url_site) {
        this.id = id;
        this.nom_site = nom_site;
        this.url_site = url_site;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_site() {
        return nom_site;
    }

    public void setNom_site(String nom_site) {
        this.nom_site = nom_site;
    }

    public String getUrl_site() {
        return url_site;
    }

    public void setUrl_site(String url_site) {
        this.url_site = url_site;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Site{");
        sb.append("id=").append(id);
        sb.append(", nom_site='").append(nom_site).append('\'');
        sb.append(", url_site='").append(url_site).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
