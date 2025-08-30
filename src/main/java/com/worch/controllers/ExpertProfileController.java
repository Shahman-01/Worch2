package com.worch.controllers;

import com.worch.mapper.ExpertProfileMapper;
import com.worch.model.dto.response.ExpertProfileResponse;
import com.worch.service.ExpertProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/experts")
public class ExpertProfileController {

    private final ExpertProfileService expertProfileService;
    private final ExpertProfileMapper expertProfileMapper;

    @Operation(summary = "Получить список всех экспертов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список экспертов успешно получен",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpertProfileResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<ExpertProfileResponse>> expertProfileList() {
        List<ExpertProfileResponse> experts = expertProfileService.getAll().stream()
                .map(expertProfileMapper::toDto)
                .toList();

        return ResponseEntity.ok(experts);
    }
}
