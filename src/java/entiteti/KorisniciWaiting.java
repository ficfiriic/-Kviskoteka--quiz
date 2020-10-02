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
@Table(name="korisniciwaiting")
public class KorisniciWaiting {

    @Id
    @Column(name="username")
    String username;
    
    @Column(name="password")
    String password;
    
    @Column(name="ime")
    String ime;
    
    @Column(name="prezime")
    String prezime;
    
    @Column(name="jmbg")
    String jmbg;
    
    @Column(name="zanimanje")
    String zanimanje;
    
    @Column(name="email")
    String email;
    
    @Column(name="pol")
    int pol;
    
    @Column(name="pitanje")
    int pitanje;
    
    @Column(name="odgovor")
    String odgovor;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
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

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getJmbg() {
        return jmbg;
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

    public String getOdgovor() {
        return odgovor;
    }
   
      
}
