/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Korisnici;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
@Named(value="ZaboravljenaKontroler")
@SessionScoped
public class ZaboravljenaKontroler implements Serializable{
    
    String username;
    String jmbg;
    String poruka;
    boolean ok = false;
    
   public static String statickiUsername;
    public static String statickiJMBG;

    public String getUsername() {
        return username;
    }

    public String getJmbg() {
        return jmbg;
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
    
    

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }
    
    public String sledeciKorak(){
    
        if (username.compareTo("")==0 || jmbg.compareTo("")==0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Username i jmbg su obavezni!"));
        return "";    
        
        }
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Korisnici.class);
        Korisnici k;
        cr.add(Restrictions.eq("username", username));
        cr.add(Restrictions.eq("jmbg", jmbg));
      //  List<Korisnici> korisnici = new ArrayList<Korisnici>();
        //Korisnici k;
        k =(Korisnici) cr.uniqueResult();
        session.getTransaction().commit();
        
    
        if (k==null) {
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Proverite username i jmbg!"));
        return "";    
        
        
        }
        
         HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
          sesija.setAttribute("korisnik", k);

          statickiJMBG=jmbg;
          statickiUsername=username;
          jmbg="";
          username="";
            
        return "nextStep?faces-redirect=true";
    
    }
    
}
