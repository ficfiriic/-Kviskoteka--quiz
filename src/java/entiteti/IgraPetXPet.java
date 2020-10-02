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
@Table(name="igra5x5")
public class IgraPetXPet implements Serializable{
    @Id
    @Column(name="prva")        
    String prva;
    
    @Id
    @Column(name="druga")
    String druga;
    
    @Id
    @Column(name="treca")
    String treca;
    
    @Id
    @Column(name="cetvrta")
    String cetvrta;
    
    @Id
    @Column(name="peta")
    String peta;

    public String getPrva() {
        return prva;
    }

    public String getDruga() {
        return druga;
    }

    public String getTreca() {
        return treca;
    }

    public String getCetvrta() {
        return cetvrta;
    }

    public String getPeta() {
        return peta;
    }

    public void setPrva(String prva) {
        this.prva = prva;
    }

    public void setDruga(String druga) {
        this.druga = druga;
    }

    public void setTreca(String treca) {
        this.treca = treca;
    }

    public void setCetvrta(String cetvrta) {
        this.cetvrta = cetvrta;
    }

    public void setPeta(String peta) {
        this.peta = peta;
    }
    
    
    
}
