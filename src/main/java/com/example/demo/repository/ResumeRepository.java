package com.example.demo.repository;

import com.example.demo.model.Resume;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long>, PagingAndSortingRepository<Resume, Long> {
    @Query("SELECT r FROM resume r WHERE r.user.idUser = :userId")
    Optional<Resume> findByUserId(@Param("userId") Long userId);
}
