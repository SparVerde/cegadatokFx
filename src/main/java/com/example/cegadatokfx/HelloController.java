package com.example.cegadatokfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
// db adattag kreálása:
    CegadatDb db;
//@FXML létrhozása .fxml fileból: jobb klikk: update Controller, előtte fxml mentése, hozzáadjuk <> és <mit tartalmaz> <CegadatRekord, String>
    @FXML
    private Label welcomeText;
    @FXML
    private TableView <CegadatRekord>tablazatId;
    @FXML
    private TableColumn<CegadatRekord, Date> szuldatum;
    @FXML
    private TableColumn <CegadatRekord, String> cim;
    @FXML
    private TableColumn <CegadatRekord, String> telefon;
    @FXML
    private TableColumn <CegadatRekord, Integer>nem;
    @FXML
    private TableColumn <CegadatRekord, String> varos;
    @FXML
    private TableColumn <CegadatRekord, Integer> az;
    @FXML
    private TableColumn <CegadatRekord, String>nev;
    @FXML
    private TableColumn <CegadatRekord, Integer>kereset;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
// initialize metódus meghívása: hogy mi törtrénjen az adattag névvel, a kapott osztályból a getId metódust minden oszlopra
    //legyen getId metódus a CegadatRekord osztályban
    public void initialize(){
        az.setCellValueFactory(new PropertyValueFactory<>("az"));
        nev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        nem.setCellValueFactory(new PropertyValueFactory<>("nem"));
        szuldatum.setCellValueFactory(new PropertyValueFactory<>("szuldatum"));
        varos.setCellValueFactory(new PropertyValueFactory<>("varos"));
        cim.setCellValueFactory(new PropertyValueFactory<>("cim"));
        telefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        kereset.setCellValueFactory(new PropertyValueFactory<>("kereset"));

        //beolvasás adatbázisba: új példány adatbázis létrehozásra + adattagot is kreálok: CegadatDb db;
        //adatbázisból lista létrehozása, metódusa: tablazatFeltolt() + try, catch

        try {
            db = new CegadatDb();
            tablazatFeltolt();

        }catch (Exception e){System.out.println(e);}
    }
//tablazatFeltolt() metódus megírása: ArrayList létrehozása és feltöltése, előtte azonban kitöröljük a korábbi tartalmat
    public void tablazatFeltolt(){

        try {
            ArrayList<CegadatRekord> lista = db.ListaFeltolt();
            tablazatId.getItems().clear();//táblázat azonosítóval tablazatId azonosított táblázat törlése
            for (CegadatRekord elem:lista
                 ) {
                tablazatId.getItems().add(elem); //táblázat azonosítóval tablazatId azonosított táblázat feltöltése
            }

        }catch (Exception e){System.out.println(e);}
    }
    //mysql-connector.jar legyen bemásolva + jobb click add as library

    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = tablazatId.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            System.out.println("A törléshez előbb válasszon ki egy elemet a táblázatból");
            uzenetKiir("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        CegadatRekord torlendo = tablazatId.getSelectionModel().getSelectedItem();
        if (!megerositesKiir("Biztos hogy törölni szeretné az alábbi rekordot: "+torlendo.getNev())){
            return;
        }
        try {
            db.rekordTorlese(torlendo.getAz());
            System.out.println("Sikeres törlés");
            uzenetKiir("Sikeres törlés");
            tablazatFeltolt();
        } catch (SQLException e) {
            hibaKiir(e, "Hiba a törlés közben");
        }
    }

    private void hibaKiir(Exception e, String hibauzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(hibauzenet);
        //alert.show();
        alert.setContentText(e.getClass().toString());
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> alert.show());
            }
        }, 500);
    }

    private void uzenetKiir(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }

    private boolean megerositesKiir(String uzenet){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztos?");
        alert.setHeaderText(uzenet);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}