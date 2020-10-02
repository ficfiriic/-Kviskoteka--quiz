/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Anagram;
import entiteti.Korisnici;
import entiteti.Pehar;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
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
@Named(value = "SuperPeharKontroler")
@SessionScoped
public class SuperPeharKontroler implements Serializable{
   
    String[] pitanja;
    String[] odgovori;
    boolean ok;
    String poruka;
    public SuperPeharKontroler(){
    
        pitanja = new String[13];
        odgovori = new String[13];
    
    }

    public String[] getPitanja() {
        return pitanja;
    }

    public String[] getOdgovori() {
        return odgovori;
    }

    public boolean isOk() {
        return ok;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPitanja(String[] pitanja) {
        this.pitanja = pitanja;
    }

    public void setOdgovori(String[] odgovori) {
        this.odgovori = odgovori;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    public boolean proveriNule(){
    
    
        for (int i=0;i<13;i++)
            if (odgovori[i].equals("") || pitanja[i].equals("")) return false;
    
        return true;
    }
    
    public boolean proveriOdgovore(){
    if (odgovori[0].length()!=9 || odgovori[1].length()!=8 ||odgovori[2].length()!=7 || odgovori[3].length()!=6 || 
    odgovori[4].length()!=5 || odgovori[5].length()!=4 || odgovori[6].length()!=3 || odgovori[7].length()!=4 ||
    odgovori[8].length()!=5 || odgovori[9].length()!=6 || odgovori[10].length()!=7 || odgovori[11].length()!=8 || odgovori[12].length()!=9) return false;
        
    return true;
    }
    
    public boolean proveriPitanja(){
    
    for (int i=0;i<12;i++)
        for (int j=i+1; j<13;j++)
            if (pitanja[i].equals(pitanja[j])) return false;
    
    return true;
    }
    
    public boolean vecPostoji(){
    
    int i=0;
    while(i<13){
    
         SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
       
    
        session.beginTransaction();
        Criteria cr = session.createCriteria(Pehar.class);
        cr.add(Restrictions.eq("pitanje", pitanja[i]));
        Pehar p;
        p =(Pehar) cr.uniqueResult();
        session.getTransaction().commit();
    
        if (p!=null) {return false;}
     i++;
    }
    return true;
    
    }
    
    public String ubaci(){
    
        ok=true;
    boolean flag  = proveriNule();
    
    if (flag==false) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Sva polja su obavezna!"));
    return "";
    }
    
    flag = true;
    flag = proveriPitanja();
    
    if (flag==false) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Uneli ste isto pitanje!"));
    return "";
    }
    
    flag = true;
    flag = proveriOdgovore();
    
    if (flag==false) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Proverite odgovore!"));
    return "";}
    
    flag = true;
    /*flag = vecPostoji();
    if (flag==false){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Pitanje već postoji u bazi!"));
    return "";
    }*/
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    Pehar a = new Pehar();
    a.setP1(pitanja[0]);a.setP2(pitanja[1]);a.setP3(pitanja[2]);
    a.setP4(pitanja[3]);a.setP5(pitanja[4]);a.setP6(pitanja[5]);
    a.setP7(pitanja[6]);a.setP8(pitanja[7]);a.setP9(pitanja[8]);
    a.setP10(pitanja[9]);a.setP11(pitanja[10]);a.setP12(pitanja[11]);a.setP13(pitanja[12]);
    
    a.setO1(odgovori[0]);a.setO2(odgovori[1]);a.setO3(odgovori[2]);
    a.setO4(odgovori[3]);a.setO5(odgovori[4]);a.setO6(odgovori[5]);
    a.setO7(odgovori[6]);a.setO8(odgovori[7]);a.setO9(odgovori[8]);
    a.setO10(odgovori[9]);a.setO11(odgovori[10]);a.setO12(odgovori[11]);a.setO13(odgovori[12]);
    
    session.beginTransaction();
    session.save(a);
    session.getTransaction().commit();
    
        
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Uspešno ste dodali igru!"));
    return "";
    }
    
}
