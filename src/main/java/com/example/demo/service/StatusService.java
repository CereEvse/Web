package com.example.demo.service;

import com.example.demo.model.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    void addStatus(Status status);
    List<Status> getAllStatus();
    Optional<Status> getStatusById(Long id);
    Optional<Status> putStatusById(Long id, Status updatedStatus);
    void deleteStatusById(Long id);
}
