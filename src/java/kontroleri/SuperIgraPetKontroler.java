/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Anagram;
import entiteti.IgraPetXPet;
import entiteti.Korisnici;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Tanja
 */
@ManagedBean
@Named(value="SuperIgraPetKontroler")
@SessionScoped
public class SuperIgraPetKontroler implements Serializable{
    
    String prva;
    String druga;
    String treca;
    String cetvrta;
    String peta;

    boolean ok;
    String poruka;
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

    public boolean isOk() {
        return ok;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    
    
    public String ubaci(){
    
        ok = true;
        if (prva.equals("") || druga.equals("") || treca.equals("") || cetvrta.equals("") || peta.equals("")) {
        
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Sva polja su obavezna!"));
     return "";
        
        }
        
       SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
       
    
        session.beginTransaction();
        Criteria cr = session.createCriteria(IgraPetXPet.class);
        cr.add(Restrictions.eq("prva", prva));
        cr.add(Restrictions.eq("druga", druga));
        cr.add(Restrictions.eq("treca", treca));
        cr.add(Restrictions.eq("cetvrta", cetvrta));
        cr.add(Restrictions.eq("peta", peta));
        IgraPetXPet igra;
        igra =(IgraPetXPet) cr.uniqueResult();
        session.getTransaction().commit();
  
     
        if (igra!=null) {
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Ista igra već postoji!"));
        return "";
            
        }
        igra = new IgraPetXPet();
        igra.setPrva(prva); igra.setDruga(druga); igra.setTreca(treca); igra.setCetvrta(cetvrta); igra.setPeta(peta);
    
        session.beginTransaction();
        session.save(igra);
        session.getTransaction().commit();
        
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Uspešno ste dodali igru!"));
     return "";
    
    
    
    }
}
