package com.worch.tests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.worch.model.entity.Stack;
import com.worch.repository.StackRepository;
import com.worch.service.StackService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
public class StackServiceTest {

  @Mock
  private StackRepository stackRepository;

  @InjectMocks
  private StackService stackService;

  @Test
  void getAllStacks_ShouldReturnPagedResult() {
    Pageable pageable = PageRequest.of(0, 5);
    List<Stack> stacks = List.of(
        new Stack(), new Stack(), new Stack()
    );
    Page<Stack> expectedPage = new PageImpl<>(stacks, pageable, stacks.size());

    when(stackRepository.findAll(pageable)).thenReturn(expectedPage);

    Page<Stack> result = stackService.getAllStacks(pageable);

    assertNotNull(result);
    assertEquals(3, result.getContent().size());
    verify(stackRepository, times(1)).findAll(pageable);
  }
}