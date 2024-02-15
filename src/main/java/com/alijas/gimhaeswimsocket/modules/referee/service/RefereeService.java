package com.alijas.gimhaeswimsocket.modules.referee.service;

import com.alijas.gimhaeswimsocket.modules.referee.entity.Referee;
import com.alijas.gimhaeswimsocket.modules.referee.repository.RefereeRepository;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;
    private final UserRepository userRepository;

    public RefereeService(RefereeRepository refereeRepository, UserRepository userRepository) {
        this.refereeRepository = refereeRepository;
        this.userRepository = userRepository;
    }

    public List<Referee> getRefereeList() {
        return refereeRepository.findAll();
    }


    public Optional<Referee> getReferee(Long id) {
        return refereeRepository.findById(id);
    }

    @Transactional
    public void delete(Referee referee) {
        refereeRepository.delete(referee);
    }

}
