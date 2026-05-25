package com.hospital.backend.Controller;

import com.hospital.backend.Entity.Patient;
import com.hospital.backend.Repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@CrossOrigin("*")

public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // ================= GET ALL PATIENTS =================

    @GetMapping
    public List<Patient> getAllPatients() {

        return patientRepository.findAll();
    }

    // ================= SAVE PATIENT =================

    @PostMapping
    public Patient savePatient(
            @RequestBody Patient patient
    ) {

        return patientRepository.save(patient);
    }

    // ================= DELETE PATIENT =================

    @DeleteMapping("/{id}")
    public String deletePatient(
            @PathVariable int id
    ) {

        patientRepository.deleteById(id);

        return "Patient Deleted Successfully";
    }

    // ================= GET SINGLE PATIENT =================

    @GetMapping("/{id}")
    public Patient getPatientById(
            @PathVariable int id
    ) {

        return patientRepository.findById(id)
                .orElse(null);
    }
}