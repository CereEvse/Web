package com.example.demo.service;

import com.example.demo.exceptions.ExceptionHandler;
import com.example.demo.model.Status;
import com.example.demo.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StatusServiceImpl implements StatusService{
    private final StatusRepository statusRepository;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void addStatus(Status status) {statusRepository.save(status);}

    @Override
    public List<Status> getAllStatus() {return statusRepository.findAll();}

    @Override
    public Optional<Status> getStatusById(Long id) {return statusRepository.findById(id);}

    @Override
    public Optional<Status> putStatusById(Long id, Status updatedStatus) {
        Optional<Status> existingStatus = statusRepository.findById(id);
        if(existingStatus.isPresent()) {
            Status statusToUpdate = existingStatus.get();
//            if(updatedStatus.getStatusTitle() != null) {
//                statusToUpdate.setStatusTitle(updatedStatus.getStatusTitle());
//            }
            statusRepository.save(statusToUpdate);
        }
        return existingStatus;
    }

    @Override
    public void deleteStatusById(Long id) {statusRepository.deleteById(id);}

}
