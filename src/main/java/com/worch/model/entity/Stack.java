package com.worch.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "stack")
@Getter
@Setter
public class Stack {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "creator_id", columnDefinition = "UUID")
    private UUID creatorId;

    @Column(name = "is_quiz")
    private Boolean isQuiz;

    @Column
    private Boolean published;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;
}