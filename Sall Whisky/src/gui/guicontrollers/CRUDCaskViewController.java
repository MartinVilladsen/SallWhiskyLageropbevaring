package gui.guicontrollers;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Cask;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CRUDCaskViewController implements Initializable {

    @FXML
    private Button btnCRUDCask;
    @FXML
    private Button btnCRUDRawMaterial;
    @FXML
    private Button btnCRUDStorage;
    @FXML
    private Button btnCRUDSupplier;
    @FXML
    private Button btnCreateCask;
    @FXML
    private Button btnCreateFillOnCask;
    @FXML
    private Button btnDeleteCask;
    @FXML
    private Button btnEditCask;
    @FXML
    private Button btnStartside;
//    @FXML
//    private ListView<Cask> lvwCasks;
    @FXML
    private TableColumn<Cask, String> columnCountryOfOrigin;
    @FXML
    private TableColumn<Cask, Integer> columnID;
    @FXML
    private TableColumn<Cask, Integer> columnPosition;
    @FXML
    private TableColumn<Cask, String> columnPreviousContent;
    @FXML
    private TableColumn<Cask, Integer> columnRack;
    @FXML
    private TableColumn<Cask, Integer> columnShelf;
    @FXML
    private TableColumn<Cask, Double> columnSizeInLiters;
    @FXML
    private TableColumn<Cask, Double> columnSpaceAvailableInLiters;
    @FXML
    private TableColumn<Cask, Integer> columnWarehouse;
    @FXML
    private TableView<Cask> tvwCasks;

    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnID.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("caskId"));
        columnCountryOfOrigin.setCellValueFactory(new PropertyValueFactory<Cask, String>("countryOfOrigin"));
        columnSizeInLiters.setCellValueFactory(new PropertyValueFactory<Cask, Double>("sizeInLiters"));
        columnSpaceAvailableInLiters.setCellValueFactory(new PropertyValueFactory<Cask, Double>("LitersAvailable"));
        columnPreviousContent.setCellValueFactory(new PropertyValueFactory<Cask, String>("previousContent"));
        columnWarehouse.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("warehouseId"));
        columnRack.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("rackId"));
        columnShelf.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("shelfId"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("positionId"));

        updateLvwCasks();
        updateTvwCasks();

    }

    @FXML
    void btnCreateCaskOnAction(ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CreateCaskView.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Opret fad");
        stage.setScene(new Scene(root1));
        stage.showAndWait();
        updateLvwCasks();
        updateTvwCasks();
    }

    /**
     * Opens the distillary and fill view
     */
    @FXML
    void btnCreateFillOnCask(ActionEvent event) throws IOException {
        SwitchSceneController.btnDestillateAndFillOnCaskOnAction(stage, scene, event);
        updateLvwCasks();
    }


    /**
     * Deletes the selected Cask
     * If the cask has any fillOnCasks or previousFillOnCasks connected
     * prompt the user with that info and abort deletion
     */
    @FXML
    void btnDeleteCaskOnAction(ActionEvent event) {
        Cask cask = tvwCasks.getSelectionModel().getSelectedItem();
        if (tvwCasks.getSelectionModel().isEmpty()) {
            tvwCasks.setStyle("-fx-border-color: red;");
        }
        else if (!cask.getFillOnCasks().isEmpty() || !cask.getPreviousFillOnCask().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setHeaderText("Fad kan ikke slettes");
            alert.setContentText("Dette fad kan ikke slettes, da der er eller har været opfyldninger" +
                    "på faddet.");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bekræft sletning");
            alert.setHeaderText("Er du sikker på, at du vil slette dette fad");
            alert.getButtonTypes().setAll(new ButtonType("Ja"), new ButtonType("Fortryd"));
            ButtonType choice = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (choice.getText() == "Ja") {
                MainController.removeCask(cask);
            }
            updateLvwCasks();

        }
    }



    @FXML
    void btnEditCaskOnAction(ActionEvent event) {
        //TODO Til version to når der skal kunne omhældes
    }

    /**
     * Updates the listView with all the casks
     */
    private void updateLvwCasks() {
        tvwCasks.getItems().setAll(MainController.getCasks());
    }
    private void updateTvwCasks() {
        tvwCasks.getItems().setAll(MainController.getCasks());
    }

    @FXML
    void btnCrudStorageOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudStorage(stage, scene, event);
    }

    @FXML
    void btnDestillateAndFillOnCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnDestillateAndFillOnCaskOnAction(stage, scene, event);
    }

    @FXML
    void btnRawMaterialOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnRawMaterial(stage, scene, event);
    }

    @FXML
    void btnStartSideOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnStartView(stage, scene, event);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCRUDSupplier(stage, scene, event);
    }

}
