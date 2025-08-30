package com.worch.controllers;

import com.worch.model.dto.response.GroupDTO;
import com.worch.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupsController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getGroups(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(groupService.getGroups());
    }
}
