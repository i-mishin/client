package com.mishin.fxml.accMenu.Wage;

import com.mishin.SceneChanger;
import com.mishin.ServerConnection;
import com.mishin.classes.Charges;
import com.mishin.classes.Deducation;
import com.mishin.classes.Employee;
import com.mishin.classes.Payroll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Controller implements Initializable{

    @FXML
    private ChoiceBox<Integer> month;
    @FXML
    private ChoiceBox<Integer> year;
    @FXML
    private TextField surname;
    @FXML
    private TextField name;
    @FXML
    private TextField patronymic;
    @FXML
    private ComboBox<String> bank_account;
    @FXML
    private Label stateBank_account;
    @FXML
    private TextField total;
    @FXML
    private ComboBox<String> pName;
    @FXML
    private TextField pSum;
    @FXML
    private ComboBox<String> dName;
    @FXML
    private TextField dSum;
    @FXML
    private Button pDelete;
    @FXML
    private Button pAdd;
    @FXML
    private Button dDelete;
    @FXML
    private Button dAdd;
    @FXML
    private Button calculation;
    @FXML
    private TextField workingDays;
    @FXML
    private TextField wasWorked;
    @FXML
    private TableView payroll;
    @FXML
    private TableColumn<Charges,String> cColumnName;
    @FXML
    private TableColumn<Charges, Double> cColumnSum;
    @FXML
    private TableView deducation;
    @FXML
    private TableColumn<Deducation,String> dColumnName;
    @FXML
    private TableColumn<Deducation, Double> dColumnSum;


    List<Employee> listEmployee = new ArrayList();
    Map<String,Double> deducat=new HashMap<>();
    List<Charges> listCharges = new ArrayList<>();
    List<Deducation> listDeducations = new ArrayList<>();
    Employee currentEmployee = new Employee();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Расчёт стипендии");
    }

    public void addDAddButtonPushed(ActionEvent event) throws IOException {
        listDeducations.add(new Deducation(dName.getValue(), Double.valueOf(dSum.getText())));
        deducation.getItems().add(new Deducation(dName.getValue(), Double.valueOf(dSum.getText())) );
    }

    public void addCAddButtonPushed(ActionEvent event) throws IOException {
        listCharges.add(new Charges(pName.getValue(), Double.valueOf(pSum.getText())));
        payroll.getItems().add(new Charges(pName.getValue(), Double.valueOf(pSum.getText())) );
    }

    public void DDeleteButtonPushed(ActionEvent event) throws IOException {
        ObservableList<Deducation> dedSelected;
        dedSelected=deducation.getSelectionModel().getSelectedItems();
        listDeducations.remove(new Deducation(dedSelected.get(0).getName(), dedSelected.get(0).getSum()));
        deducation.getItems().remove(dedSelected.get(0));
    }

    public void CDeleteButtonPushed(ActionEvent event) throws IOException {
        ObservableList<Charges> charSelected;
        charSelected=payroll.getSelectionModel().getSelectedItems();
        listCharges.remove(new Deducation(charSelected.get(0).getName(), charSelected.get(0).getSum()));
        payroll.getItems().remove(charSelected.get(0));
    }

    public void Calcuation(ActionEvent event) throws IOException {
        Payroll payroll = new Payroll(month.getValue(),
                year.getValue(),
                currentEmployee.getId(),
                currentEmployee.getPost(),
                0.0,
                Integer.valueOf(wasWorked.getText()),
                Integer.valueOf(workingDays.getText()),
                "addPayroll");
        for (Charges ch: listCharges) {
            payroll.addCharges(ch.getName(), ch.getSum().doubleValue());
        }
        for (Deducation d: listDeducations)
            payroll.addDeducations(d.getName(),d.getSum());

        List<Object> list = new ArrayList();
        Object obj = (Object) payroll;
        ObservableList<Payroll> payrolls = FXCollections.observableArrayList();
        try {
            list = (ServerConnection.send(obj));
            if(!((Payroll)list.get(0)).getType().equals("false")) {
                for (Object object : list) {
                    Payroll payroll1 = (Payroll) object;
                    payrolls.add(payroll1);
                }
                total.setText(payrolls.get(0).getSum().toString());
            }
            else
                JOptionPane.showMessageDialog(null, "Стипендия в этот месяц уже посчитана!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBank_accountPushed(ActionEvent event){
        for (Employee employee:listEmployee) {
            if (employee.getBankAccount().equals(bank_account.getValue())){
                surname.setText(employee.getSurname());
                name.setText(employee.getName());
                patronymic.setText(employee.getPatronymic());
                currentEmployee = employee;
                break;
            }
        }
    }

    public void setNameDedPushed(ActionEvent event){
        if(bank_account.getValue()!=null && wasWorked.getText()!=null && workingDays.getText()!=null) {
            for (Employee employee : listEmployee) {
                if (employee.getBankAccount().equals(bank_account.getValue())) {
                    double ded = 0;
                    ded = employee.getPost().getSalary() / Double.valueOf(workingDays.getText()) * Double.valueOf(wasWorked.getText()) * employee.getRate()*deducat.get(dName.getValue())/100;
                    //ded = (employee.getPost().getSalary()/ Double.valueOf(workingDays.getText()))*Double.valueOf(wasWorked.getText()) * deducat.get(dName.getValue())* employee.getRate();
                    dSum.setText(new DecimalFormat("#0.00").format(ded).replace(",","."));
                    break;
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Введены не все данные!");
        }
    }

    public void setNameChargesPushed(ActionEvent event){
        if(pName.getValue().equals("по стипендияу")) {
            if (bank_account.getValue() != null && wasWorked.getText() != null && workingDays.getText() != null) {
                for (Employee employee : listEmployee) {
                    if (employee.getBankAccount().equals(bank_account.getValue())) {
                        double charge = 0;
                        charge = employee.getPost().getSalary() / Double.valueOf(workingDays.getText()) * Double.valueOf(wasWorked.getText()) * employee.getRate();
                        pSum.setText(new DecimalFormat("#0.00").format(charge).replace(",","."));
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Введены не все данные!");
            }
        }
    }

    public void setWasWorkedPushed(ActionEvent event){
        if(!workingDays.getText().equals("")) {
            if(Integer.valueOf(wasWorked.getText())<=31 && Integer.valueOf(wasWorked.getText())>0) {
                if (Integer.valueOf(workingDays.getText()) < Integer.valueOf(wasWorked.getText())) {
                    JOptionPane.showMessageDialog(null, "Количество отработанных дней не может быть больше количества рабочих дней!");
                    wasWorked.setText("");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Количество дней должно быть в диапозоне[0,31]!");
                wasWorked.setText("");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Введите кол-во рабочих дней!");
            wasWorked.setText("");
        }
    }

    public void setWorkingDaysPushed(ActionEvent event){
        if(Integer.valueOf(workingDays.getText())<=31 && Integer.valueOf(workingDays.getText())>0) {
            if (!wasWorked.getText().equals("")) {
                if (Integer.valueOf(workingDays.getText()) < Integer.valueOf(wasWorked.getText())) {
                    JOptionPane.showMessageDialog(null, "Количество отработанных дней не может быть больше количества рабочих дней!");
                    wasWorked.setText("");
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Количество дней должно быть в диапозоне[0,31]!");
            workingDays.setText("");
        }
    }

    public static ObservableList<String> loadCharges() {
        List<Object> list = new ArrayList();
        String charge = "AllCharges";
        Object obj = (Object) charge;
        ObservableList<String> charges = FXCollections.observableArrayList();
        try {
            list = (ServerConnection.send(obj));
            for (Object object : list) {
                String charge1 = (String) object;
                charges.add(charge1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charges;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        dColumnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        cColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cColumnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        ObservableList<Integer> listMonth = FXCollections.observableArrayList();
        for (int i = 1; i< 13; i++){
            listMonth.add(i);
        }
        ObservableList<Integer> listYear = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        //int currentYear = 2021;
        for (int i = 2015; i<= currentYear; i++){
            listYear.add(i);
        }
        month.setItems(listMonth);
        year.setItems(listYear);
        ObservableList<String> listCharges = FXCollections.observableArrayList();
        listCharges=  loadCharges();
        pName.setItems(listCharges);
        ObservableList<String> listNameD = FXCollections.observableArrayList();
        List<Deducation> listDeduc = FXCollections.observableArrayList();
        listDeduc = Deducation.loadDeducation();
        for (Deducation d:listDeduc) {
            listNameD.add(d.getName());
            deducat.put(d.getName(),d.getPercent());
        }
        dName.setItems(listNameD);
        listEmployee = Employee.loadEmployee();
        if(listEmployee!=null) {
            ObservableList<String> listAccount = FXCollections.observableArrayList();
            for (Employee employee : listEmployee) {
                listAccount.add(employee.getBankAccount());
            }
            bank_account.setItems(listAccount);
        }
        else {
            JOptionPane.showMessageDialog(null, "Нет ни одного студента!");
        }
    }

}
