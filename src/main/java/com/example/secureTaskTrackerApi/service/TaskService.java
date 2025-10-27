package com.example.secureTaskTrackerApi.service;


import com.example.secureTaskTrackerApi.entity.Task;
import com.example.secureTaskTrackerApi.entity.User;
import com.example.secureTaskTrackerApi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public Task updateTask(Long taskId, Task updatedTask, User user) {
        return taskRepository.findByIdAndUser(taskId, user)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found or not authorized"));
    }

    public void deleteTask(Long taskId, User user) {
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found or not authorized"));
        taskRepository.delete(task);
    }
}
