/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Anagram;
import entiteti.Rezultati;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Tanja
 */
@ManagedBean
@Named(value="adminRezultatiMesecKontroler")
@SessionScoped
public class adminRezultatiMesecKontroler implements Serializable{
    
    List<Rezultati> listaRez;
    
    public adminRezultatiMesecKontroler(){
    
        listaRez = new LinkedList<>();
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Rezultati.class);
        listaRez =(List<Rezultati>) cr.list();
        session.getTransaction().commit();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        
        Date proslost = new Date(Calendar.getInstance().getTimeInMillis() - (20 * DAY_IN_MS));

        int i=0;
        while (i<listaRez.size()){
        Rezultati rez = listaRez.get(i);
        String dat = rez.getDatum();
            try {
                Date date = new SimpleDateFormat("E MMM dd yyyy").parse(dat);
                
                if (date.before(proslost)) listaRez.remove(rez);
                else i++;
            } catch (ParseException ex) {
                Logger.getLogger(adminRezultatiMesecKontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        listaRez.sort(Comparator.comparing(Rezultati::getRezultat).reversed());
    }

    public List<Rezultati> getListaRez() {
        return listaRez;
    }

    public void setListaRez(List<Rezultati> listaRez) {
        this.listaRez = listaRez;
    }
    
    
}
