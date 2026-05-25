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

    // ================= ADD PATIENT =================

    @PostMapping

    public Patient addPatient(@RequestBody Patient patient) {

        return patientRepository.save(patient);
    }

    // ================= DELETE PATIENT =================

    @DeleteMapping("/{id}")

    public String deletePatient(@PathVariable Long id) {

        patientRepository.deleteById(id);

        return "Patient Deleted Successfully";
    }
}