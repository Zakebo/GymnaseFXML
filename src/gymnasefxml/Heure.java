/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymnasefxml;

/**
 *
 * @author zakeb
 */
public class Heure
  {
    private String heure;
    private boolean libre;
    
    
    public Heure()
    {
        
    }
    public Heure(String h)
    {
        heure = h;
        libre = true;
    }
    
    
    public Heure(String h, boolean l)
    {
        heure = h;
        libre = l;
    }

    public String getHeure()
    {
        return heure;
    }

    public void setHeure(String heure)
    {
        this.heure = heure;
    }

    public boolean isLibre()
    {
        return libre;
    }

    public void setLibre(boolean libre)
    {
        this.libre = libre;
    }
    
    @Override
    public String toString()
    {
        return this.getHeure();
    }
  }
