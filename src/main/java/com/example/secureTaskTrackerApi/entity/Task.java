package com.example.secureTaskTrackerApi.entity;

import com.example.secureTaskTrackerApi.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String status; // e.g., "IN_PROGRESS", "DONE"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
