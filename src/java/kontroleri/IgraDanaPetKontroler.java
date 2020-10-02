/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.IgraPetXPet;
import entiteti.Korisnici;
import entiteti.Rezultati;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@javax.faces.bean.ManagedBean(name="IgraDanaPetKontroler")
@ViewScoped
public class IgraDanaPetKontroler{
    
    
    Character slova[];
    Boolean flagSlova[];
    IgraPetXPet igra;
    String unesenoSlovo;
    Rezultati rezu;
    List<Character> pogodjenaSlova;
    Character prvaVertikalno[];
    Character drugaVertikalno[];
    Character trecaVertikalno[];
    Character cetvrtaVertikalno[];
    Character petaVertikalno[];
    int pogodjeneReci;
    Korisnici korisnik;
    int tajmer=120;
    boolean stop = false;
    String poruka;
    SessionFactory sessionFactory;
    Session session;
    
    public IgraDanaPetKontroler(){
    
        stop = false;
        
     Date today = Calendar.getInstance().getTime();
     String datum;
     String danas = String.valueOf(today);
     String god = danas.substring(25, danas.length());
     danas = danas.substring(0, 10);
     danas = danas.concat(" ");
     danas = danas.concat(god);
    
    sessionFactory = HibernateUtil.getSessionFactory();
    session = sessionFactory.openSession();

    List<IgraPetXPet> igre = new LinkedList();
    session.beginTransaction();
    Criteria cr = session.createCriteria(IgraPetXPet.class);
    igre =(List<IgraPetXPet>) cr.list();
    session.getTransaction().commit();

    double rand = (Math.random()*igre.size());
    int j = (int)(rand);
    if (j!=0) j--;
    igra = igre.get(j);
    System.out.println("Ucitao sam reci!");
    izvuciSlova();
    flagSlova = new Boolean[25];
    for (int i=0;i<25;i++) flagSlova[i]= false;
    int k = (int)(Math.random()*24);
    pogodjenaSlova = new LinkedList<>();
    for (int i=0; i<25;i++)
    if (slova[i].compareTo(slova[k])==0) flagSlova[i]=true; 
    System.out.println("Ucitao sam slova!");
    Character c = slova[k];
    pogodjenaSlova.add(slova[k]);
    prvaVertikalno=new Character[5];drugaVertikalno=new Character[5];trecaVertikalno=new Character[5];
    cetvrtaVertikalno=new Character[5];petaVertikalno=new Character[5];
    formirajVertikalneReci();
    
    korisnik = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
    if (korisnik!=null){   
    session.beginTransaction();
    cr = session.createCriteria(Rezultati.class);
    cr.add(Restrictions.eq("datum", danas));
    cr.add(Restrictions.eq("username", korisnik.getUsername())); 
    rezu =(Rezultati) cr.uniqueResult();
    session.getTransaction().commit();
    
    } 
    pogodjeneReci = 0;
    
    }
    public void izvuciSlova(){
    
        slova = new Character[25];
        for (int i=0;i<25;) {
        
            for (int j=0;j<5;j++,i++) slova[i] = igra.getPrva().toUpperCase().charAt(j);
            for (int j=0;j<5;j++,i++) slova[i] = igra.getDruga().toUpperCase().charAt(j);
            for (int j=0;j<5;j++,i++) slova[i] = igra.getTreca().toUpperCase().charAt(j);
            for (int j=0;j<5;j++,i++) slova[i] = igra.getCetvrta().toUpperCase().charAt(j);
            for (int j=0;j<5;j++,i++) slova[i] = igra.getPeta().toUpperCase().charAt(j);
            
        
        }
    
    
    }
    
    public void formirajVertikalneReci(){
    
        
        for(int i=0; i<5;) {prvaVertikalno[i++]=igra.getPrva().charAt(0);
                            prvaVertikalno[i++]=igra.getDruga().charAt(0);
                            prvaVertikalno[i++]=igra.getTreca().charAt(0);
                            prvaVertikalno[i++]=igra.getCetvrta().charAt(0);
                            prvaVertikalno[i++]=igra.getPeta().charAt(0);}
    
    
    for(int i=0; i<5;) {drugaVertikalno[i++]=igra.getPrva().charAt(1);
                            drugaVertikalno[i++]=igra.getDruga().charAt(1);
                            drugaVertikalno[i++]=igra.getTreca().charAt(1);
                            drugaVertikalno[i++]=igra.getCetvrta().charAt(1);
                            drugaVertikalno[i++]=igra.getPeta().charAt(1);}
    
    for(int i=0; i<5;) {trecaVertikalno[i++]=igra.getPrva().charAt(2);
                            trecaVertikalno[i++]=igra.getDruga().charAt(2);
                            trecaVertikalno[i++]=igra.getTreca().charAt(2);
                            trecaVertikalno[i++]=igra.getCetvrta().charAt(2);
                            trecaVertikalno[i++]=igra.getPeta().charAt(2);}
    
for(int i=0; i<5;) {cetvrtaVertikalno[i++]=igra.getPrva().charAt(3);
                            cetvrtaVertikalno[i++]=igra.getDruga().charAt(3);
                            cetvrtaVertikalno[i++]=igra.getTreca().charAt(3);
                            cetvrtaVertikalno[i++]=igra.getCetvrta().charAt(3);
                            cetvrtaVertikalno[i++]=igra.getPeta().charAt(3);}

    for(int i=0; i<5;) {petaVertikalno[i++]=igra.getPrva().charAt(4);
                            petaVertikalno[i++]=igra.getDruga().charAt(4);
                            petaVertikalno[i++]=igra.getTreca().charAt(4);
                            petaVertikalno[i++]=igra.getCetvrta().charAt(4);
                            petaVertikalno[i++]=igra.getPeta().charAt(4);}
    
}
    

    public Character[] getSlova() {
        return slova;
    }

    public void setSlova(Character[] slova) {
        this.slova = slova;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    

    public Boolean[] getFlagSlova() {
        return flagSlova;
    }

    public void setFlagSlova(Boolean[] flagSlova) {
        this.flagSlova = flagSlova;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    
    
    public IgraPetXPet getIgra() {
        return igra;
    }

    public void setIgra(IgraPetXPet igra) {
        this.igra = igra;
    }

    public int getTajmer() {
        return tajmer;
    }

    public void setTajmer(int tajmer) {
        this.tajmer = tajmer;
    }
    
    

    public String getUnesenoSlovo() {
        return unesenoSlovo;
    }

    public void setUnesenoSlovo(String unesenoSlovo) {
        this.unesenoSlovo = unesenoSlovo;
    }
    
    int proveriSlovo(){
    
    Character c = unesenoSlovo.toUpperCase().charAt(0);
    int found = 0;
    for (int i=0; i<25;i++){
    if (slova[i].compareTo(c)==0) {flagSlova[i]=true; found++;}
    }
    if (rezu!=null){
    rezu.setRezultat(rezu.getRezultat()+found); rezu.setPet(rezu.getPet()+found);
    }
    return found;
    }
    
    void proveriReci(){
    boolean eq = true;
    int found = 0;
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(igra.getPrva().toUpperCase().charAt(i))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(igra.getDruga().toUpperCase().charAt(i))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(igra.getTreca().toUpperCase().charAt(i))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(igra.getCetvrta().toUpperCase().charAt(i))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(igra.getPeta().toUpperCase().charAt(i))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(Character.toUpperCase(prvaVertikalno[i]))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(Character.toUpperCase(drugaVertikalno[i]))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(Character.toUpperCase(trecaVertikalno[i]))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(Character.toUpperCase(cetvrtaVertikalno[i]))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    
    for (int i=0; i<5;i++) 
        if (pogodjenaSlova.contains(Character.toUpperCase(petaVertikalno[i]))==false) {eq = false; break;}
    
    if (eq==true) found++;
    eq=true;
    
    int raz= found-pogodjeneReci;
    if (rezu!=null){
    int rez=0;
    rez += raz*2;
    rezu.setRezultat(rezu.getRezultat()+rez); rezu.setPet(rezu.getPet()+rez);
    }
    
    pogodjeneReci+=raz;
    
    }
    
    public void proveraUnesenogSlova(){
        if (stop==false){
        int ret = proveriSlovo();
        if (ret>0) { pogodjenaSlova.add(Character.toUpperCase(unesenoSlovo.charAt(0))); proveriReci();}
       
    }
        unesenoSlovo="";
       if (pogodjeneReci==10){
           session.beginTransaction();
           Criteria cr = session.createCriteria(Rezultati.class);
           session.update(rezu);
           session.getTransaction().commit();
           session.close();
           FacesContext context = FacesContext.getCurrentInstance();
            try {
                context.getExternalContext().redirect("peharIgraDana.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(IgraDanaPetKontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       };
 
    }
    
    public void dekrement(){
    tajmer--;
    
    if (tajmer<=0){stop = true; //da zaustavi brojac
    RequestContext reqCtx = RequestContext.getCurrentInstance();
    reqCtx.execute("poll.stop();");
    FacesContext context = FacesContext.getCurrentInstance();
    session.beginTransaction();
    Criteria cr = session.createCriteria(Rezultati.class);
    session.update(rezu);
    session.getTransaction().commit();
    session.close();
    try {
           context.getExternalContext().redirect("peharIgraDana.xhtml");
       } catch (IOException ex) {
           Logger.getLogger(peharIgraDanaKontroler.class.getName()).log(Level.SEVERE, null, ex);
       }}
    
    }
    }
