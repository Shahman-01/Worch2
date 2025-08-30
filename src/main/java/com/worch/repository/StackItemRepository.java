package com.worch.repository;

import com.worch.model.entity.StackItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StackItemRepository extends JpaRepository<StackItem, UUID> {
    Optional<StackItem> findByStackIdAndChoiceId(UUID stackId, UUID choiceId);
}
