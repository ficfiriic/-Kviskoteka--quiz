/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Anagram;
import entiteti.IgraDana;
import entiteti.Pehar;
import entiteti.Rezultati;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Scope;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Tanja
 */


@ManagedBean
@Named(value="AdminIgraDanaKontroler")
@SessionScoped
public class AdminIgraDanaKontroler implements Serializable{
    
    List<Anagram> anagrami;
    List<String> grupePehara;
    List<Pehar> pehari;
    List<Pehar> zaUbacitiPehari;
    Anagram anagram;
    Date datum;
    
    String poruka;
    boolean ok = false;
    boolean entry = false;
    boolean dodatAnagram = false;
    boolean prikaziGrupe = false;
    String porukaGrupe;
    String porukaAnagram;
    SessionFactory sessionFactory;
    Session session;
    
    public AdminIgraDanaKontroler(){
        
        
    sessionFactory = HibernateUtil.getSessionFactory();
    session = sessionFactory.openSession();
    
        dohvatiSveAnagrame();
        
        dohvatiSvaPitanja();
    

    }
    
    public void dohvatiSveAnagrame(){
        anagrami = new LinkedList<Anagram>();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Anagram.class);
        anagrami =(List<Anagram>) cr.list();
        session.getTransaction().commit();
}

    public void dohvatiSvaPitanja(){
    
        pehari = new LinkedList<Pehar>();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Pehar.class);
        pehari =(List<Pehar>) cr.list();
        session.getTransaction().commit();
        
       pehari.sort(Comparator.comparing(Pehar::getId).reversed());
        
    
    }
    
    public void setAnagrami(List<Anagram> anagrami) {
        this.anagrami = anagrami;
    }

    public void setAnagram(Anagram anagram) {
        this.anagram = anagram;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public List<Anagram> getAnagrami() {
        return anagrami;
    }

    public Anagram getAnagram() {
        return anagram;
    }

    public Date getDatum() {
        return datum;
    }

    public boolean isDodatAnagram() {
        return dodatAnagram;
    }

    public void setDodatAnagram(boolean dodatAnagram) {
        this.dodatAnagram = dodatAnagram;
    }

    public String getPorukaAnagram() {
        return porukaAnagram;
    }

    public void setPorukaAnagram(String porukaAnagram) {
        this.porukaAnagram = porukaAnagram;
    }

    public boolean isPrikaziGrupe() {
        return prikaziGrupe;
    }

    public void setPrikaziGrupe(boolean prikaziGrupe) {
        this.prikaziGrupe = prikaziGrupe;
    }

    public String getPorukaGrupe() {
        return porukaGrupe;
    }

    public void setPorukaGrupe(String porukaGrupe) {
        this.porukaGrupe = porukaGrupe;
    }
    
    
    
    
    
public String dodajAnagram(Anagram a){
    if (this.anagram==null) {this.anagram = a; 
    if (this.anagram!=null){
    
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Čestitamo!", "Uspešno ste dodali anagram!"));
   dodatAnagram=true;
   
    }}
     return "admin.xhtml?faces-redirect=true";
}
public String dodajGrupuPitanja(Pehar tip){
     if (tip!=null && zaUbacitiPehari==null){
     zaUbacitiPehari = new LinkedList<Pehar>();
     int i = 0;
     while (i<pehari.size()){
     if (pehari.get(i).getId()==tip.getId()) zaUbacitiPehari.add(pehari.get(i));
     i++;
     }}
     prikaziGrupe=true;
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Čestitamo!", "Uspešno ste dodali grupu pitanja!"));
   
     return "admin.xhtml?faces-redirect=true";
}

public boolean proveriDatum(String datum){
    
        session.beginTransaction();
        Criteria cr = session.createCriteria(IgraDana.class);
        IgraDana igra;
        cr.add(Restrictions.eq("datum",datum));
        igra =(IgraDana) cr.uniqueResult();
        session.getTransaction().commit();

        if (igra!=null) return false;
        return true;
}
    
public String dodaj(){

    ok = true;
    boolean flag = true;
    if (anagram==null || datum==null || zaUbacitiPehari==null){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Morate da dodate datum, anagram i grupu pitanja za pehar!"));
    return "";
    }
    Date today = Calendar.getInstance().getTime();
    String unetDat = String.valueOf(datum);
    String god = unetDat.substring(25, unetDat.length());
    unetDat = unetDat.substring(0, 10);
    unetDat = unetDat.concat(" ");
    unetDat = unetDat.concat(god);
       
    flag = today.after(datum);
    if (flag==true) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Datum je u prošlosti!"));
    return "";
    
    }
    flag = true;
    
    flag = proveriDatum(unetDat);
    
    if (flag==false) {
    
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Za željeni datum već je definisana igra dana!"));
    return "";
        
    }
        session.beginTransaction();
        Criteria cr = session.createCriteria(IgraDana.class);
        IgraDana igra = new IgraDana();
        igra.setDatum(unetDat); igra.setAnagram(anagram.getZagonetka());
        igra.setTipPehar(Integer.toString(zaUbacitiPehari.get(0).getId()));
        session.save(igra);
        session.getTransaction().commit();
    
   
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Čestitamo!", "Uspešno ste dodali igru!"));
     
        
    prikaziGrupe = false; dodatAnagram = false;
    this.anagram = null; this.zaUbacitiPehari = null;
    porukaGrupe=""; porukaAnagram="";
    datum = null;
    
    return "admin?faces-redirect=true";
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

    public List<String> getGrupePehara() {
        return grupePehara;
    }

    public void setGrupePehara(List<String> grupePehara) {
        this.grupePehara = grupePehara;
    }

    

    public List<Pehar> getPehari() {
        return pehari;
    }

    public void setPehari(List<Pehar> pehari) {
        this.pehari = pehari;
    }

    

}
