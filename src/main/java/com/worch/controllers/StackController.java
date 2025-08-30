package com.worch.controllers;

import com.worch.mapper.StackMapper;
import com.worch.model.dto.request.StackChoiceLinkRequest;
import com.worch.model.dto.response.StackResponseDto;
import com.worch.service.StackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stacks")
public class StackController {

    private final StackService stackService;
    private final StackMapper stackMapper;

    @Operation(
        summary = "Получить список стэков",
        description = "Возвращает постраничный список стэков с возможностью пагинации"
    )
    @GetMapping
    public ResponseEntity<Page<StackResponseDto>> getStacks(
        @Parameter(description = "Параметры пагинации")
        @PageableDefault() Pageable pageable
    ) {
        var page = stackService.getAllStacks(pageable).map(stackMapper::toDto);
        return ResponseEntity.ok(page);
    }

    @PostMapping("{stackId}/add-choice")
    public ResponseEntity<String> addChoice(@PathVariable UUID stackId,
                                            @RequestBody StackChoiceLinkRequest choiceLinkRequest) {

        stackService.addChoiceToStack(stackId, choiceLinkRequest.choice());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{stackId}/remove-choice")
    public ResponseEntity<String> deleteChoice(@PathVariable UUID stackId,
                                               @RequestBody StackChoiceLinkRequest choiceLinkRequest) {

        stackService.deleteChoiceFromStack(stackId, choiceLinkRequest.choice());
        return ResponseEntity.ok().build();
    }
}