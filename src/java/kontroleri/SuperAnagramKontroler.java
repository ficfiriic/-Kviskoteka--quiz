/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Anagram;
import entiteti.Korisnici;
import java.io.Serializable;
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
 * 
 */
@javax.faces.bean.ManagedBean(name="SuperAnagramKontroler")
@ViewScoped
public class SuperAnagramKontroler{
    
    String zagonetka;
    String resenje;
    String poruka;
    boolean ok;

    public String getZagonetka() {
        return zagonetka;
    }

    public String getResenje() {
        return resenje;
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isOk() {
        return ok;
    }

    public void setZagonetka(String zagonetka) {
        this.zagonetka = zagonetka;
    }

    public void setResenje(String resenje) {
        this.resenje = resenje;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean provera (){
      
    String s1 = zagonetka;
    String s2 = resenje;
    boolean isAnagram = true;
   int i=0; int j=0; int slo1=0; int slo2=0;
   while (i<s1.length() && j<s2.length()){
   
       if (s1.charAt(i)!=' ' && s2.charAt(j)!=' '){
       String pom = s1.substring(i);
       String pom2 = s2.substring(j, j+1);
       if (pom.matches(s2)) isAnagram=false;
       }
       if(isAnagram==false) break;
       if (s2.charAt(j)==' ' ) j++;
       else if (s1.charAt(i)==' ') i++;
       else {i++;j++;}
   }
  //if ((i==s1.length() && j<s2.length()) || (i<s1.length() && j==s2.length())) return true; 
  return isAnagram;
   
    } 
    
    public String ubaci(){
        ok = true;
    
     if ((zagonetka.compareTo("")==0) || (resenje.compareTo("")==0)) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Zagonetka i njeno rešenje su obavezni!"));
        return "";}   
        
   // boolean check = provera();
    
    //if (check==false) {poruka="Proverite unos!"; return "superAnagram";}
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
       
    
        session.beginTransaction();
        Criteria cr = session.createCriteria(Anagram.class);
        Korisnici k;
        cr.add(Restrictions.eq("zagonetka", zagonetka));
        cr.add(Restrictions.eq("resenje", resenje));
        Anagram an;
        an =(Anagram) cr.uniqueResult();
        session.getTransaction().commit();
    
        if (an!=null) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Anagram već postoji!"));
        return "";
        }
        Anagram a = new Anagram();
        a.setResenje(resenje); a.setZagonetka(zagonetka);
    
        session.beginTransaction();
        session.save(a);
        session.getTransaction().commit();
        
                
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Uspešno ste dodali anagram!"));
        return "";
    }
    
    
}
