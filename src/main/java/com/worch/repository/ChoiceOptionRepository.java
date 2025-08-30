package com.worch.repository;

import com.worch.model.entity.ChoiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChoiceOptionRepository extends JpaRepository<ChoiceOption, UUID> {
    List<ChoiceOption> findByChoiceId(UUID choiceId);
}
