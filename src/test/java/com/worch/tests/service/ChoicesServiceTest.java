package com.worch.tests.service;

import com.worch.mapper.ChoiceMapper;
import com.worch.model.dto.response.ChoicesResponseDto;
import com.worch.model.entity.Choice;
import com.worch.model.enums.ChoiceStatus;
import com.worch.repository.ChoiceRepository;
import com.worch.service.ChoicesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса чойсов")
@ExtendWith(MockitoExtension.class)
class ChoicesServiceTest {

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private ChoiceMapper choiceMapper;

    @InjectMocks
    private ChoicesService choicesService;


    private Choice choice1;
    private Choice choice2;

    private ChoicesResponseDto dto1;
    private ChoicesResponseDto dto2;

    private static final UUID DTO_FIRST_CREATOR_ID = UUID.fromString("22bf6136-bb4a-4983-b3c2-1881fc6a400a");

    @BeforeEach
    void setUp(){
        choice1 = new Choice(
                null,
                DTO_FIRST_CREATOR_ID,
                null,
                "Title first",
                "Description first",
                true,
                ChoiceStatus.ACTIVE,
                ZonedDateTime.now().plusDays(7),
                ZonedDateTime.now()
        );
        choice2 = new Choice(
                null,
                null,
                null,
                "Title first",
                "Description first",
                true,
                ChoiceStatus.ACTIVE,
                ZonedDateTime.now().plusDays(7),
                ZonedDateTime.now()

        );
        dto1 = new ChoicesResponseDto(
                null,
                DTO_FIRST_CREATOR_ID,
                null,
                "Title first",
                "Description first",
                true,
                ChoiceStatus.ACTIVE,
                ZonedDateTime.now().plusDays(7),
                ZonedDateTime.now()

        );
        dto2 = new ChoicesResponseDto(
                null,
                null,
                null,
                "Title first",
                "Description first",
                true,
                ChoiceStatus.ACTIVE,
                ZonedDateTime.now().plusDays(7),
                ZonedDateTime.now()

        );
    }


    @Test
    @DisplayName("Тест должен вернуть чойсы, если creator_id не указан")
    void getAllChoices_shouldSelectChoiceWithoutCreatorId() {
        // arrange
        List<Choice> choices = List.of(choice1,choice2);

        when(choiceRepository.findAll(any(Specification.class))).thenReturn(choices);
        when(choiceMapper.toDto(choice1)).thenReturn(dto1);
        when(choiceMapper.toDto(choice2)).thenReturn(dto2);

        // act
        List<ChoicesResponseDto> choicesResponseDtosResult = choicesService.getAllChoices(null);

        // assert
        assertThat(choicesResponseDtosResult).isNotNull();
        assertThat(choicesResponseDtosResult.size()).isEqualTo(2);
        assertThat(choicesResponseDtosResult.get(1).creatorId()).isNotEqualTo(DTO_FIRST_CREATOR_ID);
    }

    @Test
    @DisplayName("Тест должен вернуть чойсы по указанному creator_id")
    void getAllChoices_shouldSelectChoiceWithCreatorId() {
        // arrange
        List<Choice> choices = List.of(choice1);

        when(choiceRepository.findAll(any(Specification.class))).thenReturn(choices);
        when(choiceMapper.toDto(choice1)).thenReturn(dto1);

        // act
        List<ChoicesResponseDto> choicesResponseDtosResult = choicesService.getAllChoices(DTO_FIRST_CREATOR_ID);

        // assert
        assertThat(choicesResponseDtosResult).isNotNull();
        assertThat(choicesResponseDtosResult.size()).isEqualTo(1);
        assertThat(choicesResponseDtosResult.get(0).creatorId()).isEqualTo(DTO_FIRST_CREATOR_ID);
    }
}