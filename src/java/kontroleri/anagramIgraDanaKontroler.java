/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;
import db.HibernateUtil;
import entiteti.Anagram;
import entiteti.IgraDana;
import entiteti.Korisnici;
import entiteti.Rezultati;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Tanja
 */
@javax.faces.bean.ManagedBean(name="anagramIgraDanaKontroler")
@ViewScoped
public class anagramIgraDanaKontroler{
    
    Anagram anagram;
    boolean ok = false;
    String mojeResenje;
    String poruka;
    int tajmer=60;
    boolean stop;
    boolean kraj=false;
    boolean prikazi = false;
    boolean igraj = true;
    boolean vecigrao=false;
    String danas;
    
    public anagramIgraDanaKontroler(){
        
        ok = false;
        kraj = false;
        prikazi = false;
        igraj = true;
        
        Date today = Calendar.getInstance().getTime();
        String datum;
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
        Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        cr.add(Restrictions.eq("username", k.getUsername()));
       Rezultati rez;
        rez =(Rezultati) cr.uniqueResult();
        session.getTransaction().commit();

        if (rez != null) {
     igraj=false; ok=false;vecigrao=true;
     poruka="Dozvoljeno je igrati jednu igru dnevno!"; 
    session.close();
    return;}
  
        //BCrypt.gensalt(12);
        session.beginTransaction();
        cr = session.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", danas));
        IgraDana igra;
        igra =(IgraDana) cr.uniqueResult();
        session.getTransaction().commit();

        if (igra==null) {poruka = "Igra dana ce uskoro biti definisana!";igraj=false; vecigrao=true; ok = false; return;}
        if (igra!=null) {
        
        session.beginTransaction();
        cr = session.createCriteria(IgraDana.class);
        igra.setMenjanje(1);
        session.saveOrUpdate(igra);
        session.getTransaction().commit();

        session.beginTransaction();
        cr = session.createCriteria(Anagram.class);
        String key = igra.getAnagram();
        cr.add(Restrictions.eq("zagonetka", igra.getAnagram()));
        anagram = (Anagram) cr.uniqueResult();
        session.getTransaction().commit();
               
       k.setRezultat(0);
        ok = true;
        }
    }

    public boolean isVecigrao() {
        return vecigrao;
    }

    public void setVecigrao(boolean vecigrao) {
        this.vecigrao = vecigrao;
    }

    
 
  public String start(){
    
        ok = false;
        kraj = false;
        prikazi = false;
        igraj = true;
        
        Date today = Calendar.getInstance().getTime();
        String datum;
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
        Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        cr.add(Restrictions.eq("username", k.getUsername()));
       Rezultati rez;
        rez =(Rezultati) cr.uniqueResult();
        session.getTransaction().commit();

        if (rez != null) {ok = false; igraj = false; return "anagramIgraDana.xhtml?faces-redirect=true";}
  
        session.beginTransaction();
        cr = session.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", danas));
        IgraDana igra;
        igra =(IgraDana) cr.uniqueResult();
        session.getTransaction().commit();

        if (igra==null) {FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PAZNJA!", "Igra dana ce uskoro biti definisana!")); igraj=false; return "anagramIgraDana.xhtml?faces-redirect=true";}
        if (igra!=null) {
        
        session.beginTransaction();
        cr = session.createCriteria(IgraDana.class);
        igra.setMenjanje(1);
        session.saveOrUpdate(igra);
        session.getTransaction().commit();

        session.beginTransaction();
        cr = session.createCriteria(Anagram.class);
        String key = igra.getAnagram();
        cr.add(Restrictions.eq("zagonetka", igra.getAnagram()));
        anagram = (Anagram) cr.uniqueResult();
        session.getTransaction().commit();
               
       k.setRezultat(0);
        ok = true;
        }
    return "anagramIgraDana.xhtml?faces-redirect=true";
    
    }
    
    public String getMojeResenje() {
        return mojeResenje;
    }

    public void setMojeResenje(String mojeResenje) {
        this.mojeResenje = mojeResenje;
    }
    

    public Anagram getAnagram() {
        return anagram;
    }

    public void setAnagram(Anagram anagram) {
        this.anagram = anagram;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    
    
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isPrikazi() {
        return prikazi;
    }

    public void setPrikazi(boolean prikazi) {
        this.prikazi = prikazi;
    }
    
    public String predajOdg(){
        
        if (mojeResenje.compareTo("")==0) return "";
        
        proveri();
        
        return "mojBrojIgraDana.xhtml?faces-redirect=true";
    
    
    }
    
    public boolean proveriSlova(){
    
     char[] chars = mojeResenje.toCharArray();

    for (char c : chars) {
        if(!Character.isLetter(c)) {
            return false;
        }
    }

    return true;
}
    
    public void proveri(){
    ok = false; prikazi = true;
    if (mojeResenje.compareTo("")==0) {poruka="Osvojili ste 0 poena!"; return ;}
    /*boolean flag = proveriSlova();
    if (flag==false){poruka="Dozvoljeno je koristiti samo slova!"; return;}*/
    String resenje = anagram.getResenje();
    int rez= mojeResenje.compareTo(resenje);
    if (rez==0) rez = 10;
    else rez = 0;
    Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
        
    session.beginTransaction();
    Criteria cr = session.createCriteria(Rezultati.class);
    Rezultati rezultat = new Rezultati();
    rezultat.setUsername(k.getUsername()); rezultat.setDatum(danas);
    rezultat.setRezultat(rez); rezultat.setAnagram(rez);
    session.save(rezultat);
    session.getTransaction().commit();
     return;
        }

    public void setTajmer(int tajmer) {
        this.tajmer = tajmer;
    }

    public int getTajmer() {
        return tajmer;
    }
    
    public void inkrement(){
    if (stop==false){
    tajmer--;
    if (tajmer<=0) {proveri(); stop = true;}
    }else {
    
         for (int i=0; i<32000; i++)
          for (int j=0; j<32000; j++)
              for (int k=0; k<15000;k++);
      tajmer = 0;
      RequestContext reqCtx = RequestContext.getCurrentInstance();
      reqCtx.execute("poll.stop();");
    FacesContext context = FacesContext.getCurrentInstance();
    try {
           context.getExternalContext().redirect("mojBrojIgraDana.xhtml");
       } catch (IOException ex) {
           Logger.getLogger(peharIgraDanaKontroler.class.getName()).log(Level.SEVERE, null, ex);
       }}
    
    
    }
 
    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    
    public void kraaj(){
    kraj=true;
    }

    public boolean isKraj() {
        return kraj;
    }

    public void setKraj(boolean kraj) {
        this.kraj = kraj;
    }

    public boolean isIgraj() {
        return igraj;
    }

    public void setIgraj(boolean igraj) {
        this.igraj = igraj;
    }
    
    
    
}
