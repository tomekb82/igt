package org.demo.service;

import org.demo.dao.StudentRepository;
import org.demo.dto.StudentDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository dao;

    @Autowired
    private DozerBeanMapper mapper;

    @Override
    public Page<StudentDTO> findPaginated(int page, int size) {
        return dao
                .findAll(new PageRequest(page, size))
                .map(e -> mapper.map(e, StudentDTO.class));
    }

}
