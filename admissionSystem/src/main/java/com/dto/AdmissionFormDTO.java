package com.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionFormDTO {

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String motherName;
    private String gender;
    private String address;
    private String taluka;
    private String district;
    private String pinCode;
    private String state;
    private String mobile;
    private String email;
    private String aadhaar;
    private LocalDate dob;
    private String religion;
    private String casteCategory;
    private String caste;
    private String physicallyHandicapped;

    private MultipartFile marksheet;
    private MultipartFile photo;
    private MultipartFile signature;
}
