package com.hospital.backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")

public class Patient {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int patientId;

    private String patientName;

    private int days;

    private int totalFee;

    // ================= GETTERS & SETTERS =================

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }
}