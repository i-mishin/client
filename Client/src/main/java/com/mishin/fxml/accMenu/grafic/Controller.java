package com.mishin.fxml.accMenu.grafic;

import com.mishin.SceneChanger;
import com.mishin.classes.Employee;
import com.mishin.classes.Payroll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private Label title;
    @FXML
    private PieChart pieChart;

    Model model = new Model();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }


    public void payrollButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/graficPayroll.fxml", "График средней стипендии за год");
    }
    public void amountEmployeeButtonPushed(){
        title.setText("Количество студентов");
        pieChart.setVisible(true);
        pieChart.setData(model.drawGraf()); //MVC
    }
    public void workedDaysButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/graficDays.fxml", "График учебных дней");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pieChart.setVisible(false);
        title.setText("Выберети, какой график хотите увидеть");
    }
}
