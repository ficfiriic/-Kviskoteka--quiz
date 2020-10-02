/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Tanja
 */
@Entity
@Table(name="supervizorodobrenje")
public class SuperOdobrenje implements Serializable{
    @Column(name="kategorija")
    String kategorija;
    @Column(name="pojam")
    String pojam;
    @Id
    @Column(name="id")        
    int id;
    @Column(name="datum")
    String datum;
    @Column(name="username")
    String username;

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getPojam() {
        return pojam;
    }

    public void setPojam(String pojam) {
        this.pojam = pojam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
    
}
