package kigali.clinic.rw.domain;

import java.sql.Date;

public class AppointmentDTO {
    
    private Date appointmentDate;
    private String patientName;
    private String doctorName;

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }
}
