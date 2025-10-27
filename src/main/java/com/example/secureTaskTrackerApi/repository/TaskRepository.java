package com.example.secureTaskTrackerApi.repository;

import com.example.secureTaskTrackerApi.entity.Task;
import com.example.secureTaskTrackerApi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    Optional<Task> findByIdAndUser(Long id, User user);

    Page<Task> findByUserEmailAndStatus(String email, String status, Pageable pageable);

    List<Task> findByUserEmail(String email);
}
