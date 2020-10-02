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
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
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
@ManagedBean(name="KrajIgreKontroler")
@ViewScoped
public class KrajIgreKontroler{
    
    
   Rezultati  moi;
    
    public KrajIgreKontroler(){
        Date today = Calendar.getInstance().getTime();
        String datum;
        String danas = String.valueOf(today);
        String god = danas.substring(25, danas.length());
        danas = danas.substring(0, 10);
        danas = danas.concat(" ");
        danas = danas.concat(god);
        Korisnici korisnik = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Rezultati.class);
        cr.add(Restrictions.eq("datum", danas));
        cr.add(Restrictions.eq("username", korisnik.getUsername()));
        moi =(Rezultati) cr.uniqueResult();
        session.getTransaction().commit();

    
    }

    public Rezultati getMoi() {
        return moi;
    }

    public void setMoi(Rezultati moi) {
        this.moi = moi;
    }
    
    
    
}
