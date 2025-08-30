package com.worch.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "stack_item")
public class StackItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "stack_id")
    private UUID stackId;

    @Column(name = "choice_id")
    private UUID choiceId;

    private Integer position;

}