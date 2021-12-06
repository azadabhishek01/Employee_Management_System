package com.project.service;

import com.project.entity.Employee;
import com.project.repository.EmpRepo;
import com.sun.prism.shader.Solid_TextureRGB_AlphaTest_Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpService {

    @Autowired
    private EmpRepo repo;

    public void addEmp(Employee e) {

        repo.save(e);

    }

    public List<Employee> getAllEmp() {
        return repo.findAll();
    }

    public Employee getEmpById(int id) {
        Optional<Employee> e = repo.findById(id);
        return e.orElse(null);
    }

    public void delEmp(int id) {
        repo.deleteById(id);
    }

    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repo.findAll(pageable);
    }
}
