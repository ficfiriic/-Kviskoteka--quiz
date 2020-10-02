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
import entiteti.Pehar;
import entiteti.Rezultati;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Tanja
 */
@ManagedBean
@SessionScoped
@Named(value="adminModifikacijaKontroler")
public class adminModifikacijaKontroler implements Serializable{
    
    List<IgraDana> igre;
    List<Anagram> anagrami;
    List<Pehar> pehari;
    List<Pehar> zaUbacitiPehari;
    boolean prikaziGrupe=false;
    boolean dodatAnagram=false;
    Anagram anagram;
    String porukaGrupe;
    String porukaAnagram;
    boolean prikaz = true;
    boolean mess = false;
    String datumZaModifikaciju;
    String poruka;
    SessionFactory sessionFactory;
    Session session;
    public adminModifikacijaKontroler(){
    
        igre = new LinkedList<>();
        
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        
        session.beginTransaction();
        Criteria cr = session.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("menjanje", 0));
        igre = cr.list();
        session.getTransaction().commit();

        if (igre.isEmpty()) {poruka = "Nema igara za modifikaciju!"; prikaz = false; return;}
        
        int i = 0;
        while (i<igre.size()){
        
        IgraDana igrica = igre.get(i);
        
        String datum = igrica.getDatum();
        try{
        Date date = new SimpleDateFormat("E MMM dd yyyy").parse(datum);
        
        Date today = Calendar.getInstance().getTime();
        String danas = String.valueOf(today);
        String god = danas.substring(25, danas.length());
        danas = danas.substring(0, 10);
        danas = danas.concat(" ");
        danas = danas.concat(god);
        if (datum.compareTo(danas)==0) i++;
        else if (today.after(date)) {igre.remove(igrica);}
             else i++;
        
        }catch (Exception e) {}
        }
    }

    public List<IgraDana> getIgre() {
        return igre;
    }

    public void setIgre(List<IgraDana> igre) {
        this.igre = igre;
    }

    public boolean isPrikaz() {
        return prikaz;
    }

    public void setPrikaz(boolean prikaz) {
        this.prikaz = prikaz;
    }

    public boolean isPrikaziGrupe() {
        return prikaziGrupe;
    }

    public void setPrikaziGrupe(boolean prikaziGrupe) {
        this.prikaziGrupe = prikaziGrupe;
    }

    public boolean isDodatAnagram() {
        return dodatAnagram;
    }

    public void setDodatAnagram(boolean dodatAnagram) {
        this.dodatAnagram = dodatAnagram;
    }

    public String getPorukaGrupe() {
        return porukaGrupe;
    }

    public void setPorukaGrupe(String porukaGrupe) {
        this.porukaGrupe = porukaGrupe;
    }

    public String getPorukaAnagram() {
        return porukaAnagram;
    }

    public void setPorukaAnagram(String porukaAnagram) {
        this.porukaAnagram = porukaAnagram;
    }
    
    

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
 
    public String menjaj(String datum){
        
       datumZaModifikaciju = datum;
       zaMenjanje();
       
       return "stranicaZaMenjanjeIgreDana?faces-redirect=true";
    
    
    }

    public void setDatumZaModifikaciju(String datumZaModifikaciju) {
        this.datumZaModifikaciju = datumZaModifikaciju;
    }

    public String getDatumZaModifikaciju() {
        return datumZaModifikaciju;
    }

    public void setAnagrami(List<Anagram> anagrami) {
        this.anagrami = anagrami;
    }

    public List<Anagram> getAnagrami() {
        return anagrami;
    }

    public List<Pehar> getPehari() {
        return pehari;
    }

    public void setPehari(List<Pehar> pehari) {
        this.pehari = pehari;
    }
    
    
    
    
    public void zaMenjanje(){
     anagrami = new LinkedList<Anagram>();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Anagram.class);
        anagrami =(List<Anagram>) cr.list();
        session.getTransaction().commit();
        
        pehari = new LinkedList<Pehar>();
        session.beginTransaction();
        Criteria cry = session.createCriteria(Pehar.class);
        pehari =(List<Pehar>) cry.list();
        session.getTransaction().commit();
        
       pehari.sort(Comparator.comparing(Pehar::getId).reversed());
       
       poruka="";

    
    }
    
    
public String dodajGrupuPitanja(Pehar tip){
     if (tip!=null && zaUbacitiPehari==null){
     zaUbacitiPehari = new LinkedList<Pehar>();
     int i = 0;
     while (i<pehari.size()){
     if (pehari.get(i).getId()==tip.getId()) zaUbacitiPehari.add(pehari.get(i));
     i++;
     }}
     prikaziGrupe = true;
     porukaGrupe = "Dodali ste grupu pitanja!";
return "stranicaZaMenjanjeIgreDana?faces-redirect=true";
}

public String dodajAnagram(Anagram a){
    if (this.anagram==null) {this.anagram = a; 
    if (this.anagram!=null){
    porukaAnagram="Dodali ste anagram!"; dodatAnagram = true;}}
    return "stranicaZaMenjanjeIgreDana?faces-redirect=true";
}


    
    
    
    public String promeni(){
        mess = true;
        
        if (anagram==null && zaUbacitiPehari==null){poruka="Oznacite sta zelite da promenite!"; return "stranicaZaMenjanjeIgreDana?faces-redirect=true";}
        session.beginTransaction();
        Criteria cr = session.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", datumZaModifikaciju));
        IgraDana igra =(IgraDana) cr.uniqueResult();
        session.getTransaction().commit();
        
        if (anagram!=null)igra.setAnagram(anagram.getZagonetka());
        if (zaUbacitiPehari!=null)igra.setTipPehar(Integer.toString(zaUbacitiPehari.get(0).getId()));
        session.beginTransaction();
        Criteria cry = session.createCriteria(IgraDana.class);
        session.update(igra);
        session.getTransaction().commit();
    
    poruka = "Uspesno ste promenili igru dana!";
    anagram=null;
    zaUbacitiPehari=null;
    porukaAnagram="";
    porukaGrupe="";
    dodatAnagram=false;
    prikaziGrupe=false;
    
    return "stranicaZaMenjanjeIgreDana?faces-redirect=true";
    
    
    }

    public boolean isMess() {
        return mess;
    }

    public void setMess(boolean mess) {
        this.mess = mess;
    }
    
    
    
}
