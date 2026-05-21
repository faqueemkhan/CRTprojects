import javafx.scene.control.Label;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HospitalGUI extends Application {

    HospitalManagement hospital;

    TextArea output;

    @Override
    public void start(Stage stage) {

        // Database Connection
        DBConnection.getConnection();

        // Doctor
        Doctor d1 =
                new Doctor(
                        "Dr. Sharma",
                        "Cardiologist"
                );

        hospital =
                new HospitalManagement(10, d1);

        // ================= HEADER =================

        Label title =
                new Label("RAMDEOBABA HOSPITAL");

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        34
                )
        );

        title.setTextFill(Color.WHITE);

        Label subtitle =
                new Label(
                        "Hospital Management Dashboard"
                );

        subtitle.setTextFill(Color.WHITE);

        subtitle.setFont(
                Font.font(16)
        );

        VBox headerBox =
                new VBox(5, title, subtitle);

        headerBox.setAlignment(Pos.CENTER_LEFT);

        headerBox.setPadding(
                new Insets(20)
        );

        headerBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #0f4c81, #3fa9f5);" +
                        "-fx-background-radius: 15;"
        );

        // ================= LEFT PANEL =================

        Label formTitle =
                new Label("Patient Details");

        formTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        22
                )
        );

        TextField idField =
                new TextField();

        idField.setPromptText(
                "Enter Patient ID"
        );

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Enter Patient Name"
        );

        TextField daysField =
                new TextField();

        daysField.setPromptText(
                "Enter Number of Days"
        );

        idField.setPrefHeight(40);
        nameField.setPrefHeight(40);
        daysField.setPrefHeight(40);

        idField.setStyle(fieldStyle());
        nameField.setStyle(fieldStyle());
        daysField.setStyle(fieldStyle());

        // ================= BUTTONS =================

        Button admitBtn =
                createButton(
                        "Admit Patient",
                        "#28a745"
                );

        Button searchBtn =
                createButton(
                        "Search Patient",
                        "#007bff"
                );

        Button dischargeBtn =
                createButton(
                        "Discharge Patient",
                        "#dc3545"
                );

        Button statusBtn =
                createButton(
                        "Hospital Status",
                        "#ff9800"
                );

        VBox buttonBox =
                new VBox(
                        15,
                        admitBtn,
                        searchBtn,
                        dischargeBtn,
                        statusBtn
                );

        buttonBox.setAlignment(Pos.CENTER);

        VBox leftPanel =
                new VBox(
                        20,
                        formTitle,
                        idField,
                        nameField,
                        daysField,
                        buttonBox
                );

        leftPanel.setPadding(
                new Insets(25)
        );

        leftPanel.setPrefWidth(320);

        leftPanel.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: #dcdcdc;"
        );

        // ================= RIGHT PANEL =================

        Label outputTitle =
                new Label("Hospital Activity");

        outputTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        22
                )
        );

        output =
                new TextArea();

        output.setEditable(false);

        output.setWrapText(true);

        output.setPrefHeight(450);

        output.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-radius: 15;" +
                        "-fx-control-inner-background: #f8f9fa;"
        );

        VBox rightPanel =
                new VBox(
                        20,
                        outputTitle,
                        output
                );

        rightPanel.setPadding(
                new Insets(25)
        );

        rightPanel.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: #dcdcdc;"
        );

        HBox mainContent =
                new HBox(
                        25,
                        leftPanel,
                        rightPanel
                );

        // ================= BUTTON ACTIONS =================

       admitBtn.setOnAction(e -> {

    try {

        int id =
                Integer.parseInt(
                        idField.getText()
                );

        String name =
                nameField.getText();

        int days =
                Integer.parseInt(
                        daysField.getText()
                );

        // ================= TOTAL FEE =================

        int totalFee =
                days * 700;

        // ================= DATABASE CODE =================

        java.sql.Connection con =
                DBConnection.getConnection();

        String query =
                "INSERT INTO patients(patient_id, patient_name, days, total_fee) VALUES (?, ?, ?, ?)";

        java.sql.PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, id);

        ps.setString(2, name);

        ps.setInt(3, days);

        ps.setInt(4, totalFee);

        int rows =
                ps.executeUpdate();

        if (rows > 0) {

            output.appendText(
                    "====================================\n" +
                    "✅ Patient Saved Successfully\n\n" +
                    "🆔 Patient ID : " + id + "\n" +
                    "👤 Patient Name : " + name + "\n" +
                    "📅 Days : " + days + "\n" +
                    "💰 Total Fee : ₹" + totalFee + "\n" +
                    "====================================\n\n"
            );

        } else {

            output.appendText(
                    "❌ Failed To Save Patient\n"
            );
        }

        // ================= HOSPITAL OBJECT =================

        Room patient =
                new Room(id, name);

        hospital.admitPatient(patient);

        // ================= CLEAR FIELDS =================

        idField.clear();

        nameField.clear();

        daysField.clear();

    } catch (Exception ex) {

        output.appendText(
                "❌ Database Error\n"
        );

        ex.printStackTrace();
    }
});

        searchBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText()
                        );

                hospital.searchPatient(id);

                output.appendText(
                        "🔍 Search Completed\n"
                );

            } catch (Exception ex) {

                output.appendText(
                        "❌ Invalid Input\n"
                );
            }
        });

        dischargeBtn.setOnAction(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField.getText()
                        );

                int days =
                        Integer.parseInt(
                                daysField.getText()
                        );

                hospital.dischargePatient(
                        id,
                        days
                );

                output.appendText(
                        "🚑 Patient Discharged Successfully\n"
                );

            } catch (Exception ex) {

                output.appendText(
                        "❌ Invalid Input\n"
                );
            }
        });

        statusBtn.setOnAction(e -> {

            hospital.displayStatus();

            output.appendText(
                    "📊 Hospital Status Updated\n"
            );
        });

        // ================= ROOT =================

        VBox root =
                new VBox(
                        25,
                        headerBox,
                        mainContent
                );

        root.setPadding(
                new Insets(20)
        );

        root.setStyle(
                "-fx-background-color: #eef2f7;"
        );

        Scene scene =
                new Scene(root, 1100, 700);

        stage.setTitle(
                "Hospital Management System"
        );

        stage.setScene(scene);

        stage.show();
    }

    // ================= BUTTON DESIGN =================

    private Button createButton(
            String text,
            String color
    ) {

        Button btn =
                new Button(text);

        btn.setPrefWidth(250);

        btn.setPrefHeight(45);

        btn.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;"
        );

        return btn;
    }

    // ================= FIELD DESIGN =================

    private String fieldStyle() {

        return
                "-fx-font-size: 14px;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #cfd8dc;";
    }

    public static void main(String[] args) {

        DBConnection.getConnection();

        launch();
    }
}