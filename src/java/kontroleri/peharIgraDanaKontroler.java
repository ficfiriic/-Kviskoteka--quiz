/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.IgraDana;
import entiteti.IgraPetXPet;
import entiteti.Korisnici;
import entiteti.Pehar;
import entiteti.Rezultati;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Tanja
 */
@javax.faces.bean.ManagedBean(name="peharIgraDanaKontroler")
@ViewScoped
public class peharIgraDanaKontroler{
    
    String odgovori[];
    Boolean flagOdgovora[];
    String unesenOdgovor;
    String pitanje;
    String odgovor;
    Pehar zaPitati;
    int redniBroj;
    SessionFactory sessionFactory;
    Session session;
    boolean started=false;
    boolean manje = false;
    int tajmer=30;
    int rb; //do koje sam reci stigla;
    Korisnici korisnik;
    boolean stop = false;
    boolean kraj = false;
    Rezultati rezu;
    
    
    public peharIgraDanaKontroler(){
        
        started = false; manje = false; stop = false; kraj = false;
    
        odgovori = new String[13];
        flagOdgovora = new Boolean[13];
        Date today = Calendar.getInstance().getTime();
        String datum;
        String danas = String.valueOf(today);
        String god = danas.substring(25, danas.length());
        danas = danas.substring(0, 10);
        danas = danas.concat(" ");
        danas = danas.concat(god);
        for (int i=0;i<13;i++) flagOdgovora[i] = false;
        redniBroj = 1;
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", danas));
        IgraDana igra;
        igra =(IgraDana) cr.uniqueResult();
        session.getTransaction().commit();
        
        session.beginTransaction();
        cr = session.createCriteria(Pehar.class);
        int i = Integer.parseInt(igra.getTipPehar());
        cr.add(Restrictions.eq("id",i));
        zaPitati =(Pehar) cr.uniqueResult();
        session.getTransaction().commit();
        rb=0;
        korisnik = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        
        if (korisnik!=null){
           
         session.beginTransaction();
         cr = session.createCriteria(Rezultati.class);
         cr.add(Restrictions.eq("datum", danas));
         cr.add(Restrictions.eq("username", korisnik.getUsername())); 
         rezu =(Rezultati) cr.uniqueResult();
         session.getTransaction().commit();
        
        }
    }

    public String[] getOdgovori() {
        return odgovori;
    }

    public void setOdgovori(String[] odgovori) {
        this.odgovori = odgovori;
    }

    public Boolean[] getFlagOdgovora() {
        return flagOdgovora;
    }

    public void setFlagOdgovora(Boolean[] flagOdgovora) {
        this.flagOdgovora = flagOdgovora;
    }
    
    
    
    public String getUnesenOdgovor() {
        return unesenOdgovor;
    }

    public void setUnesenOdgovor(String unesenOdgovor) {
        this.unesenOdgovor = unesenOdgovor;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
    
    
    public void postaviPitanje(){
    started = true;
    switch(rb){
        case 0:
            pitanje=zaPitati.getP1(); odgovor = zaPitati.getO1(); break;
        case 1:
            pitanje=zaPitati.getP2(); odgovor = zaPitati.getO2(); break;
        case 2:
            pitanje=zaPitati.getP3(); odgovor = zaPitati.getO3(); break;
        case 3:
            pitanje=zaPitati.getP4(); odgovor = zaPitati.getO4(); break;
        case 4:
            pitanje=zaPitati.getP5(); odgovor = zaPitati.getO5(); break;
        case 5:
            pitanje=zaPitati.getP6(); odgovor = zaPitati.getO6(); break;
        case 6:
            pitanje=zaPitati.getP7(); odgovor = zaPitati.getO7(); break;
        case 7:
            pitanje=zaPitati.getP8(); odgovor = zaPitati.getO8(); break;
        case 8:
            pitanje=zaPitati.getP9(); odgovor = zaPitati.getO9(); break;
        case 9:
            pitanje=zaPitati.getP10(); odgovor = zaPitati.getO10(); break;
        case 10:
            pitanje=zaPitati.getP11(); odgovor = zaPitati.getO11(); break;
        case 11:
            pitanje=zaPitati.getP12(); odgovor = zaPitati.getO12(); break;
        case 12:
            pitanje=zaPitati.getP13(); odgovor = zaPitati.getO13(); break;
    }
    }

    public int getTajmer() {
        return tajmer;
    }

    public void setTajmer(int tajmer) {
        this.tajmer = tajmer;
    }
    
    public void proveraOdgovora(){
        int j=0;
        if (stop==false){
        if (korisnik!=null){
        if (unesenOdgovor.compareTo("")!=0){
        if (odgovor.compareToIgnoreCase(unesenOdgovor)==0) {
        rezu.setRezultat(rezu.getRezultat()+10);
        rezu.setPehar(rezu.getPehar()+10);
        }else System.out.println("Pogresan odgovor");
        }}
        odgovori[rb] = odgovor;                              
        flagOdgovora[rb] = true;
      rb++;
        }
    if (rb==13) stop = true;
    
          
    
    }
    
    
    
    public void dekrement(){
    if (kraj==false){    
   if (started && rb<13){ //da ne bi javljao index out of Bound exception
    tajmer--;
    if (tajmer<=0 && stop==false) {
    proveraOdgovora();
    if (stop==false) postaviPitanje();
    tajmer=30;
    if (stop==true) {kraj=true; tajmer=0;}
    }}}
    else{
      for (int i=0; i<32000; i++)
          for (int j=0; j<32000; j++)
              for (int k=0; k<5000;k++);
      tajmer = 0;
      RequestContext reqCtx = RequestContext.getCurrentInstance();
      reqCtx.execute("poll.stop();");
      session.beginTransaction();
    Criteria cr = session.createCriteria(Rezultati.class);
    session.update(rezu);
    session.getTransaction().commit();
    session.close();
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    try {
           context.getExternalContext().redirect("KrajIgre.xhtml");
       } catch (IOException ex) {
           Logger.getLogger(peharIgraDanaKontroler.class.getName()).log(Level.SEVERE, null, ex);
       }}
    
    }
    public boolean isStop() {
        return stop;
    }
    
    public void predajOdg(){
    proveraOdgovora();
    postaviPitanje();
    tajmer = 30;
    if (stop==true){
    session.beginTransaction();
    Criteria cr = session.createCriteria(Rezultati.class);
    session.update(rezu);
    session.getTransaction().commit();
    session.close();
    FacesContext context = FacesContext.getCurrentInstance();
    try {
     context.getExternalContext().redirect("KrajIgre.xhtml");
    } catch (IOException ex) {
     Logger.getLogger(IgraDanaPetKontroler.class.getName()).log(Level.SEVERE, null, ex);
   }
    }
    
    
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isKraj() {
        return kraj;
    }

    public void setKraj(boolean kraj) {
        this.kraj = kraj;
    }
    
    
    
    
    
}
