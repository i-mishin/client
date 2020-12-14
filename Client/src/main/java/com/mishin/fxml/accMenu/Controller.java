package com.mishin.fxml.accMenu;

import com.mishin.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/authorization.fxml", "Авторизация");
    }

    public void workWithEmployeeButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/workEmployee.fxml", "Работа со студентами");
    }
    public void addPostButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/addPosition.fxml", "Добавить специальность");
    }
    public void statementButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event,  "/view/statementWage.fxml", "Отчёт");
    }
    public void deducationButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/deductions.fxml", "Работа с издержакми");
    }
    public void wageButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/wageForm.fxml", "Рассчитать стипендию");
    }
    public void recalculationWageButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/recalculationWage.fxml", "Пересчитать стипендию");
    }
    public void graficsButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/grafic.fxml", "Графики");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
