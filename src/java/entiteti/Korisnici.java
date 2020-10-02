/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Tanja
 */

@Entity
@Table(name="korisnici")
public class Korisnici {
    
    @Id
    @Column(name="username")
    String username;
    
    @Column(name="password")
    String password;
    
    @Column(name="tip")
    String tip;

    @Column(name="jmbg")
    String jmbg;
    
    @Column(name="ime")
    String ime;
    
    @Column(name="prezime")
    String prezime;
    
    @Column(name="odgovor")
    String odgovor;
    
    @Column(name="zanimanje")
    String zanimanje;
    
    @Column(name="email")
    String email;
    
    @Column(name="pol")
    int pol;
    
    @Column (name="pitanje")
    int pitanje;
    
    @Column(name="rezultat")
    int rezultat;

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPol(int pol) {
        this.pol = pol;
    }

    public void setPitanje(int pitanje) {
        this.pitanje = pitanje;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public String getZanimanje() {
        return zanimanje;
    }

    public String getEmail() {
        return email;
    }

    public int getPol() {
        return pol;
    }

    public int getPitanje() {
        return pitanje;
    }
    
    
    
    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getJmbg() {
        return jmbg;
    }
    
    
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getRezultat() {
        return rezultat;
    }

    public void setRezultat(int rezultat) {
        this.rezultat = rezultat;
    }
    
   

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTip() {
        return tip;
    }
    
    
    public void update(KorisniciWaiting k){
    
    this.email = k.email;
    this.ime = k.ime;
    this.prezime = k.prezime;
    this.username = k.username;
    this.jmbg = k.jmbg;
    this.odgovor = k.odgovor;
    this.pitanje = k.pitanje;
    this.pol = k.pol;
    this.password = k.password;
    this.zanimanje = k.zanimanje;
    this.tip = "korisnik";
    this.rezultat=0;
    
    }
    
}

