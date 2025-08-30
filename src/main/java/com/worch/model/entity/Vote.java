package com.worch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @Column(name = "choice_id")
    private UUID choiceId;

    @Column(name = "option_id")
    private UUID optionId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "voted_at")
    private OffsetDateTime votedAt;
}
