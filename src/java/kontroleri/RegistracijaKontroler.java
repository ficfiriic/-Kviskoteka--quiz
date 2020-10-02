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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.UploadedFileWrapper;

/**
 *
 * @author Tanja
 */
@ManagedBean
@Named(value="RegistracijaKontroler")
@SessionScoped
public class RegistracijaKontroler implements Serializable{
    
    String ime;
    String prezime;
    String email;
    String zanimanje;
    String username;
    String password;
    String ponovoPassword;
    String pol;
    String pitanje;
    String jmbg;
    String odgovor;
    UploadedFile pic;
    InputStream input;

    String poruka;
    boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getPoruka() {
        return poruka;
    }
    
    
    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getZanimanje() {
        return zanimanje;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPonovoPassword() {
        return ponovoPassword;
    }

    public String getPol() {
        return pol;
    }

    public String getPitanje() {
        return pitanje;
    }

    public String getJmbg() {
        return jmbg;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPonovoPassword(String ponovoPassword) {
        this.ponovoPassword = ponovoPassword;
    }
  
       
    public void setPol(String pol) {
        this.pol = pol;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }
   
     public void handleFileUpload(FileUploadEvent event) {
        pic = event.getFile();
    }
     
     
    public boolean jmbgProvera(){
    
    if (jmbg.length()!=13) return false;
    
    char niz[] = jmbg.toCharArray();
    int s=0; int j=7;
    for (int i=0; i<niz.length; i++){
    if (Character.isDigit(niz[i])==false) return false;
    int num = (Integer.parseInt(Character.toString(niz[i]))); 
    s+=j*num;
    j--;
    if (j==1) j=7;
    }
    
    int k = s%11;
    int poslednjaCifra = (Integer.parseInt(Character.toString(niz[12])));
    
    if (k!=poslednjaCifra) return false;
    return true;
    }
    
    public void registracija(){
        ok = true;
        
    if (ime.compareTo("")==0 || prezime.compareTo("")==0 || email.compareTo("")==0 || zanimanje.compareTo("")==0 || username.compareTo("")==0 
        || password.compareTo("")==0 || ponovoPassword.compareTo("")==0 || pol==null || odgovor.compareTo("")==0 || jmbg.compareTo("")==0 || pic==null){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Sva polja su obavezna!"));
     return;
    }

    try{

        boolean flag = password.matches("^(?!.*([A-Za-z0-9@$!%*?&amp;])\\1{2})(?=.*?[A-Z])(?=(.*?[a-z]){3,})(?=.*?[0-9])(?=.*?[@$!%*?&amp;])(?=)[A-Za-z][A-Za-z\\d@$!%*?&amp;]{7,11}$");
        
        if (flag==false){
            
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Password mora da sadrzi od 8 do 12 karaktera, pocinje malim ili velikim slovom, ima barem 3 mala slova,bar jedno veliko slovo,bar jedan broj i bar jedan specijalan karakter!"));
        return;
        }
    
     if (password.equals(ponovoPassword)==false) {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Lozinke se ne poklapaju!"));
     return;   
     }
     
     flag=jmbgProvera();
    
    if (flag==false){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Proverite jmbg!"));
     return;   }
  
     if (pic.getSize()>270000) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Slika je prevelika!"));
     return;}
    
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
    
        ArrayList<Korisnici> korisnici = new ArrayList<Korisnici>();
                
        Criteria cr = session.createCriteria(Korisnici.class);
        cr.add(Restrictions.eq("username", username));
      //  List<Korisnici> korisnici = new ArrayList<Korisnici>();
        //Korisnici k;
        korisnici =(ArrayList<Korisnici>) cr.list();
        session.getTransaction().commit();
        
        if (korisnici.isEmpty()==false) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Korisničko ime je zauzeto!"));
     return;   
        }

               session.beginTransaction();
      ArrayList<KorisniciWaiting> kor = new ArrayList<KorisniciWaiting>();
                
        Criteria cri = session.createCriteria(KorisniciWaiting.class);
        cri.add(Restrictions.eq("username", username));
    
        kor =(ArrayList<KorisniciWaiting>) cri.list();
        session.getTransaction().commit();
        
        if (kor.isEmpty()==false) {
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Korisničko ime je zauzeto!"));
     return;
        }
        
        password =  BCrypt.hashpw(password, BCrypt.gensalt(10));
        String ispis = "Duzina passworda je ";
        ispis = ispis.concat(Integer.toString(password.length()));
        System.out.println(ispis);

        KorisniciWaiting kw = new KorisniciWaiting();
        int pit = Integer.parseInt(pitanje);
        kw.setPassword(password);
        int p = Integer.parseInt(pol);
        kw.setIme(ime); kw.setEmail(email); kw.setJmbg(jmbg); kw.setOdgovor(odgovor); 
        kw.setPitanje(pit);kw.setPol(p); kw.setPrezime(prezime); kw.setUsername(username); kw.setZanimanje(zanimanje);
        session.beginTransaction();
        
        session.save(kw);
       
        session.getTransaction().commit();
        
        session.close();
    
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Čestitamo!", "Vaš zahtev za registracijom je poslat na odobrenje!"));
     return;
        
    }catch(Exception e) {}
    // return "registracija?faces-redirect=true";
    }

    public UploadedFile getPic() {
        return pic;
    }

    public void setPic(UploadedFile pic) {
        this.pic = pic;
    }

    
    
    
    
}
