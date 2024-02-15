package com.alijas.gimhaeswimsocket.modules.competition.service;

import com.alijas.gimhaeswimsocket.modules.competition.entity.Department;
import com.alijas.gimhaeswimsocket.modules.competition.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}

