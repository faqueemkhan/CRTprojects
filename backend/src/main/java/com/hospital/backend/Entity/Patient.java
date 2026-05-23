package com.hospital.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")

public class Patient {

    @Id
    private int patientId;

    private String patientName;

    private int days;

    private int totalFee;

    // ================= DEFAULT CONSTRUCTOR =================

    public Patient() {

    }

    // ================= PARAMETERIZED CONSTRUCTOR =================

    public Patient(int patientId,
                   String patientName,
                   int days,
                   int totalFee) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.days = days;
        this.totalFee = totalFee;
    }

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