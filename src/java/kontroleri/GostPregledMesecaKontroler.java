/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Rezultati;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
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
@Named(value="GostPregledMesecaKontroler")
public class GostPregledMesecaKontroler implements Serializable{
    
      List<Rezultati> listaRezultata;
      String poruka;
      boolean mess = true;
      boolean show = false;
      String danas;
      int i;
      
      
      public GostPregledMesecaKontroler(){
      
            
    listaRezultata = new LinkedList<>();
    
    Date today = Calendar.getInstance().getTime();
    danas = String.valueOf(today);
    String god = danas.substring(25, danas.length());
    danas = danas.substring(0, 10);
    danas = danas.concat(" ");
    danas = danas.concat(god);
    String mesec = danas.substring(4, 7);
    
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Criteria cr = session.createCriteria(Rezultati.class);
    listaRezultata = cr.list();
    session.getTransaction().commit();
    
    int i = 0;
    while (i<listaRezultata.size()){
    Rezultati r = listaRezultata.get(i);
    
    if (r.getDatum().substring(4, 7).compareTo(mesec)!=0) listaRezultata.remove(r);
    else i++;
    }
    
    if (listaRezultata.isEmpty()) {poruka="Ocekujemo prve igrace uskoro!"; mess = false;}
    else poruka="Pregled rezultata za tekući mesec";
    
    listaRezultata.sort(Comparator.comparing(Rezultati::getRezultat).reversed());
      
      }

    public List<Rezultati> getListaRezultata() {
        return listaRezultata;
    }

    public void setListaRezultata(List<Rezultati> listaRezultata) {
        this.listaRezultata = listaRezultata;
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

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
      
      
    
}
