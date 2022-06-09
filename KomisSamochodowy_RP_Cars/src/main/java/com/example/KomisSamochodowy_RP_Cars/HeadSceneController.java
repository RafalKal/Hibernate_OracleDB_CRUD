package com.example.KomisSamochodowy_RP_Cars;

import com.example.KomisSamochodowy_RP_Cars.HibernateUtil.SingletonConnection;
import com.example.KomisSamochodowy_RP_Cars.controller.*;
import com.example.KomisSamochodowy_RP_Cars.model.*;
import com.example.KomisSamochodowy_RP_Cars.service.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HeadSceneController implements Initializable {

    @FXML
    private TabPane myTabPane;

    /*
    |------- TO DO TAB -------|------- TO DO TAB -------|------- TO DO TAB -------|
    */
    @FXML
    private Tab todoTab;
    @FXML
    private RadioButton r1;
    @FXML
    private RadioButton r2;
    @FXML
    private TextField myTextField;
    @FXML
    private ListView<String> todayTasks;
    @FXML
    private ListView<String> tomorrowTasks;
    @FXML
    private ToggleGroup whichDay;
    @FXML
    private Label actualDateLabel;

    @FXML
    void addTo(ActionEvent event) {
        if(r1.isSelected()){
            if(!myTextField.getText().isEmpty()) todayTasks.getItems().add(myTextField.getText());
        }
        else if(r2.isSelected()){
            if(!myTextField.getText().isEmpty()) tomorrowTasks.getItems().add(myTextField.getText());
        }
        myTextField.clear();
    }

    @FXML
    void moveToDone(ActionEvent event){
        int index1 = todayTasks.getSelectionModel().getSelectedIndex();
        int index2 = tomorrowTasks.getSelectionModel().getSelectedIndex();
        if(index1>-1){
            todayTasks.getItems().remove(index1);
        }
        if(index2>-1){
            tomorrowTasks.getItems().remove(index2);
        }
    }
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            actualDateLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /*
    |------- MODELE TAB -------|------- MODELE TAB -------|------- MODELE TAB -------|
    */
    @FXML
    private Tab modeleTab;
    @FXML
    private TableView<Model> modelTableView;
    @FXML
    private TableColumn<Model, Integer> idModelColumn;
    @FXML
    private TableColumn<Model, String> markaColumn;
    @FXML
    private TableColumn<Model, String> modelColumn;
    @FXML
    private TableColumn<Model, String> typNadwoziaColumn;

    @FXML
    Button addModelButton;
    @FXML
    Button removeModelButton;
    @FXML
    Button updateModelButton;

    @FXML
    void addModelToDataBase(ActionEvent event){
        AddModelForm.displayAddModelForm();
        modelObservableList = FXCollections.observableArrayList(modelService.getModelTable());
        modelTableView.setItems(modelObservableList);
        modelTableView.refresh();
    }

    @FXML
    void removeModelFromDataBase(ActionEvent event){
        Model model = modelTableView.getSelectionModel().getSelectedItem();
        modelService.removeModel(model);
        modelObservableList = FXCollections.observableArrayList(modelService.getModelTable());
        modelTableView.setItems(modelObservableList);
        modelTableView.refresh();
    }

    @FXML
    void updateModelInDataBase(ActionEvent event){
        Model model = modelTableView.getSelectionModel().getSelectedItem();
        UpdateModelForm.displayUpdateModelForm(model);
        modelObservableList = FXCollections.observableArrayList(modelService.getModelTable());
        modelTableView.setItems(modelObservableList);
        modelTableView.refresh();

    }

    ModelService modelService = new ModelService();

    public ObservableList<Model> modelObservableList;


    /*
   |------ EGZEMPLARZ TAB ------|------ EGZEMPLARZ TAB ------|------ EGZEMPLARZ TAB ------|
   */
    @FXML
    private Tab egzemplarzeTab;
    @FXML
    private TableView<Egzemplarz> egzemplarzTableView;
    @FXML
    private TableColumn<Egzemplarz, Integer> idEgzemplarzColumn;
    @FXML
    private TableColumn<Egzemplarz, Model> idModeluColumn;
    @FXML
    private TableColumn<Egzemplarz, Integer> rokProdukcjiColumn;
    @FXML
    private TableColumn<Egzemplarz, Integer> przebiegColumn;
    @FXML
    private TableColumn<Egzemplarz, Integer> pojemnoscSilnikaColumn;
    @FXML
    private TableColumn<Egzemplarz, String> rodzajPaliwaColumn;
    @FXML
    private TableColumn<Egzemplarz, String> kolorColumn;
    @FXML
    private TableColumn<Egzemplarz, String> vinColumn;
    @FXML
    private TableColumn<Egzemplarz, Boolean> dostepnoscColumn;
    @FXML
    private TableColumn<Egzemplarz, Integer> cenaColumn;
    @FXML
    Button addEgzemplarzButton;
    @FXML
    Button removeEgzemplarzButton;
    @FXML
    Button updateEgzemplarzButton;

    @FXML
    void addEgzemplarzToDataBase(ActionEvent event){
        AddEgzemplarzForm.displayAddEgzemplarzForm();
        egzemplarzObservableList = FXCollections.observableArrayList(egzemplarzService.getEgzemplarzTable());
        egzemplarzTableView.setItems(egzemplarzObservableList);
        egzemplarzTableView.refresh();
    }

    @FXML
    void removeEgzemplarzFromDataBase(ActionEvent event){
        Egzemplarz egzemplarz = egzemplarzTableView.getSelectionModel().getSelectedItem();
        egzemplarzService.removeEgzemplarz(egzemplarz);
        egzemplarzObservableList = FXCollections.observableArrayList(egzemplarzService.getEgzemplarzTable());
        egzemplarzTableView.setItems(egzemplarzObservableList);
        egzemplarzTableView.refresh();
    }

    @FXML
    void updateEgzemplarzInDataBase(ActionEvent event){
        Egzemplarz egzemplarz = egzemplarzTableView.getSelectionModel().getSelectedItem();
        UpdateEgzemplarzForm.displayUpdateEgzemplarzForm(egzemplarz);
        egzemplarzObservableList = FXCollections.observableArrayList(egzemplarzService.getEgzemplarzTable());
        egzemplarzTableView.setItems(egzemplarzObservableList);
        egzemplarzTableView.refresh();

    }

    EgzemplarzService egzemplarzService = new EgzemplarzService();

    public ObservableList<Egzemplarz> egzemplarzObservableList;


    /*
    |------- KLIENT TAB -------|------- KLIENT TAB -------|------- KLIENT TAB -------|
    */
    @FXML
    private Tab klienciTab;
    @FXML
    private TableView<Klient> klientTableView;
    @FXML
    private TableColumn<Klient, Integer> idKlientColumn;
    @FXML
    private TableColumn<Klient, String> imieColumn;
    @FXML
    private TableColumn<Klient, String> nazwiskoColumn;
    @FXML
    private TableColumn<Klient, LocalDate> dataUrodzeniaColumn;
    @FXML
    private TableColumn<Klient, String> peselColumn;
    @FXML
    private TableColumn<Klient, String> numerDowoduColumn;
    @FXML
    private TableColumn<Klient, String> emailColumn;
    @FXML
    private TableColumn<Klient, String> telefonColumn;
    @FXML
    private TableColumn<Klient, String> miejscowoscColumn;
    @FXML
    private TableColumn<Klient, String> kodPocztowyColumn;
    @FXML
    private TableColumn<Klient, String> ulicaColumn;
    @FXML
    private TableColumn<Klient, Integer> numerDomuColumn;
    @FXML
    private TableColumn<Klient, String> firmaColumn;
    @FXML
    private TableColumn<Klient, String> nipColumn;

    @FXML
    Button addKlientButton;
    @FXML
    Button removeKlientButton;
    @FXML
    Button updateKlientButton;

    @FXML
    void addKlientToDataBase(ActionEvent event){
        AddKlientForm.displayAddKlientForm();
        klientObservableList = FXCollections.observableArrayList(klientService.getKlientTable());
        klientTableView.setItems(klientObservableList);
    }

    @FXML
    void removeKlientFromDataBase(ActionEvent event){
        Klient klient = klientTableView.getSelectionModel().getSelectedItem();
        klientService.removeKlient(klient);
        klientObservableList = FXCollections.observableArrayList(klientService.getKlientTable());
        klientTableView.setItems(klientObservableList);
    }

    @FXML
    void updateKlientInDataBase(ActionEvent event){
        Klient klient = klientTableView.getSelectionModel().getSelectedItem();
        UpdateKlientForm.displayUpdateKlientForm(klient);
        klientObservableList = FXCollections.observableArrayList(klientService.getKlientTable());
        klientTableView.setItems(klientObservableList);
    }

    public KlientService klientService = new KlientService();

    public ObservableList<Klient> klientObservableList;

    /*
    |------- LEASINGI TAB -------|------- LEASINGI TAB -------|------- LEASINGI TAB -------|
    */
    @FXML
    private TableView<Leasing> leasingTableView;
    @FXML
    private Tab leasingiTab;
    @FXML
    private TableColumn<Leasing, Integer> idLeasinguColumn;
    @FXML
    private TableColumn<Leasing, Integer> idKlientaColumn;
    @FXML
    private TableColumn<Leasing, Long> idEgzemplarzaColumn;
    @FXML
    private TableColumn<Leasing, LocalDate> dataPoczatekColumn;
    @FXML
    private TableColumn<Leasing, LocalDate> dataKoncowaColumn;
    @FXML
    private TableColumn<Leasing, Double> oplataMiesiecznaColumn;

    @FXML
    Button addLeasingButton;
    @FXML
    Button removeLeasingButton;
    @FXML
    Button updateLeasingButton;

    @FXML
    void addLeasingToDataBase(ActionEvent event){
        AddLeasingForm.displayAddLeasingForm();
        leasingObservableList = FXCollections.observableArrayList(leasingService.getLeasingTable());
        leasingTableView.setItems(leasingObservableList);
    }

    @FXML
    void removeLeasingFromDataBase(ActionEvent event){
        Leasing leasing = leasingTableView.getSelectionModel().getSelectedItem();
        leasingService.removeLeasing(leasing);
        leasingObservableList = FXCollections.observableArrayList(leasingService.getLeasingTable());
        leasingTableView.setItems(leasingObservableList);
    }

    @FXML
    void updateLeasingInDataBase(ActionEvent event){
        Leasing leasing = leasingTableView.getSelectionModel().getSelectedItem();
        UpdateLeasingForm.displayUpdateLeasingForm(leasing);
        leasingObservableList = FXCollections.observableArrayList(leasingService.getLeasingTable());
        leasingTableView.setItems(leasingObservableList);
    }

    public LeasingService leasingService = new LeasingService();

    public ObservableList<Leasing> leasingObservableList;

    /*
    |------- ZAKUPY TAB -------|------- ZAKUPY TAB -------|------- ZAKUPY TAB -------|
    */
    @FXML
    private Tab transakcjeKupnaTab;
    @FXML
    private TableView<Transakcja_kupna> transakcjeTableView;
    @FXML
    private TableColumn<Transakcja_kupna, Integer> idTransakcjiColumn;
    @FXML
    private TableColumn<Transakcja_kupna, Integer> idKlienciColumn;
    @FXML
    private TableColumn<Transakcja_kupna, Integer> idEgzemplarzyColumn;
    @FXML
    private TableColumn<Transakcja_kupna, LocalDate> dataZakupuColumn;
    @FXML
    private TableColumn<Transakcja_kupna, Integer> dlugoscGwarancjiColumn;
    @FXML
    private TableColumn<Transakcja_kupna, Integer> cenaTransakcjiColumn;

    @FXML
    Button addTransakcja_kupnaButton;
    @FXML
    Button removeTransakcja_kupnaButton;
    @FXML
    Button updateTransakcja_kupnaButton;

    @FXML
    void addTransakcjaToDataBase(ActionEvent event){
        AddTransakcjaForm.displayAddTransakcjaForm();
        transakcja_kupnaObservableList = FXCollections.observableArrayList(transakcja_kupnaService.getTransakcja_kupnaTable());
        transakcjeTableView.setItems(transakcja_kupnaObservableList);
    }

    @FXML
    void removeTransakcjaFromDataBase(ActionEvent event){
        Transakcja_kupna transakcja_kupna = transakcjeTableView.getSelectionModel().getSelectedItem();
        transakcja_kupnaService.removeTransakcja_kupna(transakcja_kupna);
        transakcja_kupnaObservableList = FXCollections.observableArrayList(transakcja_kupnaService.getTransakcja_kupnaTable());
        transakcjeTableView.setItems(transakcja_kupnaObservableList);
    }

    @FXML
    void updateTransakcjaInDataBase(ActionEvent event){
        Transakcja_kupna transakcja_kupna = transakcjeTableView.getSelectionModel().getSelectedItem();
        UpdateTransakcjaForm.displayUpdateTransakcjaForm(transakcja_kupna);
        transakcja_kupnaObservableList = FXCollections.observableArrayList(transakcja_kupnaService.getTransakcja_kupnaTable());
        transakcjeTableView.setItems(transakcja_kupnaObservableList);
    }

    public Transakcja_kupnaService transakcja_kupnaService = new Transakcja_kupnaService();

    public ObservableList<Transakcja_kupna> transakcja_kupnaObservableList;


    private static final SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
    private static final Session session = sessionFactory.openSession();

    private Stage stage;
    private Scene scene;

    public void logout(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();

        //---- MODELE INITIALIZE ----|---- MODELE INITIALIZE ----|---- MODELE INITIALIZE ----|

        idModelColumn.setCellValueFactory(new PropertyValueFactory<Model, Integer>("id"));
        markaColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("marka"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("nazwa_modelu"));
        typNadwoziaColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("typ_nadwozia"));

        modelObservableList = FXCollections.observableArrayList(modelService.getModelTable());
        modelTableView.setItems(modelObservableList);


        //------- EGZEMPLARZ INITIALIZE -------|------- EGZEMPLARZ INITIALIZE -------|

        idEgzemplarzColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Integer>("id"));
        idModeluColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Model>("model_id"));
        rokProdukcjiColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Integer>("rok_produkcji"));
        przebiegColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Integer>("przebieg"));
        rodzajPaliwaColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, String>("rodzaj_paliwa"));
        cenaColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Integer>("cena"));
        pojemnoscSilnikaColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Integer>("pojemnosc_silnika"));
        dostepnoscColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, Boolean>("dostepnosc"));
        vinColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, String>("vin"));
        kolorColumn.setCellValueFactory(new PropertyValueFactory<Egzemplarz, String>("kolor"));

        egzemplarzObservableList = FXCollections.observableArrayList(egzemplarzService.getEgzemplarzTable());
        egzemplarzTableView.setItems(egzemplarzObservableList);


        //--- KLIENCI INITIALIZE ---|--- KLIENCI INITIALIZE ---|--- KLIENCI INITIALIZE ---|

        idKlientColumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("id"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("imie"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("nazwisko"));
        dataUrodzeniaColumn.setCellValueFactory(new PropertyValueFactory<Klient, LocalDate>("data_urodzenia"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("pesel"));
        numerDowoduColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("numer_dowodu"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("email"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("telefon"));
        miejscowoscColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("miejscowosc"));
        kodPocztowyColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("kod_pocztowy"));
        ulicaColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("ulica"));
        numerDomuColumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("numer_domu"));
        firmaColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("email"));
        nipColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("telefon"));


        klientObservableList = FXCollections.observableArrayList(klientService.getKlientTable());
        klientTableView.setItems(klientObservableList);


        //--- LEASINGI INITIALIZE ---|--- LEASINGI INITIALIZE ---|--- LEASINGI INITIALIZE ---|

        idLeasinguColumn.setCellValueFactory(new PropertyValueFactory<Leasing, Integer>("id"));
        idKlientaColumn.setCellValueFactory(new PropertyValueFactory<Leasing, Integer>("klient_id"));
        //idEgzemplarzaColumn.setCellValueFactory(new PropertyValueFactory<Leasing, Long>("egzemplarz_id"));
        dataPoczatekColumn.setCellValueFactory(new PropertyValueFactory<Leasing, LocalDate>("data_początek"));
        dataKoncowaColumn.setCellValueFactory(new PropertyValueFactory<Leasing, LocalDate>("data_koniec"));
        oplataMiesiecznaColumn.setCellValueFactory(new PropertyValueFactory<Leasing, Double>("oplata_miesieczna"));

        leasingObservableList = FXCollections.observableArrayList(leasingService.getLeasingTable());
        leasingTableView.setItems(leasingObservableList);


        //---- TRANSAKCJE KUPNA INITIALIZE ----|---- TRANSAKCJE KUPNA INITIALIZE ----|

        idTransakcjiColumn.setCellValueFactory(new PropertyValueFactory<Transakcja_kupna, Integer>("id"));
        idKlienciColumn.setCellValueFactory(new PropertyValueFactory<Transakcja_kupna, Integer>("klient_id"));
        idEgzemplarzyColumn.setCellValueFactory(new PropertyValueFactory<Transakcja_kupna, Integer>("egzemplarz_id"));
        dataZakupuColumn.setCellValueFactory(new PropertyValueFactory<Transakcja_kupna, LocalDate>("data_zakupu"));
        dlugoscGwarancjiColumn.setCellValueFactory(new PropertyValueFactory<Transakcja_kupna, Integer>("dlugosc_gwarancji"));
        cenaTransakcjiColumn.setCellValueFactory(new PropertyValueFactory<Transakcja_kupna, Integer>("cena"));

        leasingObservableList = FXCollections.observableArrayList(leasingService.getLeasingTable());
        leasingTableView.setItems(leasingObservableList);
    }

}