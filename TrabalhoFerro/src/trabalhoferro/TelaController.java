/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoferro;

import com.sun.javaws.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author GABRIEL
 */
public class TelaController implements Initializable
{
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void evt_Raca(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CadRaca.fxml"));        
        Scene scene = new Scene(root);
        stage.setTitle("Cadastro de Racas");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void evt_Especie(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CadEspecie.fxml"));        
        Scene scene = new Scene(root);
        stage.setTitle("Cadastro de Especie");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void evt_Pet(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CadPet.fxml"));        
        Scene scene = new Scene(root);
        stage.setTitle("Cadastro de Pet");
        stage.setScene(scene);
        stage.show();
    }
    
}
