/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymnasefxml;

import java.util.ArrayList;

/**
 *
 * @author zakeb
 */
public class Salle
  {
    private String name;
    private int Surface;
    private String revetement;
    ArrayList<String> Sport;

    public Salle(String name, int Surface, String revetement)
    {
        this.name = name;
        this.Surface = Surface;
        this.revetement = revetement;
    //    this.Sport = Sport;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSurface()
    {
        return Surface;
    }

    public void setSurface(int Surface)
    {
        this.Surface = Surface;
    }

    public String getRevetement()
    {
        return revetement;
    }

    public void setRevetement(String revetement)
    {
        this.revetement = revetement;
    }

    public ArrayList<String> getSport()
    {
        return Sport;
    }

    public void setSport(ArrayList<String> Sport)
    {
        this.Sport = Sport;
    }
    
    
  }
