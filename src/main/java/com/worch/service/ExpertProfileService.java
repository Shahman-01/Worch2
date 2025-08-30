package com.worch.service;

import com.worch.repository.ExpertProfileRepository;
import com.worch.model.entity.ExpertProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertProfileService {

    private final ExpertProfileRepository expertProfileRepository;

    public List<ExpertProfile> getAll() {
        return expertProfileRepository.findAll();
    }
}
