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
@Table(name="zanimljivageografija")
public class ZanimljivaGeografija implements Serializable{
    @Column(name="drzava")
    String drzava;
    @Column(name="grad")
    String grad;
    @Column(name="reka")
    String reka;
    @Column(name="jezero")
    String jezero;
    @Column(name="planina")
    String planina;
    @Column(name="zivotinja")
    String zivotinja;
    @Column(name="biljka")
    String biljka;
    @Column(name="grupa")
    String grupa;
    @Id
    @Column(name="id")        
    int id;

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getReka() {
        return reka;
    }

    public void setReka(String reka) {
        this.reka = reka;
    }

    public String getJezero() {
        return jezero;
    }

    public void setJezero(String jezero) {
        this.jezero = jezero;
    }

    public String getPlanina() {
        return planina;
    }

    public void setPlanina(String planina) {
        this.planina = planina;
    }

    public String getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(String zivotinja) {
        this.zivotinja = zivotinja;
    }

    public String getBiljka() {
        return biljka;
    }

    public void setBiljka(String biljka) {
        this.biljka = biljka;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
