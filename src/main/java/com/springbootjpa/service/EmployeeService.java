package com.springbootjpa.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootjpa.exception.RecordNotFoundException;
import com.springbootjpa.model.EmployeeEntity;
import com.springbootjpa.repository.EmployeeRepository;
 
@Service
public class EmployeeService {
     
    @Autowired
    EmployeeRepository repository;
     
    public List<EmployeeEntity> getAllEmployees()
    {
        List<EmployeeEntity> employeeList = repository.findAll();
         
        if(employeeList.size() > 0) {
            return employeeList;
        } else {
            return new ArrayList<EmployeeEntity>();
        }
    }
     
    public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException
    {
        Optional<EmployeeEntity> employee = repository.findById(id);
         
        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
     
    public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity) throws RecordNotFoundException
    {

        Optional<EmployeeEntity> employee;
        if (entity.getId() == null) {
            employee = Optional.of(new EmployeeEntity());
        } else {
            employee = repository.findById(entity.getId());
        }


        EmployeeEntity ent = employee.orElseThrow(() -> new RecordNotFoundException(
                MessageFormat.format("EmployeeEntity with id {0} does not exist", entity.getId())));
        ent.setEmail(entity.getEmail());
        ent.setFirstName(entity.getFirstName());
        ent.setLastName(entity.getLastName());

        ent = repository.save(ent);

        return ent;
    }
     
    public void deleteEmployeeById(Long id) throws RecordNotFoundException
    {
        Optional<EmployeeEntity> employee = repository.findById(id);
         
        if(employee.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}