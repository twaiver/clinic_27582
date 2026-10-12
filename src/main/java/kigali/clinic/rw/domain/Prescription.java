package kigali.clinic.rw.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "prescription")
public class Prescription {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String medecineName;

    private String dosage;

    private int durationDays;

    private LocalDate issuedDate;

    @ManyToOne
    @JoinColumn (name = "appointment_id")
    private Appointment appointment;

    public UUID getId() {
        return id;
    }

    public void setMedecineName(String medecineName) {
        this.medecineName = medecineName;
    }

    public String getMedecineName() {
        return medecineName;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}