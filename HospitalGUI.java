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

        DBConnection.getConnection();

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
                        36
                )
        );

        title.setTextFill(Color.WHITE);

        Label subtitle =
                new Label(
                        "Professional Hospital Management Dashboard"
                );

        subtitle.setTextFill(Color.web("#dbeafe"));

        subtitle.setFont(Font.font(16));

        VBox headerBox =
                new VBox(8, title, subtitle);

        headerBox.setAlignment(Pos.CENTER_LEFT);

        headerBox.setPadding(
                new Insets(25)
        );

        headerBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #0f172a, #2563eb);" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12,0,0,4);"
        );

        // ================= DASHBOARD CARDS =================

        VBox patientCard =
                createCard("👨‍⚕ Total Patients", "120");

        VBox revenueCard =
                createCard("💰 Revenue", "₹85,000");

        VBox doctorCard =
                createCard("🩺 Doctors", "15");

        VBox bedCard =
                createCard("🛏 Available Beds", "32");

        HBox dashboardCards =
                new HBox(
                        20,
                        patientCard,
                        revenueCard,
                        doctorCard,
                        bedCard
                );

        // ================= LEFT PANEL =================

        Label formTitle =
                new Label("Patient Details");

        formTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        24
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

        idField.setPrefHeight(45);
        nameField.setPrefHeight(45);
        daysField.setPrefHeight(45);

        idField.setStyle(fieldStyle());
        nameField.setStyle(fieldStyle());
        daysField.setStyle(fieldStyle());

        // ================= BUTTONS =================

        Button admitBtn =
                createButton(
                        "➕ Admit Patient",
                        "#10b981"
                );

        Button searchBtn =
                createButton(
                        "🔍 Search Patient",
                        "#2563eb"
                );

        Button dischargeBtn =
                createButton(
                        "🚑 Discharge Patient",
                        "#ef4444"
                );

        Button statusBtn =
                createButton(
                        "📊 Hospital Status",
                        "#f59e0b"
                );

        VBox buttonBox =
                new VBox(
                        18,
                        admitBtn,
                        searchBtn,
                        dischargeBtn,
                        statusBtn
                );

        buttonBox.setAlignment(Pos.CENTER);

        VBox leftPanel =
                new VBox(
                        25,
                        formTitle,
                        idField,
                        nameField,
                        daysField,
                        buttonBox
                );

        leftPanel.setPadding(
                new Insets(30)
        );

        leftPanel.setPrefWidth(330);

        leftPanel.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12,0,0,4);"
        );

        // ================= RIGHT PANEL =================

        Label outputTitle =
                new Label("Hospital Activity");

        outputTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        24
                )
        );

        output =
                new TextArea();

        output.setEditable(false);

        output.setWrapText(true);

        output.setPrefHeight(470);

        output.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 15;" +
                "-fx-control-inner-background: #f8fafc;" +
                "-fx-border-color: #cbd5e1;" +
                "-fx-border-radius: 15;"
        );

        VBox rightPanel =
                new VBox(
                        20,
                        outputTitle,
                        output
                );

        rightPanel.setPadding(
                new Insets(30)
        );

        rightPanel.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12,0,0,4);"
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

                int totalFee =
                        days * 700;

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
                            "\n====================================\n" +
                            "✅ PATIENT ADMITTED SUCCESSFULLY\n\n" +
                            "🆔 Patient ID : " + id + "\n" +
                            "👤 Name : " + name + "\n" +
                            "📅 Days : " + days + "\n" +
                            "💰 Total Fee : ₹" + totalFee + "\n" +
                            "====================================\n"
                    );

                } else {

                    output.appendText(
                            "❌ Failed To Save Patient\n"
                    );
                }

                Room patient =
                        new Room(id, name);

                hospital.admitPatient(patient);

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

            output.appendText(
                    "\n🔍 Patient Search Completed\n"
            );
        });

        dischargeBtn.setOnAction(e -> {

            output.appendText(
                    "\n🚑 Patient Discharged Successfully\n"
            );
        });

        statusBtn.setOnAction(e -> {

            output.appendText(
                    "\n📊 Hospital Status Updated\n"
            );
        });

        // ================= ROOT =================

        VBox root =
                new VBox(
                        25,
                        headerBox,
                        dashboardCards,
                        mainContent
                );

        root.setPadding(
                new Insets(20)
        );

        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #f8fafc, #e2e8f0);"
        );

        Scene scene =
                new Scene(root, 1300, 820);

        stage.setTitle(
                "Hospital Management System"
        );

        stage.setScene(scene);

        stage.show();
    }

    // ================= CARD =================

    private VBox createCard(
            String title,
            String value
    ) {

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-text-fill: #475569;" +
                "-fx-font-weight: bold;"
        );

        Label valueLabel =
                new Label(value);

        valueLabel.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #0f172a;"
        );

        VBox card =
                new VBox(
                        12,
                        titleLabel,
                        valueLabel
                );

        card.setPadding(
                new Insets(20)
        );

        card.setPrefWidth(250);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10,0,0,4);"
        );

        return card;
    }

    // ================= BUTTON DESIGN =================

    private Button createButton(
            String text,
            String color
    ) {

        Button btn =
                new Button(text);

        btn.setPrefWidth(250);

        btn.setPrefHeight(50);

        btn.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14;" +
                "-fx-cursor: hand;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-color: derive(" + color + ", -15%);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 14;" +
                        "-fx-cursor: hand;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 14;" +
                        "-fx-cursor: hand;"
                )
        );

        return btn;
    }

    // ================= FIELD DESIGN =================

    private String fieldStyle() {

        return
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-padding: 10;" +
                "-fx-border-color: #d1d5db;" +
                "-fx-background-color: white;";
    }

    public static void main(String[] args) {

        DBConnection.getConnection();

        launch();
    }
}