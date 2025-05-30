package com.example.demo.repository;

import com.example.demo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long>, PagingAndSortingRepository<Status, Long> {
    List<Status> findByStatusTitle(String statusTitle);
}
