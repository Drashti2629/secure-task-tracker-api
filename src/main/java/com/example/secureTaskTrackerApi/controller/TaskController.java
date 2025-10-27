package com.example.secureTaskTrackerApi.controller;

import com.example.secureTaskTrackerApi.entity.Task;
import com.example.secureTaskTrackerApi.repository.TaskRepository;
import com.example.secureTaskTrackerApi.repository.UserRepository;
import com.example.secureTaskTrackerApi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        task.setUser(userRepository.findByEmail(email).orElseThrow());
        return ResponseEntity.ok(taskRepository.save(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        return ResponseEntity.ok(taskRepository.findByUserEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task, @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Task existing = taskRepository.findById(id).orElseThrow();
        if(!existing.getUser().getEmail().equals(email)) return ResponseEntity.status(403).build();
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        return ResponseEntity.ok(taskRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Task existing = taskRepository.findById(id).orElseThrow();
        if(!existing.getUser().getEmail().equals(email)) return ResponseEntity.status(403).build();
        taskRepository.delete(existing);
        return ResponseEntity.ok(Map.of("message","Task deleted"));
    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<Task>> getFilteredTasks(
            @RequestParam String status,
            @RequestParam int page,
            @RequestParam int size,
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(taskRepository.findByUserEmailAndStatus(email, status, pageable));
    }

}
