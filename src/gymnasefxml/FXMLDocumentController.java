/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymnasefxml;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

/**
 *
 * @author zakeb
 */
public class FXMLDocumentController implements Initializable
  {
    
    @FXML
    private ListView<Heure> lvHeure;
    @FXML
    private ComboBox<Association> cbAssoc;
    @FXML
    private ComboBox<Salle> cbSalle;
    @FXML
    private Label lblSalle;
    @FXML
    private Label lblAssociation;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblHeure;
    @FXML
    private DatePicker dDate;
    @FXML
    private Button btnReserver;
    @FXML
    private Label lblHeureReservee;
  

   
   
    @FXML
    /*Listener sur la listview*/ 
    private void handleButtonActiontest(MouseEvent event)
    {
        if (lvHeure.getSelectionModel().getSelectedIndex() >= 2 && lvHeure.getSelectionModel().getSelectedIndex() <= 11) {
            lblHeure.setText(lvHeure.getSelectionModel().getSelectedItem().toString());
            if (!lvHeure.getSelectionModel().getSelectedItem().isLibre()) {
                btnReserver.setDisable(true);
                lblHeureReservee.setVisible(true);
            }
            else
            {
                btnReserver.setDisable(false);
                lblHeureReservee.setVisible(false);

            }
        }
        else
        {
            lblHeure.setText("");
        }
        
    
       
            /* Affichage de la date*/    
            
            /*
             String time = (String) lvHeure.getSelectionModel().getSelectedItem();
        String parsing = time.substring(0, 2);
        if (lvHeure.getSelectionModel().getSelectedIndex() >= 2 && lvHeure.getSelectionModel().getSelectedIndex() <= 11)
        {
            if (!Character.isDigit(parsing.charAt(1)) ) {
                            System.out.println(parsing.substring(0, 1));
            }
            else
            {
             System.out.println(parsing.substring(0, 2));
            }
            
        }
            */
    }
        
        
    

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
         /* Appel de la fonction connexion à BDD*/
        try {
           
            Connection conn = connexionBDD();
           Statement st = conn.createStatement();
           Statement st2 = conn.createStatement();
           Statement st3 = conn.createStatement();
           Statement st4 = conn.createStatement();
            /* Insérer les associations*/
           ResultSet result;
           ResultSet result2;
           ResultSet result3;
           ResultSet result4;
           
           result = st.executeQuery("SELECT * from association");
           ObservableList associations = FXCollections.observableArrayList();
           ObservableList  salles      = FXCollections.observableArrayList();
           
            if (result.next()) {
                do {
                   
                    /* Ajout des sports pratiqués par l'assoc*/
                    result2 = st2.executeQuery("Select nomSport from pratiquer where refAsso ='" +  result.getObject(1) + "'");
                    ArrayList<String> sportsPratiques = new ArrayList<String>();
                    if (result2.next()) {
                        do {
                            sportsPratiques.add((String) result2.getObject(1));
                        } while (result2.next());
                    }
                  /* Fin recherche des sports pratiqués  */
                    associations.add(new Association((String)result.getObject(1),(String)result.getObject(2),(String)result.getObject(3),(String)result.getObject(4),(ArrayList<String>) sportsPratiques));
                    sportsPratiques.clear();
                } while (result.next());
                
            }
           cbAssoc.setItems(associations);
           cbAssoc.setConverter(new StringConverter<Association>()   {
               @Override
               public String toString(Association object)
               {
                   return object.getName();
               }
               @Override
               public Association fromString(String string) 
               {
                   return null;
               }
              });
          
           
           /* fermeture de la connexion */
           conn.close();
           result.close();
           st.close();
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Remplissage de la listview */
          ObservableList heures = FXCollections.observableArrayList();
          heures.add(new Heure("HEURES DISPONIBLES"));
          heures.add(new Heure("-----------"));
          for (int i = 8; i < 18 ; i++) {
            heures.add(new Heure(i+"h" + "-" + (i+1)+"h"));
        }   
          
     
         lvHeure.setItems(heures);
        /* Contrôle de saisie */
        if (cbAssoc.getSelectionModel().getSelectedIndex() < 0) {
            cbSalle.setDisable(true);
            cbSalle.setStyle("-fx-opacity: 1; -fx-text-fill: black;-fx-background-color: white");
        }
        
        if (dDate.getValue() == null) {
            lvHeure.setDisable(true);
        }
        /* On cache le label "heure déjà reservée*/
        lblHeureReservee.setVisible(false);
      }
    
    public Connection connexionBDD() throws ClassNotFoundException,SQLException
    {
        String cochaine = "jdbc:mysql://localhost:3306/gymnase";
         Class.forName ("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection(cochaine,"root","");
      
          return conn;
    }
    
    public String getHeureAvecPlage(String plage)
    {
         if (!Character.isDigit(plage.charAt(1)) ) {
                 return (plage.substring(0, 1));
            }
            else
            {
             return (plage.substring(0,2));
            }
    }
   /* Listener sur le changement de la salle dans le combobox */
    @FXML
    private void handleButtonActionChangeCboSalle(ActionEvent event)
    {
        if (cbSalle.getSelectionModel().getSelectedIndex() >= 0) {
                    lblSalle.setText(cbSalle.getSelectionModel().getSelectedItem().getName());
                   
                    /* On regarde si une date a été choisie avant de faire la requête*/
                    if (dDate.getValue() != null) {
                                    try {
                //Update de la listview car changement de salle
                updateListView();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }   
            }

        }
    }

    /*Listener sur le changement de date*/
    @FXML
    private void handleOnChangeDate(ActionEvent event) throws ParseException, ClassNotFoundException
    {
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            /* Date sur le label date */
                lblDate.setText(formatter.format(dDate.getValue()));    
             /* Contrôle de saisie*/
             if (dDate.getValue() != null ) {
            lvHeure.setDisable(false);
            /* Récupération des réservations de la journée*/
            
                 updateListView();                 
        }
    }           

    /* Listener sur la combobox association*/
    
    private void updateListView() throws ClassNotFoundException
    {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         lvHeure.getItems().clear();
                
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        ObservableList newHeureList = FXCollections.observableArrayList();
                        Connection conn;
                        conn = connexionBDD();
                        Statement st = conn.createStatement();
                        ResultSet result = st.executeQuery("Select * from reservation where date = '"+ formatter.format(dDate.getValue()) + "' and refSalle = '" + lblSalle.getText() + "'");
                        SimpleDateFormat sdfTime = new SimpleDateFormat("HH");
                        
                            newHeureList.add(new Heure("Horaires Disponibles"));
                            newHeureList.add(new Heure("--------------"));
                            ArrayList<String> listeHeureReservations = new ArrayList<>();
                            for (int i = 8; i < 18 ; i++) {
                               Heure currentHeure = new Heure(i+"h-"+(i+1)+"h");
                              
                               /* Si il y a au moins 1 réservation pour la date choisie */
                               if (result.next()) {
                                   do {
                                        listeHeureReservations.add((String)sdfTime.format(result.getObject(3)));
                                        System.out.println("Reservations sures : " + result.getObject(3));
                                   } while (result.next());
                                   
                               }
                               for(int j = 0; j < listeHeureReservations.size(); j++) {
                                   String currentHeureAVerifier = getHeureAvecPlage(currentHeure.getHeure());
                                   if (currentHeureAVerifier.length() == 1) {
                                       currentHeureAVerifier = "0"+currentHeureAVerifier;
                                   }
                                   if (currentHeureAVerifier.equals(listeHeureReservations.get(j))) {
                                        currentHeure.setLibre(false);
                                       
                                        System.out.println("reservation à " + currentHeure.getHeure());
                                   }
                               }
                            
                               newHeureList.add(currentHeure);
                           }    
                           
                           
                           
                          lvHeure.setItems(newHeureList);
                          
                            lvHeure.setCellFactory(lv -> new ListCell<Heure>(){
                                      @Override
                                      protected void updateItem(Heure item, boolean empty)
                                      {
                                          super.updateItem(item, empty);
                                            if (!empty && item.isLibre()) {
                                                   setText(item.getHeure());
                                          }
                                            /* Si c'est déjà réservé*/
                                            if (!empty && !item.isLibre()) {
                                                setText(item.getHeure());
                                                setTextFill(Color.RED);
                                          }
                                      
                                       
                                      }
                                  });
                          conn.close();
                          st.close();
                          result.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
    @FXML
    private void handleButtonActionChangeCboAssoc(ActionEvent event)
    {
       lblAssociation.setText(cbAssoc.getSelectionModel().getSelectedItem().getName());
        try {
            cbSalle.getItems().removeAll();
            ObservableList  salles      = FXCollections.observableArrayList();
            Connection conn = connexionBDD();
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery("Select * from salle where refSalle in (Select refSalle from accueillir,pratiquer where accueillir.nomSportAutorise = pratiquer.nomSport and pratiquer.refAsso ='" + cbAssoc.getSelectionModel().getSelectedItem().getName() + "')");
            if (result.next()) {
                do {
                    salles.add(new Salle((String)result.getObject(1),Math.round((float) result.getObject(2)),(String)result.getObject(3)));
                } while (result.next());
               cbSalle.setItems(salles);
            }
            
             cbSalle.setConverter(new StringConverter<Salle>()   {
               @Override
               public String toString(Salle object)
               {
                   return object.getName();
               }
               @Override
               public Salle fromString(String string) 
               {
                   return null;
               }
              });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       /* Changement de l'accessibilité à la combobox salles*/
      cbSalle.setDisable(false);
      cbSalle.setStyle(null);
    }

    @FXML
    private void handleBtnReserverOnClick(ActionEvent event)
    {
        /* Contrôle de saisie, si tout est rempli */
        if (cbSalle.getSelectionModel().getSelectedIndex() >= 0 && cbAssoc.getSelectionModel().getSelectedIndex() >= 0 && (lvHeure.getSelectionModel().getSelectedIndex() >= 2 && lvHeure.getSelectionModel().getSelectedIndex() <= 11) && dDate.getValue() != null) {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateAReserver = formatter.format(dDate.getValue());
            String salle = cbSalle.getSelectionModel().getSelectedItem().getName();
            String assoc = cbAssoc.getSelectionModel().getSelectedItem().getName();
            String heureAReserver = getHeureAvecPlage(lvHeure.getSelectionModel().getSelectedItem().getHeure());
            if (heureAReserver.length() == 1) {
                heureAReserver = "0" + heureAReserver + ":00:00";
            }
            else
            {
                heureAReserver += ":00:00";
            }
            
            
            /* Insertion de la réservation dans la BDD */

            Connection conn;
            try {
                conn = connexionBDD();
                Statement st = conn.createStatement();
                int nbLignes = st.executeUpdate("INSERT INTO reservation VALUES ('" + salle + "','" + dateAReserver + "','" + heureAReserver + "','" + assoc + "')");
                System.out.println(nbLignes);
                
                
                conn.close();
                st.close();
                
                /*Mise à jour des données de la listview*/
                updateListView();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    

  }
