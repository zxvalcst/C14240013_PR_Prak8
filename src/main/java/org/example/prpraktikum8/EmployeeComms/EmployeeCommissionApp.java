package org.example.prpraktikum8.EmployeeComms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.prpraktikum8.EmployeeTable.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class EmployeeCommissionApp extends Application {
    private final String url = "jdbc:postgresql://localhost:5432/oracle_hr";
    private final String user = "postgres";
    private final String password = "___";

    private TableView<EmployeeCommission> table = new TableView<>();

    @Override
    public void start(Stage stage) {
        TableColumn<EmployeeCommission, String> nameCol = new TableColumn<>("Full Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<EmployeeCommission, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salaryCol.setCellFactory(column -> new TableCell<EmployeeCommission, Double>() {
            @Override
            protected void updateItem(Double salary, boolean empty) {
                super.updateItem(salary, empty);
                if (empty || salary == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("$%.2f", salary));
                }
            }
        });

        TableColumn<EmployeeCommission, Double> commissionPctCol = new TableColumn<>("Commission Percent");
        commissionPctCol.setCellValueFactory(new PropertyValueFactory<>("commissionPct"));

        TableColumn<EmployeeCommission, Double> commissionCol = new TableColumn<>("Commission");
        commissionCol.setCellValueFactory(new PropertyValueFactory<>("commission"));

        // custom styling for comms column
        commissionCol.setCellFactory(column -> new TableCell<EmployeeCommission, Double>() {
            @Override
            protected void updateItem(Double commission, boolean empty) {
                super.updateItem(commission, empty);
                if (empty || commission == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("$%.2f", commission));
                }
            }
        });

        TableColumn<EmployeeCommission, Double> totalSalaryCol = new TableColumn<>("Total Salary");
        totalSalaryCol.setCellValueFactory(new PropertyValueFactory<>("totalSalary"));

        // custom styling for total salary column
        totalSalaryCol.setCellFactory(column -> new TableCell<EmployeeCommission, Double>() {
            @Override
            protected void updateItem(Double totalSalary, boolean empty) {
                super.updateItem(totalSalary, empty);
                if (empty || totalSalary == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("$%.2f", totalSalary));
                }
            }
        });

        table.getColumns().addAll(nameCol, salaryCol, commissionPctCol, commissionCol, totalSalaryCol);
        table.setItems(getEmployeeCommissions());

        nameCol.setPrefWidth(150);
        salaryCol.setPrefWidth(100);
        commissionPctCol.setPrefWidth(160);
        commissionCol.setPrefWidth(120);
        totalSalaryCol.setPrefWidth(140);

        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        Label title = new Label("「Employee Commission Report」");
        title.setFont(Font.font("Britannic Bold", 24)); // Set custom font
        title.setStyle("-fx-text-fill: white;");     // Set font color
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);

        VBox root = new VBox(10, title, table);
        root.setAlignment(Pos.TOP_CENTER); // vertical alignment
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 700, 500);

        // Load the CSS
        scene.getStylesheets().add(getClass().getResource("/style1.css").toExternalForm());

        stage.setTitle("Employee Commission Report");
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<EmployeeCommission> getEmployeeCommissions() {
        ObservableList<EmployeeCommission> employees = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT first_name, last_name, salary, COALESCE(commission_pct, 0) AS commission_pct FROM employees";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DecimalFormat format = new DecimalFormat("##,###.00");
            while (rs.next()) {
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                double salary = rs.getDouble("salary");
                double commissionPct = rs.getDouble("commission_pct");
                double commission = salary * commissionPct;
                double totalSalary = salary + commission;

                employees.add(new EmployeeCommission(fullName, salary, commissionPct, commission, totalSalary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
