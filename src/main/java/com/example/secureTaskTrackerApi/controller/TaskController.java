package com.example.secureTaskTrackerApi.controller;

import com.example.secureTaskTrackerApi.entity.User;
import com.example.secureTaskTrackerApi.model.Task;
import com.example.secureTaskTrackerApi.service.TaskService;
import com.example.secureTaskTrackerApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        task.setUser(user);
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        return taskService.getTasksByUser(user);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask,
                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        return taskService.updateTask(id, updatedTask, user);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        taskService.deleteTask(id, user);
    }
}
