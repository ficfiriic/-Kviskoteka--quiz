/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tanja
 */
@Entity
@Table(name="igradana")
public class IgraDana implements Serializable{
    @Id
    @Column(name="datum")  
   // @Temporal(TemporalType.DATE)        
    String datum;
    
    @Column(name="menjanje")
    int menjanje;
    
    @Column(name="anagram")
    String anagram;
    
    @Column(name="tipPehar")
    String tipPehar;

    public String getDatum() {
        return datum;
    }

    public int getMenjanje() {
        return menjanje;
    }

    public String getAnagram() {
        return anagram;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setMenjanje(int menjanje) {
        this.menjanje = menjanje;
    }

    public void setAnagram(String anagram) {
        this.anagram = anagram;
    }

    public String getTipPehar() {
        return tipPehar;
    }

    public void setTipPehar(String tipPehar) {
        this.tipPehar = tipPehar;
    }
    
    
    
    
    
    
}
