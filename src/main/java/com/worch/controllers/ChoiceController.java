package com.worch.controllers;

import com.worch.model.dto.response.ChoicesResponseDto;
import com.worch.service.ChoicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/choices")
@RequiredArgsConstructor
@Tag(name = "Choices API")
public class ChoiceController {

    private final ChoicesService choicesService;

    @Operation(
            summary = "Получить список чойсов",
            description = "Возвращает список чойсов только после" +
                    " авторизации с возможностью фильтрации по creator_id "
    )
    @GetMapping
    public ResponseEntity<List<ChoicesResponseDto>> getChoices(
            @RequestParam(name = "creator_id",required = false) UUID creator_id
    ){
        return ResponseEntity.ok(choicesService.getAllChoices(creator_id));
    }


}

