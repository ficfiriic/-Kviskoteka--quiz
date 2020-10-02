/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.BCrypt;
import db.HibernateUtil;
import entiteti.Korisnici;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value="LogInKontroler")
@SessionScoped
public class LogInKontroler implements Serializable{
    
    String username;
    String password;
    boolean poruka = false;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPoruka() {
        return poruka;
    }

    public void setPoruka(boolean poruka) {
        this.poruka = poruka;
    }
    
    
    
    
    public String login(){
    
        if (username.compareTo("")==0 || password.compareTo("")==0){
            
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Username i password su obavezni!"));
        return "";
        }
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Korisnici.class);
        Korisnici k;
        cr.add(Restrictions.eq("username", username));
        //cr.add(Restrictions.eq("password", password));
      //  List<Korisnici> korisnici = new ArrayList<Korisnici>();
        //Korisnici k;
        k =(Korisnici) cr.uniqueResult();
        session.getTransaction().commit();
        
        if (k!=null){
        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
       sesija.setAttribute("korisnik", k);
       boolean flag = BCrypt.checkpw(password, k.getPassword());
       if (flag==false){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Proverite username i password!"));
        return "";
       }else {
           String tip = k.getTip();
           tip = tip.concat(".xhtml?faces-redirect=true");
           session.close();
           return tip;
}
        }       
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Proverite username i password!"));
        
            return "";
    
    }
    
    
    public String logout(){
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "index?faces-redirect=true";
    
    }
}
