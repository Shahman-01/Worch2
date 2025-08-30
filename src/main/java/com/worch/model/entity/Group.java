package com.worch.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Entity
@Getter
@Setter
@Table(name = "group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "created_at")
    private Instant createdAt;
}
