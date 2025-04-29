package org.example.prpraktikum8.EmployeeTable;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeTableApp extends Application {
    private final String url = "jdbc:postgresql://localhost:5432/oracle_hr";
    private final String user = "postgres";
    private final String password = "___";

    private TableView<Employee> table = new TableView<>();

    @Override
    public void start(Stage stage) {
        TableColumn<Employee, Integer> idCol = new TableColumn<>("Employee ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Employee, String> hireDateCol = new TableColumn<>("Hire Date");
        hireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));

        TableColumn<Employee, String> jobIdCol = new TableColumn<>("Job ID");
        jobIdCol.setCellValueFactory(new PropertyValueFactory<>("jobID"));

        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // custom styling for Salary column
        salaryCol.setCellFactory(column -> new TableCell<Employee, Double>() {
            @Override
            protected void updateItem(Double salary, boolean empty) {
                super.updateItem(salary, empty);
                if (empty || salary == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("$%.2f", salary));
                    setStyle(salary >= 10000 ? "-fx-background-color: #699851;" : "");
                }
            }
        });

        TableColumn<Employee, Double> commissionCol = new TableColumn<>("Commission %");
        commissionCol.setCellValueFactory(new PropertyValueFactory<>("commissionPct"));

        TableColumn<Employee, Integer> managerCol = new TableColumn<>("Manager ID");
        managerCol.setCellValueFactory(new PropertyValueFactory<>("managerID"));

        TableColumn<Employee, Integer> deptCol = new TableColumn<>("Department ID");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("departmentID"));

        table.getColumns().addAll(
                idCol, firstNameCol, lastNameCol, emailCol,
                phoneCol, hireDateCol, jobIdCol, salaryCol,
                commissionCol, managerCol, deptCol
        );
        table.setItems(getEmployees());

        idCol.setPrefWidth(120);
        firstNameCol.setPrefWidth(120);
        lastNameCol.setPrefWidth(120);
        emailCol.setPrefWidth(120);
        phoneCol.setPrefWidth(150);
        hireDateCol.setPrefWidth(120);
        salaryCol.setPrefWidth(100);
        commissionCol.setPrefWidth(120);
        managerCol.setPrefWidth(120);
        deptCol.setPrefWidth(140);

        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);


        Label title = new Label("「Employee Data Table」");
        title.setFont(Font.font("Britannic Bold", 24)); // Set custom font
        title.setStyle("-fx-text-fill: white;");     // Set font color
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);

        VBox root = new VBox(10, title, table);
        root.setAlignment(Pos.TOP_CENTER); // vertical alignment
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1000, 500);

        // Load the CSS
        scene.getStylesheets().add(getClass().getResource("/style1.css").toExternalForm());
        stage.setTitle("Employee Table");
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM employees";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("hire_date"),
                        rs.getString("job_id"),
                        rs.getDouble("salary"),
                        rs.getDouble("commission_pct"),
                        rs.getInt("manager_id"),
                        rs.getInt("department_id")
                ));
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
