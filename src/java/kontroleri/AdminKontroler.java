/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Korisnici;
import entiteti.KorisniciWaiting;
import java.io.Serializable;
import java.util.List;
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

/**
 *
 * @author Tanja
 */
@javax.faces.bean.ManagedBean(name="AdminKontroler")
@ViewScoped
public class AdminKontroler{
    
    List<KorisniciWaiting> waiting;
    boolean ok=false;
    
public AdminKontroler(){

    dospeliZahtevi();

}

    public List<KorisniciWaiting> getWaiting() {
        return waiting;
    }

    public void setWaiting(List<KorisniciWaiting> waiting) {
        this.waiting = waiting;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean empty) {
        this.ok = empty;
    }

    
public void dospeliZahtevi(){

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(KorisniciWaiting.class);
      //  List<Korisnici> korisnici = new ArrayList<Korisnici>();
        //Korisnici k;
        waiting =(List<KorisniciWaiting>) cr.list();
        session.getTransaction().commit();

        if (waiting.size()>0) ok = true;
        else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Obaveštenje!", "Nema novih zahteva!"));

        }
}

public String prihvatiZahtev(KorisniciWaiting k){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Korisnici korisnik = new Korisnici();
        korisnik.update(k);
        session.beginTransaction();
        session.save(korisnik);
        session.getTransaction().commit();
        
        session.beginTransaction();
        session.delete(k);
        session.getTransaction().commit();
        
        waiting.remove(k);
        if (waiting.size()>0) ok = true;
        else ok = false;

        return "adminZahtevi?faces-redirect=true";



}


public String obrisiZahtev(KorisniciWaiting k){

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(k);
        session.getTransaction().commit();
        
        waiting.remove(k);
        if (waiting.size()>0) ok = true;
        else ok = false;

        return "adminZahtevi?faces-redirect=true";

}
}

