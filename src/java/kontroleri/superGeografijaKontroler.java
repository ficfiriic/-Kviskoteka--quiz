/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.IgraDana;
import entiteti.KorisniciWaiting;
import entiteti.Rezultati;
import entiteti.SuperOdobrenje;
import entiteti.ZanimljivaGeografija;
import java.io.Serializable;
import java.util.List;
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
@SessionScoped
@Named(value="superGeografijaKontroler")
public class superGeografijaKontroler implements Serializable{
    
    List<SuperOdobrenje> zahtevi;
    String poruka;
    boolean ok = true;
    
    public superGeografijaKontroler(){
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(SuperOdobrenje.class);
      //  List<Korisnici> korisnici = new ArrayList<Korisnici>();
        //Korisnici k;
        zahtevi =(List<SuperOdobrenje>) cr.list();
        session.getTransaction().commit();

        if (zahtevi.size()>0) {poruka="Dospeli pojmovi na proveru:"; }
        else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Nemate zahteva za odobrenje!"));
        }
       }

    public List<SuperOdobrenje> getZahtevi() {
        return zahtevi;
    }

    public void setZahtevi(List<SuperOdobrenje> zahtevi) {
        this.zahtevi = zahtevi;
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
    
    
    
    public String odobri(SuperOdobrenje zahtev){
    
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(SuperOdobrenje.class);
        session.delete(zahtev);
        session.getTransaction().commit();

        
        session.beginTransaction();
        cr = session.createCriteria(Rezultati.class);
        cr.add(Restrictions.eq("username", zahtev.getUsername()));
        cr.add(Restrictions.eq("datum", zahtev.getDatum()));
        Rezultati rez = (Rezultati) cr.uniqueResult();
        session.getTransaction().commit();

        rez.setRezultat(rez.getRezultat()+4);
        rez.setGeografija(rez.getGeografija()+4);
    
        session.beginTransaction();
        cr = session.createCriteria(Rezultati.class);
        session.saveOrUpdate(rez);
        session.getTransaction().commit();
    
        session.beginTransaction();
        cr = session.createCriteria(ZanimljivaGeografija.class);
        ZanimljivaGeografija zg = new ZanimljivaGeografija();
        
        if (zahtev.getKategorija().compareTo("drzava")==0) zg.setDrzava(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("grad")==0) zg.setGrad(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("planina")==0) zg.setPlanina(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("reka")==0) zg.setReka(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("jezero")==0) zg.setJezero(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("biljka")==0) zg.setBiljka(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("zivotinja")==0) zg.setZivotinja(zahtev.getPojam());
        if (zahtev.getKategorija().compareTo("grupa")==0) zg.setGrupa(zahtev.getPojam());
        session.save(zg);
        session.getTransaction().commit();
        zahtevi.remove(zahtev);
        
        
    if (zahtevi.size()==0){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Nemate zahteva za odobrenje!"));
    return "superGeografija?faces-redirect=true";    
    }
        
        return "superGeografija?faces-redirect=true";
    }
    

public String odbij(SuperOdobrenje zahtev){

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Criteria cr = session.createCriteria(SuperOdobrenje.class);
    session.delete(zahtev);
    session.getTransaction().commit();

    zahtevi.remove(zahtev);
    if (zahtevi.size()==0){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Nemate zahteva za odobrenje!"));
    return "";    
    }
    return "superGeografija?faces-redirect=true";
}    
}
