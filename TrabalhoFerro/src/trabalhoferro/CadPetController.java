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
import java.sql.SQLException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;

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
     *
     * @param url
     * @param rb
     */
    private List<Racas> racas = new ArrayList<>();
    private List<Especie> especies = new ArrayList<>();
    private List<Pet> arquivos = new ArrayList();
    @FXML
    private DatePicker dp_datanasc;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        if (!Banco.conectar())
        {
            System.exit(0);
        }
        JOptionPane.showMessageDialog(null, "Cadastro de PET com defeitos, script do banco foi feito com erro!");
        dp_datanasc.setValue(LocalDate.now());
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

    private void carregarItems()
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

    private void preencher(String filtro)
    {
        DALpets dal = new DALpets();
        DALespecie especie = new DALespecie();
        DALracas racas = new DALracas();
        arquivos = dal.get(filtro);
        ObservableList<Pet> listaPets;
        listaPets = FXCollections.observableArrayList(arquivos);
        if (!listaPets.isEmpty())
        {
            listaPets.clear();
        }
        for (Pet p : arquivos)
        {
            Pet pet = new Pet(p.getCod(), p.getNome(), p.getPeso(), p.getCor(), p.getDataNasc(), p.getSexo(), especie.getUm(p.getCod()), racas.getUm(p.getCod()));
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
        btn_Alterar.setVisible(true);
        DALpets dal = new DALpets();
        Especie esp = null;
        Racas rac = null;
        try
        {
            if (JOptionPane.showConfirmDialog(null, "Confirma a alteração?") == JOptionPane.OK_OPTION)
            {
                Pet p = new Pet(Integer.parseInt(tb_codigo.getText()), tb_nome.getText(), Double.parseDouble(tb_peso.getText()), tb_cor.getText(), dp_datanasc.getValue(),
                        tb_sexo.getText().charAt(0), esp = new Especie(cbb_especie.getValue().getCod(), cbb_especie.getValue().getNome()), rac = new Racas(cbb_raca.getValue().getCod(), cbb_raca.getValue().getNome()));
                dal.alterar(p);
            }
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        carregarItems();
        preencher("");
        inicializar();
    }

    @FXML
    private void evt_Apagar(ActionEvent event)
    {
        Especie esp = null;
        Racas rac = null;
        DALpets dal = new DALpets();
        try
        {
            if (JOptionPane.showConfirmDialog(null, "Confirma a exclusão?") == JOptionPane.OK_OPTION)
            {
                Pet p = new Pet(Integer.parseInt(tb_codigo.getText()), tb_nome.getText(), Double.parseDouble(tb_peso.getText()), tb_cor.getText(), dp_datanasc.getValue(),
                        tb_sexo.getText().charAt(0), esp = new Especie(cbb_especie.getValue().getCod(), cbb_especie.getValue().getNome()), rac = new Racas(cbb_raca.getValue().getCod(), cbb_raca.getValue().getNome()));
                dal.apagar(p);
            }
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        carregarItems();
        preencher("");
        inicializar();
    }

    @FXML
    private void evt_Confirmar(ActionEvent event) // FAZER
    {
        Pet p = null;
        Especie esp = null;
        Racas rac = null;
        DALpets dal = new DALpets();
        String hoje = LocalDate.now().toString();
        try
        {
            if (tb_codigo.getText().length() != 0)
            {
                if (tb_nome.getText().length() != 0)
                {
                    if (tb_cor.getText().length() != 0)
                    {
                        if (tb_peso.getText().length() != 0)
                        {
                            if (tb_sexo.getText().length() != 0)
                            {
                                if (dp_datanasc.getValue().toString().compareTo(hoje) <= 0)
                                {
                                    p = new Pet(Integer.parseInt(tb_codigo.getText()), tb_nome.getText(), Double.parseDouble(tb_peso.getText()), tb_cor.getText(), dp_datanasc.getValue(),
                                            tb_sexo.getText().charAt(0), esp = new Especie(cbb_especie.getValue().getCod(), cbb_especie.getValue().getNome()), rac = new Racas(cbb_raca.getValue().getCod(), cbb_raca.getValue().getNome()));
                                    dal.salvar(p);
                                    arquivos.add(p);
                                    inicializar();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex)
        {
        }
        preencherTabela(arquivos);
    }

    @FXML
    private void evt_Cancelar(ActionEvent event)
    {
        preencher("");
        carregarItems();
        inicializar();
    }

    @FXML
    private void evt_Pesquisar(ActionEvent event)
    {
        List<Pet> tela = new ArrayList();
        DALpets dal = new DALpets();
        if (tb_pesquisar.getText().length() != 0)
        {
            tela.add(dal.getUm(Integer.parseInt(tb_pesquisar.getText())));
            tb_Tabela.setItems(FXCollections.observableArrayList(tela));
        } else
        {
            carregarItems();
            preencher("");
        }
    }

    @FXML
    private void evt_click(MouseEvent event)
    {
        pnl_Cadastro.setVisible(true);
        tb_codigo.setText("" + (tb_Tabela.getSelectionModel().getSelectedItem().getCod()));
        tb_nome.setText(tb_Tabela.getSelectionModel().getSelectedItem().getNome());
        tb_cor.setText(tb_Tabela.getSelectionModel().getSelectedItem().getCor());
        tb_peso.setText("" + tb_Tabela.getSelectionModel().getSelectedItem().getPeso());
        tb_sexo.setText("" + tb_Tabela.getSelectionModel().getSelectedItem().getSexo());
        dp_datanasc.setValue(tb_Tabela.getSelectionModel().getSelectedItem().getDataNasc());
        cbb_especie.setValue(tb_Tabela.getSelectionModel().getSelectedItem().getEspecie());
        cbb_raca.setValue(tb_Tabela.getSelectionModel().getSelectedItem().getRaca());
    }

}
