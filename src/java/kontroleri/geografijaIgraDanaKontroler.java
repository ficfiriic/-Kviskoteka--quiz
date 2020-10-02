/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.IgraDana;
import entiteti.Korisnici;
import entiteti.Rezultati;
import entiteti.SuperOdobrenje;
import entiteti.ZanimljivaGeografija;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.hibernate.metamodel.source.annotations.JPADotNames;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Tanja
 */
@javax.faces.bean.ManagedBean(name="geografijaIgraDanaKontroler")
@ViewScoped
public class geografijaIgraDanaKontroler{
    String[] pojmovi;
    String poruka;
    boolean mess=false;
    String slovo;
    int tajmer = 120;
    boolean kraj=false;
    boolean ok = true;
    boolean stop = false;

    public geografijaIgraDanaKontroler(){
        
        int redniBroj = (int)(Math.random()*30);
        kraj = false;
        ok = true;
        stop = false;
        mess = false;
                
         switch(redniBroj){
             case 0: slovo = "A"; break;
             case 1:slovo = "B"; break;
             case 2:slovo = "C"; break;
             case 3:slovo = "Č"; break;
             case 4:slovo = "Ć"; break;
             case 5:slovo = "D"; break;
             case 6:slovo = "Dž"; break;
             case 7:slovo = "Đ"; break;
             case 8:slovo = "E"; break;
             case 9:slovo = "F"; break;
             case 10:slovo = "G"; break;
             case 11:slovo = "H"; break;
             case 12:slovo = "I"; break;
             case 13:slovo = "J"; break;
             case 14:slovo = "K"; break;
             case 15:slovo = "L"; break;
             case 16:slovo = "Lj"; break;
             case 17:slovo = "M"; break;
             case 18:slovo = "N"; break;
             case 19:slovo = "Nj"; break;
             case 20:slovo = "O"; break;
             case 21:slovo = "P"; break;
             case 22:slovo = "R"; break;
             case 23:slovo = "S"; break;
             case 24:slovo = "Š"; break;
             case 25:slovo = "T"; break;
             case 26:slovo = "U"; break;
             case 27:slovo = "V"; break;
             case 28:slovo = "Z"; break;
             case 29:slovo="Ž"; break;
         }       
    
        pojmovi = new String[8];
    }

    public String[] getPojmovi() {
        return pojmovi;
    }

    public void setPojmovi(String[] pojmovi) {
        this.pojmovi = pojmovi;
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

    public String getSlovo() {
        return slovo;
    }

    public void setSlovo(String slovo) {
        this.slovo = slovo;
    }
    
   
    
    
public int proveriPojedinacanPojam(String pojam,int i){
    String pom = pojam.toUpperCase();
    char pocetno = pom.charAt(0);
    char trazeno = slovo.charAt(0);
    if (pocetno!=trazeno) return -1;
    
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Criteria cr = session.createCriteria(ZanimljivaGeografija.class);
    switch(i){
    
        case 0:cr.add(Restrictions.eq("drzava", pojam)); break;
        case 1:cr.add(Restrictions.eq("grad", pojam)); break;
        case 2:cr.add(Restrictions.eq("jezero", pojam)); break;
        case 3:cr.add(Restrictions.eq("planina", pojam)); break;
        case 4:cr.add(Restrictions.eq("reka", pojam)); break;
        case 5:cr.add(Restrictions.eq("zivotinja", pojam)); break;
        case 6:cr.add(Restrictions.eq("biljka", pojam)); break;
        case 7:cr.add(Restrictions.eq("grupa", pojam)); break;
   
    }
    List<ZanimljivaGeografija> zg;    
    zg =(List<ZanimljivaGeografija>) cr.list();
    session.getTransaction().commit();

    if (zg.size()>0) return 2;
    else {
    session.beginTransaction();
    cr = session.createCriteria(SuperOdobrenje.class);
    String kat="";
    switch(i){
        case 0:kat="drzava"; break;
        case 1:kat="grad"; break;
        case 2:kat="jezero"; break;
        case 3:kat="planina"; break;
        case 4:kat="reka"; break;
        case 5:kat="zivotinja"; break;
        case 6:kat="biljka"; break;
        case 7:kat="grupa"; break;
   }
        SuperOdobrenje so = new SuperOdobrenje();
        so.setKategorija(kat); so.setPojam(pojam); 
        Date today = Calendar.getInstance().getTime();
        String datum;
        String danas = String.valueOf(today);
        String god = danas.substring(25, danas.length());
        danas = danas.substring(0, 10);
        danas = danas.concat(" ");
        danas = danas.concat(god);
        so.setDatum(danas); 
        Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
      if (k!=null)  so.setUsername(k.getUsername());
        session.save(so);
        session.getTransaction().commit();
        session.close();

        return 0;
    }
    }
    
    
    public String proveri(){
    mess = true;
    int i=0;
    int rez=0;
    int supervizor=0;
    while(i<pojmovi.length){
    String pojam = pojmovi[i];
    if (pojam.compareTo("")==0) {i++; rez+=0; continue;}
    int flag;
    flag = proveriPojedinacanPojam(pojam,i);
    
    if (flag==-1) flag=0;
    else if (flag==0) supervizor++;
    rez+=flag;
    i++;
    }
   
    String rezultat = String.valueOf(rez);
    
   Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
    if (k!=null){
     Date today = Calendar.getInstance().getTime();
        String datum;
        String danas = String.valueOf(today);
        String god = danas.substring(25, danas.length());
        danas = danas.substring(0, 10);
        danas = danas.concat(" ");
        danas = danas.concat(god);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
        session.beginTransaction();
        Criteria cr = session.createCriteria(Rezultati.class);
        cr.add(Restrictions.eq("datum", danas));
        cr.add(Restrictions.eq("username", k.getUsername()));
        Rezultati rezu; 
        rezu =(Rezultati) cr.uniqueResult();
        session.getTransaction().commit();
        
        session.beginTransaction();
        cr = session.createCriteria(Rezultati.class);
        rezu.setGeografija(rez); rezu.setRezultat(rezu.getRezultat()+rez);
        session.update(rezu);
        session.getTransaction().commit();
        session.close();}
    
    if (supervizor>0) {
    poruka = "Neki od Vaših odgovara su poslati, supervizoru na odobrenje! Minimalan broj poena koje ste osvojili je ";
    poruka=poruka.concat(rezultat);
    }else poruka="Osvojili ste " + rezultat+" poena"; 
    
    return "geografijaIgraDana";
    }
    
    
    
     public void setTajmer(int tajmer) {
        this.tajmer = tajmer;
    }

    public int getTajmer() {
        return tajmer;
    }
    
    public void inkrement(){
    if (ok){
    tajmer--;
    if (tajmer<=0) {proveri(); stop = true; ok = false;}
    }
    if (stop==true){
        
    tajmer = 0;
      RequestContext reqCtx = RequestContext.getCurrentInstance();
      reqCtx.execute("poll.stop();");
    FacesContext context = FacesContext.getCurrentInstance();
    try {
           context.getExternalContext().redirect("5x5IgraDana.xhtml");
       } catch (IOException ex) {
           Logger.getLogger(peharIgraDanaKontroler.class.getName()).log(Level.SEVERE, null, ex);
       }}
    
    
    }
    
    public String predajOdg(){
    
    proveri();
    
    return "5x5IgraDana.xhtml?faces-redirect=true";
    
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

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
    
    
