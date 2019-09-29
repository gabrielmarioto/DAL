/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoferro;

import DAL.DALespecie;
import DAL.DALpets;
import DAL.DALracas;
import banco.Banco;
import entidades.Especie;
import entidades.Pet;
import entidades.Racas;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author GABRIEL
 */
public class CadPetController implements Initializable
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
    private AnchorPane pnl_Cadastro;
    @FXML
    private Color x3;
    @FXML
    private TextField tb_codigo;
    @FXML
    private TextField tb_nome;
    @FXML
    private Color x4;
    @FXML
    private TextField tb_peso;
    @FXML
    private TextField tb_cor;
    @FXML
    private TextField tb_sexo;
    @FXML
    private ComboBox<Especie> cbb_especie;
    @FXML
    private ComboBox<Racas> cbb_raca;
    @FXML
    private TextField tb_pesquisar;
    @FXML
    private Button btn_Pesquisar;
    @FXML
    private TableView<Pet> tb_Tabela;
    @FXML
    private TableColumn<Pet, Integer> col_codigo;
    @FXML
    private TableColumn<Pet, String> col_nome;
    @FXML
    private TableColumn<Pet, Double> col_peso;
    @FXML
    private TableColumn<Pet, String> col_cor;
    @FXML
    private TableColumn<Pet, Character> col_sexo;
    @FXML
    private TableColumn<Pet, LocalDate> col_dtnasc;
    @FXML
    private TableColumn<Pet, Racas> col_raca;
    @FXML
    private TableColumn<Pet, Especie> col_especie;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    private List <Racas> racas = new ArrayList<>();
    private List <Especie> especies = new ArrayList<>();
    private List <Pet> arquivos = new ArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        
        
        if(!Banco.conectar())
            System.exit(0);
        preencher("");
        inicializar();
        carregarItems();
    }    
     public void inicializar()
    {
        btn_Confirmar.setVisible(false);
        pnl_Cadastro.setVisible(false);
        tb_codigo.clear();
        tb_nome.clear();
        tb_cor.clear();
        tb_peso.clear();
        tb_pesquisar.clear();
        tb_sexo.clear();
        cbb_especie.valueProperty().set(null);
        cbb_raca.valueProperty().set(null);
    }
     private void preencherTabela(List arquivos)
    {
        tb_Tabela.setItems(FXCollections.observableArrayList(arquivos));        
    }
    private void carregarItems() // SEI LÁ 2
    {        
        DALracas DAL = new DALracas();
        DALespecie DALesp = new DALespecie();

        ObservableList<Racas> listaRacas;
        ObservableList<Especie> listaEspecies;
        
        racas = DAL.get("");
        especies = DALesp.get("");      
        listaRacas = FXCollections.observableArrayList(racas);   
        listaEspecies = FXCollections.observableArrayList(especies);
        cbb_raca.setItems(listaRacas);        
        cbb_especie.setItems(listaEspecies);
    }
    private void preencher(String filtro) // SEI LA QUE PORRA É ESSA
    {
        DALpets dal = new DALpets();
        DALespecie especie = new DALespecie();
        DALracas racas = new DALracas();
        arquivos = dal.get(filtro);
        ObservableList<Pet> listaPets;
        listaPets = FXCollections.observableArrayList();
        if(!listaPets.isEmpty())
            listaPets.clear();
        for(Pet p : arquivos)
        {
            Pet pet = new Pet(p.getCod(), p.getNome(), p.getPeso(), p.getCor(),p.getDataNasc(), p.getSexo(),especie.getUm(p.getCod()), racas.getUm(p.getCod()));
            listaPets.add(p);
        }
        col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("cod")); 
        col_peso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        col_cor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        col_sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        col_dtnasc.setCellValueFactory(new PropertyValueFactory<>("datanasc"));
        col_raca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        col_especie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        
        tb_Tabela.setItems(listaPets);
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
    }

    @FXML
    private void evt_Apagar(ActionEvent event)
    {
    }

    @FXML
    private void evt_Confirmar(ActionEvent event) // FAZER
    {
        Pet p = null;
        DALpets dal = new DALpets();
        try
        {
            if(tb_codigo.getText().length() != 0 )
            {
                if(tb_nome.getText().length() != 0 )
                {
                    if(tb_cor.getText().length() != 0)
                    {
                        if(tb_peso.getText().length() != 0)
                        {
                            if(tb_sexo.getText().length() != 0)
                            {
                              //  if(cbb_especie.get)
                            }
                        }
                    }
                }
            }
        }
        catch(Exception ex){}
    }

    @FXML
    private void evt_Cancelar(ActionEvent event)
    {
        preencher("");
        carregarItems();
        
    }

    @FXML
    private void evt_Pesquisar(ActionEvent event)
    {
    }

    @FXML
    private void evt_click(MouseEvent event) // ERRO
    {
       DALespecie esp = new DALespecie();
       ObservableList <Especie> listaEspecie = null;
       listaEspecie = FXCollections.observableArrayList();
       listaEspecie.add(esp.getUm(tb_Tabela.getSelectionModel().getSelectedItem().getCod()));
        pnl_Cadastro.setVisible(true);
        tb_codigo.setText(""+(tb_Tabela.getSelectionModel().getSelectedItem().getCod()));
        tb_nome.setText(tb_Tabela.getSelectionModel().getSelectedItem().getNome());
        tb_cor.setText(tb_Tabela.getSelectionModel().getSelectedItem().getCor());
        tb_peso.setText(""+tb_Tabela.getSelectionModel().getSelectedItem().getPeso());
        tb_sexo.setText(""+tb_Tabela.getSelectionModel().getSelectedItem().getSexo());
        System.out.println(tb_Tabela.getSelectionModel().getSelectedItem().getRaca().getNome());
        cbb_especie.setItems(listaEspecie);
        cbb_raca.setId(""+tb_Tabela.getSelectionModel().getSelectedItem().getRaca().getNome());
    }
    
}
