package com.example.demo.controller;

import com.example.demo.model.Status;
import com.example.demo.service.StatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    @PostMapping
    ResponseEntity<Void> addStatus(@RequestBody @Valid Status status, BindingResult bindingResult) {
        statusService.addStatus(status);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<Status>> getAllStatus() {
        return ResponseEntity.ok(statusService.getAllStatus());
    }

    @GetMapping("/{id}")
    ResponseEntity<Status> getStatusById(@PathVariable Long id) {
        Optional<Status> statusOptional = statusService.getStatusById(id);
        return statusOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Status> updateStatusById(@PathVariable Long id, @RequestBody @Valid Status updatedStatus) {
        Optional<Status> updatedStatusOptional = statusService.putStatusById(id, updatedStatus);
        return updatedStatusOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStatusById(@PathVariable Long id) {
        statusService.deleteStatusById(id);
        return ResponseEntity.ok().build();
    }
}
