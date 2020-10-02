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
@Table(name="rezultati")
public class Rezultati implements Serializable{
    @Id
    @Column(name="username")
    String username;
    @Id
    @Column(name="datum")
    String datum;
    @Column(name="rezultat")
    int rezultat;
    @Column(name="anagram")
    int anagram;
    @Column(name="mojbroj")
    int mojBroj;
    @Column(name="geografija")
    int geografija;
    @Column(name="pet")
    int pet;
    @Column(name="pehar")
    int pehar;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getRezultat() {
        return rezultat;
    }

    public void setRezultat(int rezultat) {
        this.rezultat = rezultat;
    }

    public int getAnagram() {
        return anagram;
    }

    public void setAnagram(int anagram) {
        this.anagram = anagram;
    }

    public int getMojBroj() {
        return mojBroj;
    }

    public void setMojBroj(int mojBroj) {
        this.mojBroj = mojBroj;
    }

    public int getGeografija() {
        return geografija;
    }

    public void setGeografija(int geografija) {
        this.geografija = geografija;
    }

    public int getPet() {
        return pet;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }

    public int getPehar() {
        return pehar;
    }

    public void setPehar(int pehar) {
        this.pehar = pehar;
    }
    
    
    
    
}
