package com.worch.controllers;


import com.worch.mapper.ChoiceMapper;
import com.worch.mapper.VoteMapper;
import com.worch.mapper.ChoiceOptionMapper;
import com.worch.model.dto.request.CreateChoiceRequest;
import com.worch.model.dto.request.UpdateChoiceRequest;
import com.worch.model.dto.request.VoteRequest;
import com.worch.model.dto.response.ChoiceOptionStatsDto;
import com.worch.model.dto.response.ChoiceResponseDto;
import com.worch.model.dto.response.VoteResponseDto;
import com.worch.model.entity.ChoiceOption;
import com.worch.repository.VoteRepository;
import com.worch.service.ChoiceService;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/choices")
public class ChoiceController {

    private final ChoiceService choiceService;
    private final ChoiceMapper choiceMapper;
    private final ChoiceOptionMapper choiceOptionMapper;
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;


    @GetMapping("/{id}")
    public ResponseEntity<List<ChoiceOptionStatsDto>> getChoice(
            @PathVariable UUID id,
            @RequestParam UUID userId
    ) {
        List<ChoiceOption> options = choiceService.getChoiceOptionsOrElseThrow(id);

        List<ChoiceOptionStatsDto> result = options.stream()
                .map(option -> choiceOptionMapper.toStatsDto(
                        option,
                        voteRepository.countByOptionId(option.getId()),
                        voteRepository.existsByOptionIdAndUserId(option.getId(), userId)
                ))
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<ChoiceResponseDto>> getAllChoices(
            @RequestParam(name = "creator_id", required = false) UUID creatorId,
            @PageableDefault() Pageable pageable) {
        var page = choiceService.getAllChoices(Optional.ofNullable(creatorId), pageable)
                .map(choiceMapper::toDto);
        return ResponseEntity.ok(page);
    }

    @PostMapping()
    public ResponseEntity<ChoiceResponseDto> createChoice(
            @Valid @RequestBody CreateChoiceRequest choiceRequest) {
        log.info("Creating choice: {} ", choiceRequest);
        var created = choiceService.createChoice(choiceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(choiceMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChoiceResponseDto> updateChoice(@PathVariable UUID id,
                                                          @Valid @RequestBody UpdateChoiceRequest choiceRequest) {
        log.info("Updating choice: {}", id);
        var updated = choiceService.updateChoice(id, choiceRequest);
        return ResponseEntity.ok(choiceMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChoice(@PathVariable UUID id) {
        log.info("Deleting choice: {}", id);
        choiceService.deleteChoice(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<VoteResponseDto> voteForChoice(
            @PathVariable("id") UUID id,
            @Valid @RequestBody VoteRequest voteRequest) {

        log.info("Received vote request for choice {}",
                id);

        var vote = choiceService.voteForChoice(id, voteRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voteMapper.toDto(vote));
    }
}
