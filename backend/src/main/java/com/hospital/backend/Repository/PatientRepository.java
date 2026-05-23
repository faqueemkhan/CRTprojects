package com.hospital.backend.Repository;

import com.hospital.backend.Entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PatientRepository
        extends JpaRepository<Patient, Integer> {

}