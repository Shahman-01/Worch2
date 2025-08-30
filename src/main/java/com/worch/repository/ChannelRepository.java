package com.worch.repository;

import com.worch.model.entity.Channel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {

    @EntityGraph(value = "Channel.withMembers", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Channel> findWithMembersById(UUID id);
}