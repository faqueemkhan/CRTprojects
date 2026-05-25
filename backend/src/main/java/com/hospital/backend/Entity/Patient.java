package com.hospital.backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long patientId;

    private String patientName;

    private int days;

    private int totalFee;

    // ================= GETTERS =================

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getDays() {
        return days;
    }

    public int getTotalFee() {
        return totalFee;
    }

    // ================= SETTERS =================

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }
}