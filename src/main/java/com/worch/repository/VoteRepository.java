package com.worch.repository;

import com.worch.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    boolean existsByChoiceIdAndUserId(UUID choiceId, UUID userId);
    long countByOptionId(UUID optionId);
    boolean existsByOptionIdAndUserId(UUID optionId, UUID userId);
}
