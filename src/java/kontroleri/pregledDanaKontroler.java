/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Korisnici;
import entiteti.Rezultati;
import entiteti.SuperOdobrenje;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
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
@javax.faces.bean.ManagedBean(name="pregledDanaKontroler")
@ViewScoped
public class pregledDanaKontroler implements Serializable{
    
    List<Rezultati> listaRezultata;
    List<Rezultati> zaKorisnika;
    Rezultati rez;
    String poruka;
    boolean mess = true;
    boolean show = false;
    boolean moi = true;
    String danas;
    int i;
    
    public pregledDanaKontroler(){
    
    listaRezultata = new LinkedList<>();
    
    Date today = Calendar.getInstance().getTime();
    danas = String.valueOf(today);
    String god = danas.substring(25, danas.length());
    danas = danas.substring(0, 10);
    danas = danas.concat(" ");
    danas = danas.concat(god);
    
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Criteria cr = session.createCriteria(Rezultati.class);
    cr.add(Restrictions.eq("datum", danas));
    listaRezultata = cr.list();
    session.getTransaction().commit();
    
    if (listaRezultata.isEmpty()) {poruka="Ocekujemo prve igrace uskoro!"; mess = false;}
    else poruka="Pregled rezultata za danas";
    
    listaRezultata.sort(Comparator.comparing(Rezultati::getRezultat).reversed());
    zaKorisnika = listaRezultata;
     Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
     
     i = 0;
     boolean found=false;
     while (i<zaKorisnika.size()){
     rez = zaKorisnika.get(i);
     if (rez.getUsername().compareTo(k.getUsername())==0) {found=true; break;} 
     i++;}
     
     if (i>10) {show=true;
    zaKorisnika = zaKorisnika.subList(0, 10);}
     
     if (found==false) moi = false;
    
     }

    public boolean isMoi() {
        return moi;
    }

    public void setMoi(boolean moi) {
        this.moi = moi;
    }
    
    
    

    public Rezultati getRez() {
        return rez;
    }

    public void setRez(Rezultati rez) {
        this.rez = rez;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    
    

    public List<Rezultati> getListaRezultata() {
        return listaRezultata;
    }

    public void setListaRezultata(List<Rezultati> listaRezultata) {
        this.listaRezultata = listaRezultata;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public boolean isMess() {
        return mess;
    }

    public void setMess(boolean mess) {
        this.mess = mess;
    }

    public String getDanas() {
        return danas;
    }

    public void setDanas(String danas) {
        this.danas = danas;
    }
    
    
    public void korisnik(){
    
       
        
        mess = false;
        
        
    }

    public List<Rezultati> getZaKorisnika() {
        return zaKorisnika;
    }

    public void setZaKorisnika(List<Rezultati> zaKorisnika) {
        this.zaKorisnika = zaKorisnika;
    }
    
    
    
}
