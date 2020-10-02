/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.BCrypt;
import db.HibernateUtil;
import entiteti.Korisnici;
import entiteti.KorisniciWaiting;
import java.io.Serializable;
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
@Named(value="PitanjeIOdgovorKontroler")
@SessionScoped
public class PitanjeIOdgovorKontroler implements Serializable{
    
    String pitanje;
    String odgovor;
    Korisnici k;
    
    String noviPass;
    String ponovljeniNoviPass;
    String poruka;
    boolean ok = false;
    
    
    public PitanjeIOdgovorKontroler(){
    
        dohvatiPitanje();
       
    }

    public String getNoviPass() {
        return noviPass;
    }

    public String getPonovljeniNoviPass() {
        return ponovljeniNoviPass;
    }

    public void setNoviPass(String noviPass) {
        this.noviPass = noviPass;
    }

    public void setPonovljeniNoviPass(String ponovljeniNoviPass) {
        this.ponovljeniNoviPass = ponovljeniNoviPass;
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isOk() {
        return ok;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    
    
    
    
    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    
    
    public String getPitanje() {
        return pitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }
    
    
    
    public void dohvatiPitanje(){
    
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Korisnici.class);
        cr.add(Restrictions.eq("username",ZaboravljenaKontroler.statickiUsername));
        cr.add(Restrictions.eq("jmbg", ZaboravljenaKontroler.statickiJMBG));
      //  List<Korisnici> korisnici = new ArrayList<Korisnici>();
        //Korisnici k;
        k =(Korisnici) cr.uniqueResult();
        session.getTransaction().commit();
        
        int p = k.getPitanje();

        switch(p){
            case 1:
                pitanje = "Ime majke?"; break;
            
            case 2: pitanje = "Ime oca?"; break;
            case 3: pitanje = "Ime osnovne škole?"; break;
            case 4: pitanje = "Ime srednje škole?"; break;
            case 5: pitanje = "Omiljena boja?"; break;
        
        }
    }
    
    public String proveriOdgovor(){
    
    
        if (k.getOdgovor().equals(odgovor)) {odgovor="";return "nextStep1?faces-redirect=true";}
        odgovor="";
        return "index?faces-redirect=true";
        }
    
    public String promeniLozinku(){
    
        if (noviPass.compareTo("")==0 || ponovljeniNoviPass.compareTo("")==0){
            
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Sva polja su obavezna!"));
        return "";
            
        }
        ok = true;
        boolean flag = noviPass.matches("^(?!.*([A-Za-z0-9@$!%*?&amp;])\\1{2})(?=.*?[A-Z])(?=(.*?[a-z]){3,})(?=.*?[0-9])(?=.*?[@$!%*?&amp;])(?=)[A-Za-z][A-Za-z\\d@$!%*?&amp;]{7,11}$");
        
        if (flag==false){
            
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Password mora da sadrzi od 8 do 12 karaktera, pocinje malim ili velikim slovom, ima barem 3 mala slova,bar jedno veliko slovo,bar jedan broj i bar jedan specijalan karakter!"));
        return "";
        }
        if (noviPass.equals(ponovljeniNoviPass)==false) {
            
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Passwordi nisu isti!"));
        return "";
        }
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Korisnici.class);
        noviPass = BCrypt.hashpw(noviPass, BCrypt.gensalt(10));
        k.setPassword(noviPass);
        session.saveOrUpdate(k);
        session.getTransaction().commit();
        
        noviPass="";
        ponovljeniNoviPass="";
            
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Uspešno ste promenili password!"));
        return "";
    
    }
}
