package com.worch.repository;

import com.worch.model.entity.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StackRepository extends JpaRepository<Stack, UUID> {
}
