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
public class Association
  {
    private String name;
    private String ville;
    private String adresse;
    private String nomRes;
    private ArrayList<String> sport;
    
    
    public Association(String n, String v, String a, String nR,ArrayList<String> sP)
    {
        name = n;
        ville = v;
        adresse = a;
        nomRes = nR;
        sport = sP;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVille()
    {
        return ville;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getNomRes()
    {
        return nomRes;
    }

    public void setNomRes(String nomRes)
    {
        this.nomRes = nomRes;
    }
    
    public void setSport(ArrayList<String> sport)
    {
        this.sport = sport;
    }
    
  }
