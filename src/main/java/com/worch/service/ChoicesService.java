package com.worch.service;

import com.worch.mapper.ChoiceMapper;
import com.worch.model.dto.response.ChoicesResponseDto;
import com.worch.model.entity.Choice;
import com.worch.model.specification.ChoiceSpecifications;
import com.worch.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChoicesService {

    private final ChoiceRepository choiceRepository;
    private final ChoiceMapper choiceMapper;

    public List<ChoicesResponseDto> getAllChoices(UUID creatorId) {

        Specification<Choice> spec = Specification
                .where(ChoiceSpecifications.byCreatorId(creatorId));

        return choiceRepository.findAll(spec)
                .stream()
                .map(choiceMapper::toDto)
                .toList();
    }
}
