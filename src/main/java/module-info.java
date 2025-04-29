module org.example.prpraktikum8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.prpraktikum8 to javafx.fxml;
    exports org.example.prpraktikum8.EmployeeTable;
    opens org.example.prpraktikum8.EmployeeTable to javafx.fxml;
    exports org.example.prpraktikum8.EmployeeComms;
    opens org.example.prpraktikum8.EmployeeComms to javafx.fxml;
}