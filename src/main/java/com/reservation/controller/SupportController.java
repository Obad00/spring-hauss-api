package com.reservation.controller;

import com.reservation.entity.Support;
import com.reservation.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supports")
public class SupportController {

    @Autowired
    private SupportService supportService;

    @GetMapping
    public List<Support> getAllSupports() {
        return supportService.getAllSupports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Support> getSupportById(@PathVariable Long id) {
        return supportService.getSupportById(id)
                .map(support -> ResponseEntity.ok().body(support))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Support createSupport(@RequestBody Support support) {
        return supportService.createSupport(support);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Support> updateSupport(@PathVariable Long id, @RequestBody Support updatedSupport) {
        try {
            Support support = supportService.updateSupport(id, updatedSupport);
            return ResponseEntity.ok().body(support);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupport(@PathVariable Long id) {
        supportService.deleteSupport(id);
        return ResponseEntity.noContent().build();
    }
}
