package com.ecommerce.admin.controller;

import com.ecommerce.admin.exception.ResourceNotFoundException;
import com.ecommerce.admin.model.Admin;
import com.ecommerce.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Admin not found with id: " + id));
        return ResponseEntity.ok().body(admin);
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails) {
        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Admin not found with id: " + id));

        admin.setUsername(adminDetails.getUsername());
        admin.setEmail(adminDetails.getEmail());
        admin.setPassword(adminDetails.getPassword());

        final Admin updatedAdmin = adminRepository.save(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Admin not found with id: " + id));

        adminRepository.delete(admin);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody Admin admin) {
        Optional<Admin> foundAdmin = adminRepository.findByEmail(admin.getEmail());

        if (foundAdmin.isPresent() && foundAdmin.get().getPassword().equals(admin.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }
}
