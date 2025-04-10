package ru.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.web.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long>, PagingAndSortingRepository<Resume, Long> {
}
