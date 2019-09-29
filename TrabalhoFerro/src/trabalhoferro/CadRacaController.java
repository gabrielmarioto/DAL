/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoferro;

import DAL.DALracas;
import banco.Banco;
import entidades.Racas;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableRow;
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
public class CadRacaController implements Initializable
{

    @FXML
    private Button btn_Novo;
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
    private TableView<Racas> tb_Tabela;
    @FXML
    private AnchorPane pnl_Cadastro;
    @FXML
    private TableColumn<Racas, Integer> col_codigo;
    @FXML
    private TableColumn<Racas, String> col_nome;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    List<Racas> arquivos = new ArrayList();
    
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
        DALracas dal = new DALracas();
        if(JOptionPane.showConfirmDialog(null, "Confirma a alteração?") == JOptionPane.OK_OPTION)
        {
            Racas r = new Racas(Integer.parseInt(tb_codigo.getText()),tb_nome.getText());
            dal.alterar(r);  
        }    
        preencherTabela(arquivos);
        abrir();        
    }
    @FXML
    private void evt_Apagar(ActionEvent event)
    {
        DALracas dal = new DALracas();
        if(JOptionPane.showConfirmDialog(null, "Confirma a exclusão?") == JOptionPane.OK_OPTION)
        {
            Racas r = new Racas (Integer.parseInt(tb_codigo.getText()),tb_nome.getText());
            dal.apagar(r);                        
        }   
        preencherTabela(arquivos);
        abrir();
    }
    private void preencherTabela(List arquivos)
    {
        tb_Tabela.setItems(FXCollections.observableArrayList(arquivos));        
    }

    private void abrir()
    {
        arquivos.clear();
        DALracas dal = new DALracas();
        Racas r = null;
        int cont = Banco.getCon().getMaxPK("rac_cod", "raca");
        try
        {
            for (int i = 1; i <= cont; i++)
            {
                r = dal.getUm(i);
                arquivos.add(r);
            }
        } catch (Exception e)
        {
        }
        preencherTabela(arquivos);
    }

    @FXML
    private void evt_Confirmar(ActionEvent event)
    {

        Racas r = null;
        DALracas dal = new DALracas();        
        try
        {
            if (tb_codigo.getText().length() != 0)
            {
                 if (tb_nome.getLength() != 0)
                {
                    r = new Racas(Integer.parseInt(tb_codigo.getText()), tb_nome.getText());
                    dal.salvar(r);
                    arquivos.add(r);
                    inicializar();                    
                } else
                {
                    JOptionPane.showMessageDialog(null, "Digite algum nome");
                }
            } 
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
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
        List <Racas> tela = new ArrayList();
        DALracas r = new DALracas();
        if(tb_pesquisa.getText().length() != 0)
        {
            tela.add(r.getUm(Integer.parseInt(tb_pesquisa.getText())));
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

    private void evt_Atualizar(ActionEvent event)
    {
        tb_Tabela.refresh();
        abrir();
    }


}
