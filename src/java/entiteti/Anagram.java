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
@Table(name="anagram")
public class Anagram {
    @Id
    @Column(name="zagonetka")        
    String zagonetka;
    
    @Column(name="resenje")
    String resenje;

    public String getZagonetka() {
        return zagonetka;
    }

    public String getResenje() {
        return resenje;
    }

    public void setZagonetka(String zagonetka) {
        this.zagonetka = zagonetka;
    }

    public void setResenje(String resenje) {
        this.resenje = resenje;
    }
    
    
}
