package com.example.demo.repository;

import com.example.demo.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long>, PagingAndSortingRepository<Resume, Long> {
}
