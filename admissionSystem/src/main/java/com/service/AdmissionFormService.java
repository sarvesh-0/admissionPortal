package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dto.AdmissionFormDTO;
import com.entity.AdmissionForm;
import com.repository.AdmissionFormRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AdmissionFormService {

   @Autowired
   private AdmissionFormRepository admissionFormRepository;

   @Value("${file.upload-dir}") // Configure this in application.properties
   private String uploadDir;

   @Transactional
   public AdmissionForm createAdmissionForm(AdmissionFormDTO formDTO) throws IOException {
       // Validate files (size, type) - you can create a separate validator class

       AdmissionForm form = new AdmissionForm();

       form.setTitle(formDTO.getTitle());
       form.setFirstName(formDTO.getFirstName());
       form.setMiddleName(formDTO.getMiddleName());
       form.setLastName(formDTO.getLastName());
       form.setFullName(formDTO.getFirstName()+" "+formDTO.getMiddleName()+" "+formDTO.getLastName());
       form.setMotherName(formDTO.getMotherName());
       form.setGender(formDTO.getGender());
       form.setAddress(formDTO.getAddress());
       form.setTaluka(formDTO.getTaluka());
       form.setDistrict(formDTO.getDistrict());
       form.setPinCode(formDTO.getPinCode());
       form.setState(formDTO.getState());
       form.setMobile(formDTO.getMobile());
       form.setEmail(formDTO.getEmail());
       form.setAadhaar(formDTO.getAadhaar());
       form.setDob(formDTO.getDob());
       form.setReligion(formDTO.getReligion());
       form.setCasteCategory(formDTO.getCasteCategory());
       form.setCaste(formDTO.getCaste());
       form.setPhysicallyHandicapped(formDTO.getPhysicallyHandicapped());

       // Handle file uploads
       form.setMarksheet(saveFile(formDTO.getMarksheet()));
       form.setPhoto(saveFile(formDTO.getPhoto()));
       form.setSignature(saveFile(formDTO.getSignature()));
       return admissionFormRepository.save(form);
   }



   private String saveFile(MultipartFile file) throws IOException {
       if (file.isEmpty()) {
           return null; // Or handle the case where no file is uploaded
       }

       String originalFilename = file.getOriginalFilename();

       // Use UUID for unique filenames
       String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

       // Create directory if it doesn't exist
       Path uploadPath = Paths.get(uploadDir);
       if (!Files.exists(uploadPath)) {
           Files.createDirectories(uploadPath);
       }


       Path filePath = uploadPath.resolve(uniqueFilename); // Use Paths.get
       Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
       return filePath.toString(); // Return the full path

   }

   public List<AdmissionForm> getAllForms() {
       return admissionFormRepository.findAll();
   }
   
   public AdmissionForm getAdmissionFormById(String id) {
       return admissionFormRepository.findById(id)
           .orElseThrow(() -> new RuntimeException("Admission form not found with id: " + id));
   }

   @Transactional
   public AdmissionForm updateAdmissionForm(String id, AdmissionFormDTO formDTO) throws IOException {
       AdmissionForm existingForm = getAdmissionFormById(id); // Use the method

       // Update fields (similar to create, but we're updating an existing object)
       existingForm.setTitle(formDTO.getTitle());
       existingForm.setFirstName(formDTO.getFirstName());
       existingForm.setMiddleName(formDTO.getMiddleName());
       existingForm.setLastName(formDTO.getLastName());
       existingForm.setFullName(formDTO.getFirstName() + " " + formDTO.getMiddleName() + " " + formDTO.getLastName());
       existingForm.setMotherName(formDTO.getMotherName());
       existingForm.setGender(formDTO.getGender());
       existingForm.setAddress(formDTO.getAddress());
       existingForm.setTaluka(formDTO.getTaluka());
       existingForm.setDistrict(formDTO.getDistrict());
       existingForm.setPinCode(formDTO.getPinCode());
       existingForm.setState(formDTO.getState());
       existingForm.setMobile(formDTO.getMobile());
       existingForm.setEmail(formDTO.getEmail());
       existingForm.setAadhaar(formDTO.getAadhaar());
       existingForm.setDob(formDTO.getDob());
       existingForm.setReligion(formDTO.getReligion());
       existingForm.setCasteCategory(formDTO.getCasteCategory());
       existingForm.setCaste(formDTO.getCaste());
       existingForm.setPhysicallyHandicapped(formDTO.getPhysicallyHandicapped());

       // Handle file updates *carefully*
       if (formDTO.getMarksheet() != null && !formDTO.getMarksheet().isEmpty()) {
           deleteFile(existingForm.getMarksheet()); // Delete old file
           existingForm.setMarksheet(saveFile(formDTO.getMarksheet()));
       }
       if (formDTO.getPhoto() != null && !formDTO.getPhoto().isEmpty()) {
           deleteFile(existingForm.getPhoto());
           existingForm.setPhoto(saveFile(formDTO.getPhoto()));
       }
       if (formDTO.getSignature() != null && !formDTO.getSignature().isEmpty()) {
           deleteFile(existingForm.getSignature());
           existingForm.setSignature(saveFile(formDTO.getSignature()));
       }
       return admissionFormRepository.save(existingForm);
   }

 @Transactional
   public void deleteAdmissionForm(String id) {
       try {
           AdmissionForm form = getAdmissionFormById(id); // Reuse for efficiency

           // Delete associated files *before* deleting the database record
           deleteFile(form.getMarksheet());
           deleteFile(form.getPhoto());
           deleteFile(form.getSignature());

           admissionFormRepository.deleteById(id);
      } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("Admission form not found with id: " + id);
      }
   }
 
 private void deleteFile(String filePath) {
	    if (filePath != null && !filePath.isEmpty()) {
	        try {
	            Path path = Paths.get(filePath);
	            Files.deleteIfExists(path);
	        } catch (NoSuchFileException e) {
	            System.err.println("File to delete not found: " + filePath);
	        } catch (IOException e) {
	            System.err.println("Error deleting file: " + filePath + ", " + e.getMessage());
	            // Consider throwing a custom exception here to be handled by the controller
	        }
	      }
	   }
	}