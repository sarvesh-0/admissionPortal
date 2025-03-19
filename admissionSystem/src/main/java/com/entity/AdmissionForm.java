package com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "admission_form")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionForm {

    @Id
    private String admissionId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;
    private String fullName;
    private String motherName;

    @NotBlank(message = "Gender is required")
    private String gender;
    private String address;
    private String taluka;
    private String district;

    @Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits")
    private String pinCode;
    private String state;

    @Pattern(regexp = "[6-9]\\d{9}", message = "Invalid mobile number")
    private String mobile;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{12}", message = "Aadhaar must be 12 digits")
    private String aadhaar;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of Birth cannot be null")
    private LocalDate dob;


    private Integer age;
    private String religion;
    private String casteCategory;
    private String caste;
    private String physicallyHandicapped;
    private String marksheet;  // Store file path
    private String photo;      // Store file path
    private String signature;    // Store file path


    @PrePersist
    public void generateAdmissionId() {
        this.admissionId = "ADM" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }

    @PreUpdate
    public void calculateAge() {
        if (dob != null) {
            this.age = LocalDate.now().getYear() - dob.getYear();
        }
    }
}
