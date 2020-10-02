/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.BCrypt;
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
 * 
 */
@ManagedBean
@Named(value="MenjanjeLozinkeKontroler")
@SessionScoped
public class MenjanjeLozinkeKontroler implements Serializable{
    
    String username;
    String password;
    String ponovljeniPassword;
    String noviPassword;
    String poruka;
    boolean ok = false;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPonovljeniPassword(String ponovljeniPassword) {
        this.ponovljeniPassword = ponovljeniPassword;
    }

    public void setNoviPassword(String noviPassword) {
        this.noviPassword = noviPassword;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPonovljeniPassword() {
        return ponovljeniPassword;
    }

    public String getNoviPassword() {
        return noviPassword;
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isOk() {
        return ok;
    }
    
    public String promeniLozinku(){
        
        if (username.compareTo("")==0 || password.compareTo("")==0 || noviPassword.compareTo("")==0 || ponovljeniPassword.compareTo("")==0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Sva polja su obavezna!"));
        return "";
        }
    
        ok = true;
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
        boolean flag = BCrypt.checkpw(password, k.getPassword());
        if (flag==false){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Proverite username i password!"));
        return "";
       }
        }
        boolean flag = noviPassword.matches("^(?!.*([A-Za-z0-9@$!%*?&amp;])\\1{2})(?=.*?[A-Z])(?=(.*?[a-z]){3,})(?=.*?[0-9])(?=.*?[@$!%*?&amp;])(?=)[A-Za-z][A-Za-z\\d@$!%*?&amp;]{7,11}$");
        
        if (flag==false){
            
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Password mora da sadrzi od 8 do 12 karaktera, pocinje malim ili velikim slovom, ima barem 3 mala slova,bar jedno veliko slovo,bar jedan broj i bar jedan specijalan karakter!"));
        return "";
        }
        
        
        if (ponovljeniPassword.equals(noviPassword)==false) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Passwordi nisu isti!"));
        return "";
        }
        
        session.beginTransaction();
        noviPassword = BCrypt.hashpw(noviPassword, BCrypt.gensalt(10));
        k.setPassword(noviPassword);
        session.saveOrUpdate(k);
        
        session.getTransaction().commit();
        session.close();
        return "index?faces-redirect=true";
    }
    
    
    }
    
