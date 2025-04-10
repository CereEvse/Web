package ru.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.web.model.StatusResume;

public interface StatusResimeRepository extends JpaRepository<StatusResume, Long>, PagingAndSortingRepository<StatusResume, Long> {
}
