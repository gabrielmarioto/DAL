/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoferro;

import DAL.DALespecie;
import DAL.DALracas;
import banco.Banco;
import entidades.Especie;
import entidades.Racas;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author GABRIEL
 */
public class CadEspecieController implements Initializable
{

    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private Button btn_Alterar;
    @FXML
    private Button btn_Apagar;
    @FXML
    private Button btn_Confirmar;
    @FXML
    private Button btn_Cancelar;
    @FXML
    private TextField tb_codigo;
    @FXML
    private TextField tb_nome;
    @FXML
    private TextField tb_pesquisa;
    @FXML
    private Button btn_Pesquisar;
    @FXML
    private TableView<Especie> tb_Tabela;
    @FXML
    private TableColumn<Especie, Integer> col_codigo;
    @FXML
    private TableColumn<Especie, String> col_nome;

    /**
     * Initializes the controller class.
     */
    List<Especie> arquivos = new ArrayList();
    @FXML
    private AnchorPane pnl_Cadastro;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("cod"));               
        if(!Banco.conectar())
            System.exit(0);
        abrir();
        inicializar();
    }    
     public void inicializar()
    {
        btn_Confirmar.setVisible(false);
        pnl_Cadastro.setVisible(false);
        tb_codigo.clear();
        tb_nome.clear();
    }
     private void preencherTabela(List arquivos)
    {
        tb_Tabela.setItems(FXCollections.observableArrayList(arquivos));        
    }

    private void abrir()
    {
        arquivos.clear();
        DALespecie dal = new DALespecie();
        Especie e = null;
        int cont = Banco.getCon().getMaxPK("esp_cod", "especie");
        try
        {
            for (int i = 1; i <= cont; i++)
            {
                e = dal.getUm(i);
                arquivos.add(e);
            }
        } catch (Exception ex){}
        preencherTabela(arquivos);
    }
    @FXML
    private void evt_Novo(ActionEvent event)
    {
        pnl_Cadastro.setVisible(true);
        btn_Confirmar.setVisible(true);
    }

    @FXML
    private void evt_Alterar(ActionEvent event)
    {
        btn_Alterar.setVisible(true);
        DALespecie dal = new DALespecie();
        if(JOptionPane.showConfirmDialog(null, "Confirma a alteração?") == JOptionPane.OK_OPTION)
        {
            Especie e = new Especie(Integer.parseInt(tb_codigo.getText()),tb_nome.getText());
            dal.alterar(e);  
        }    
        preencherTabela(arquivos);
        abrir();        
    }

    @FXML
    private void evt_Apagar(ActionEvent event)
    {
        DALespecie dal = new DALespecie();
        if(JOptionPane.showConfirmDialog(null, "Confirma a exclusão?") == JOptionPane.OK_OPTION)
        {
            Especie e = new Especie (Integer.parseInt(tb_codigo.getText()),tb_nome.getText());
            dal.apagar(e);                        
        }   
        preencherTabela(arquivos);
        abrir();
    }

    @FXML
    private void evt_Confirmar(ActionEvent event)
    {
        Especie e = null;
        DALespecie dal = new DALespecie();        
        try
        {
            if (tb_codigo.getText().length() != 0)
            {
                 if (tb_nome.getLength() != 0)
                {
                    e = new Especie(Integer.parseInt(tb_codigo.getText()), tb_nome.getText());
                    dal.salvar(e);
                    arquivos.add(e);
                    inicializar();                    
                } else
                {
                    JOptionPane.showMessageDialog(null, "Digite algum nome");
                }
            } 
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        preencherTabela(arquivos);
    }

    @FXML
    private void evt_Cancelar(ActionEvent event)
    {
        inicializar();
    }

    @FXML
    private void evt_Pesquisar(ActionEvent event)
    {
        List <Especie> tela = new ArrayList();
        DALespecie e = new DALespecie();
        if(tb_pesquisa.getText().length() != 0)
        {
            tela.add(e.getUm(Integer.parseInt(tb_pesquisa.getText())));
            tb_Tabela.setItems(FXCollections.observableArrayList(tela));  
        }
        else
            abrir();
    }

    @FXML
    private void evt_click(MouseEvent event)
    {
        pnl_Cadastro.setVisible(true);
        tb_codigo.setText(""+(tb_Tabela.getSelectionModel().getSelectedItem().getCod()));
        tb_nome.setText(tb_Tabela.getSelectionModel().getSelectedItem().getNome());
    }
    
}
