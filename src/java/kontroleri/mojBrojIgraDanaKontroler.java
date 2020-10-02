/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.HibernateUtil;
import entiteti.Korisnici;
import entiteti.Rezultati;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Tanja
 */
@javax.faces.bean.ManagedBean(name="mojBrojIgraDanaKontroler")
@ViewScoped
public class mojBrojIgraDanaKontroler{
    
    int turn;
    String trazeniBroj;
    String prvi;
    String drugi;
    String treci;
    String cetvrti;
    String peti;
    String sesti;
    boolean dugme=true;
    String mojIzraz;
    boolean traz=false; boolean p=false; boolean d=false; boolean t=false; boolean c=false; boolean pe=false; boolean s=false;

    String poruka;
    boolean mess;
    int tajmer = 60;
    boolean kraj=false; boolean ok; boolean stop=false;
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
    
    
    public int getTurn() {
        return turn;
    }

    public void setMojIzraz(String mojIzraz) {
        this.mojIzraz = mojIzraz;
    }

    public String getMojIzraz() {
        return mojIzraz;
    }
    
    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isDugme() {
        return dugme;
    }

    public void setDugme(boolean dugme) {
        this.dugme = dugme;
    }

    public String getTrazeniBroj() {
        return trazeniBroj;
    }

    public void setTrazeniBroj(String trazeniBroj) {
        this.trazeniBroj = trazeniBroj;
    }

    public String getPrvi() {
        return prvi;
    }

    public void setPrvi(String prvi) {
        this.prvi = prvi;
    }

    public String getDrugi() {
        return drugi;
    }

    public void setDrugi(String drugi) {
        this.drugi = drugi;
    }

    public String getTreci() {
        return treci;
    }

    public void setTreci(String treci) {
        this.treci = treci;
    }

    public String getCetvrti() {
        return cetvrti;
    }

    public void setCetvrti(String cetvrti) {
        this.cetvrti = cetvrti;
    }

    public String getPeti() {
        return peti;
    }

    public void setPeti(String peti) {
        this.peti = peti;
    }

    public String getSesti() {
        return sesti;
    }

    public void setSesti(String sesti) {
        this.sesti = sesti;
    }

    public boolean isTraz() {
        return traz;
    }

    public void setTraz(boolean traz) {
        this.traz = traz;
    }

    public boolean isP() {
        return p;
    }

    public void setP(boolean p) {
        this.p = p;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public boolean isT() {
        return t;
    }

    public void setT(boolean t) {
        this.t = t;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public boolean isPe() {
        return pe;
    }

    public void setPe(boolean pe) {
        this.pe = pe;
    }

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }
    
    
    
public void stop(){
int broj;
turn++;
switch(turn){

    case 1:
        broj = (int)(Math.random()*1000);
        trazeniBroj = String.valueOf(broj);
        traz = true;
        break;
       // return "mojBrojIgraDana.xhtml?faces-redirect=true";
    case 2:        
        broj = (int)(Math.random()*10);
        if (broj==0) broj=1;
        prvi = String.valueOf(broj);
        p = true;
        break;
       // return "mojBrojIgraDana.xhtml?faces-redirect=true";
    case 3:        
        broj = (int)(Math.random()*10);
        if (broj==0) broj=1;
        drugi = String.valueOf(broj);
        d = true;
        break;
       // return "mojBrojIgraDana.xhtml?faces-redirect=true";
    case 4:
        broj = (int)(Math.random()*10);
        if (broj==0) broj=1;
        treci = String.valueOf(broj);
        t = true;
        break;
       // return "mojBrojIgraDana.xhtml?faces-redirect=true";
    case 5:
        broj = (int)(Math.random()*10);
        if (broj==0) broj=1;
        cetvrti = String.valueOf(broj);
        c = true;
        break;
        //return "mojBrojIgraDana.xhtml?faces-redirect=true";
    
    case 6:
        broj = (int)(Math.random()*3);
        switch(broj){
            case 0: broj = 10; break;
            case 1: broj = 15; break;
            case 2: broj = 20; break;
                }
        peti = String.valueOf(broj);
        pe = true;
        break;
        //return "mojBrojIgraDana.xhtml?faces-redirect=true";
    
    case 7:
        broj = (int)(Math.random()*4);
        switch(broj){
            case 0: broj = 25; break;
            case 1: broj = 50; break;
            case 2: broj = 75; break;
            case 3: broj = 100; break;
                }
        
        sesti = String.valueOf(broj);
        s = true;
        ok = true;
        dugme = false;
        tajmer=60;
        
       // return "mojBrojIgraDana.xhtml?faces-redirect=true";
    


}
//return "mojBrojIgraDana.xhtml?faces-redirect=true";
}

public double racunaj(){
char[] tokens = mojIzraz.toCharArray(); 
  
         // Stack for numbers: 'values' 
        LinkedList<Double> values = new LinkedList<>(); 
  
        // Stack for Operators: 'ops' 
        LinkedList<Character> ops = new LinkedList<>(); 
  
        for (int i = 0; i < tokens.length;) 
        { 
             // Current token is a whitespace, skip it 
            if (tokens[i] == ' ') {i++;
                continue; }
  
            // Current token is a number, push it to stack for numbers 
            if (tokens[i] >= '0' && tokens[i] <= '9') 
            { 
                StringBuffer sbuf = new StringBuffer(); 
                // There may be more than one digits in number 
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') 
                    sbuf.append(tokens[i++]); 
                values.push(Double.parseDouble(sbuf.toString())); 
            } 
  
            // Current token is an opening brace, push it to 'ops' 
            else if (tokens[i] == '(') 
                ops.push(tokens[i++]); 
  
            // Closing brace encountered, solve entire brace 
            else if (tokens[i] == ')') 
            { 
                while (ops.peek() != '(') 
                  values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
                ops.pop(); 
                i++;
            } 
  
            // Current token is an operator. 
            else if (tokens[i] == '+' || tokens[i] == '-' || 
                     tokens[i] == '*' || tokens[i] == '/') 
            { 
                // While top of 'ops' has same or greater precedence to current 
                // token, which is an operator. Apply operator on top of 'ops' 
                // to top two elements in values stack 
                while (!ops.isEmpty() && hasPrecedence(tokens[i], ops.peek())) 
                  values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
  
                // Push current token to 'ops'. 
                ops.push(tokens[i]); 
                i++;
            } 
        } 
  
        // Entire expression has been parsed at this point, apply remaining 
        // ops to remaining values 
        while (!ops.isEmpty()) 
            values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
  
        // Top of 'values' contains result, return it 
        return (double)values.pop(); 
    } 
  
    // Returns true if 'op2' has higher or same precedence as 'op1', 
    // otherwise returns false. 
    public boolean hasPrecedence(char op1, char op2) 
    { 
        if (op2 == '(' || op2 == ')') 
            return false; 
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) 
            return false; 
        else
            return true; 
    } 

public double applyOp(char op, double b, double a) 
    { 
        switch (op) 
        { 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case '*': 
            return a * b; 
        case '/': 
            if (b == 0) 
                throw new
                UnsupportedOperationException("Cannot divide by zero"); 
            return a / b; 
        } 
        return 0; 
    }



public boolean proveriBrojeve(){

    String[] niz =mojIzraz.split("\\+|\\*|-|/|\\(|\\)");
    int pr=0; int dr=0;
    int tr=0; int ce=0;
    int pe=0; int se=0;
    boolean problems = false;
    int i=0;
    
    while (i<niz.length){

    if (niz[i].compareTo("")==0);
    else if (niz[i].compareTo(prvi)==0 && pr==0) pr++;
    else if (niz[i].compareTo(drugi)==0 && dr==0) dr++;
    else if (niz[i].compareTo(treci)==0 && tr==0) tr++;
    else if (niz[i].compareTo(cetvrti)==0 && ce==0) ce++;
    else if (niz[i].compareTo(peti)==0 && pe==0) pe++;
    else if (niz[i].compareTo(sesti)==0 && se==0) se++;
    else {problems=true; break;}
    i++;
}
    
    if (problems) return false;
    
    return true;

}

public boolean proveriZagrade(){

    char[] tokens = mojIzraz.toCharArray();
    
    int otvorena = 0; int zatvorena = 0;
    
    int i = 0;
    while (i<tokens.length){
    
        if (tokens[i]=='(') otvorena++;
        if (tokens[i]==')' && otvorena>zatvorena) zatvorena++;
        else if (tokens[i]==')') return false;
        i++;
    }
    
    if (zatvorena!=otvorena) return false;
    return true;
    


}

public boolean proveriIzraz(){


    char[] tokens = mojIzraz.toCharArray();

    if (tokens[0]=='+' || tokens[0]=='-' || tokens[0]=='/' || tokens[0]=='*') return false;
    if (tokens[tokens.length-1]=='+' || tokens[tokens.length-1]=='-' || tokens[tokens.length-1]=='/' || tokens[tokens.length-1]=='*') return false;

    boolean flag = proveriZagrade();
    
    if (flag==false) return false;
    
    int i=0;
    while (i<tokens.length){
    
    if (tokens[i]=='+' || tokens[i]=='-' || tokens[i]=='/' || tokens[i]=='*'){
    if (tokens[i+1]=='+' || tokens[i+1]=='-' || tokens[i+1]=='/' || tokens[i+1]=='*' || tokens[i+1]==')') return false;
 }
    if (tokens[i]=='(')
        if (tokens[i+1]=='+' || tokens[i+1]=='-' || tokens[i+1]=='/' || tokens[i+1]=='*') return false;
     
     i++; }
    
    return true;

}
public String proveri(){
   
    int i=0;
    ok = false;
    mess = true;
    if (mojIzraz.compareTo("")==0){poruka="Osvojili ste 0 poena!"; return "";}
 
    
    boolean flag = proveriIzraz();
    
    if (flag==false){poruka="Greska u izrazu!"; return "";}
    
    flag = proveriBrojeve();
    
    if (flag==false) {poruka="Trebalo bi da koristite ponudjene brojeve, osvojili ste 0 poena!"; return "";}
    
   double rez = racunaj();

   if ((rez%1)!=0) {poruka="Odgovor mora biti ceo broj, osvojili ste 0 poena!"; return "";}
   
   int rezultat = (int) rez;
   if (rezultat!=Integer.parseInt(trazeniBroj)) {poruka="Dobili ste netačno rešenje, nažalost osvojili ste 0 poena!";rezultat=-1; return "";}
   else {poruka = "Čestitamo dobili ste tačno rešenje i osvojili 10 poena"; rezultat = 10;}

   Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
   
   if (k!=null){
       Date today = Calendar.getInstance().getTime();
        String datum;
        String danas = String.valueOf(today);
        String god = danas.substring(25, danas.length());
        danas = danas.substring(0, 10);
        danas = danas.concat(" ");
        danas = danas.concat(god);
         SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
        session.beginTransaction();
        Criteria cr = session.createCriteria(Rezultati.class);
        cr.add(Restrictions.eq("datum", danas));
        cr.add(Restrictions.eq("username", k.getUsername()));
        Rezultati rezu; 
        rezu =(Rezultati) cr.uniqueResult();
        session.getTransaction().commit();
        
        session.beginTransaction();
        cr = session.createCriteria(Rezultati.class);
        rezu.setMojBroj(rezultat); rezu.setRezultat(rezu.getRezultat()+rezultat);
        session.update(rezu);
        session.getTransaction().commit();
        session.close();}
return "mojBrojIgraDana";
}


public void setTajmer(int tajmer) {
        this.tajmer = tajmer;
    }

    public int getTajmer() {
        return tajmer;
    }
    
    public void inkrement(){
    if (ok){
    tajmer--;
    if (tajmer<=0) {proveri(); stop = true;}
    }
    if (stop==true){
    tajmer = 0;
    RequestContext reqCtx = RequestContext.getCurrentInstance();
    reqCtx.execute("poll.stop();");
    FacesContext context = FacesContext.getCurrentInstance();
    try {
           context.getExternalContext().redirect("geografijaIgraDana.xhtml");
       } catch (IOException ex) {
           Logger.getLogger(peharIgraDanaKontroler.class.getName()).log(Level.SEVERE, null, ex);
       }}
    
    }
    
    public String predajOdg(){
    
    proveri();
    
    return "geografijaIgraDana.xhtml?faces-redirect=true";
    }
    
    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    
    public void kraaj(){
    kraj=true;
    }

    public boolean isKraj() {
        return kraj;
    }

    public void setKraj(boolean kraj) {
        this.kraj = kraj;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    
    
    

}
