package com.bookfit.www.map.service.main.impl;

import com.bookfit.www.map.db.entity.User;
import com.bookfit.www.map.db.repo.UserRepository;
import com.bookfit.www.map.service.main.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MapService {
    private final UserRepository userRepository;

    public List<User> getMain() {
        return userRepository.findAll();
    }
}
