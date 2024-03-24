package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.repositories.EmployeeRepository;
import com.onedayoffer.taskdistribution.repositories.TaskRepository;
import com.onedayoffer.taskdistribution.repositories.entities.Employee;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDTO> getEmployees(@Nullable String sortDirection) {
        List<Employee> employees;
        if ("ASC".equals(sortDirection)) {
            employees = employeeRepository.findAllAndSort(Sort.by(Sort.Direction.ASC, "fio"));
        } else if ("DESC".equals(sortDirection)){
            employees = employeeRepository.findAllAndSort(Sort.by(Sort.Direction.DESC, "fio"));
        } else {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<EmployeeDTO>>() {}.getType();
        return modelMapper.map(employees, listType);
        /*
        return employees.stream().map(emp ->
                new EmployeeDTO(emp.getFio(),
                        emp.getJobTitle(),
                        emp.getTasks().stream().map(t -> TaskDTO.builder().id(t.getId()).name(t.getName())
                                .taskType(t.getTaskType()).status(t.getStatus()).priority(t.getPriority()).leadTime(t.getLeadTime()).build()).toList()
                )).toList();
        */


        //throw new java.lang.UnsupportedOperationException("implement getEmployees");

        // if sortDirection.isPresent() ..
        // Sort.Direction direction = ...
        // employees = employeeRepository.findAllAndSort(Sort.by(direction, "fio"))
        // employees = employeeRepository.findAll()
        // Type listType = new TypeToken<List<EmployeeDTO>>() {}.getType()
        // List<EmployeeDTO> employeeDTOS = modelMapper.map(employees, listType)
    }

    @Transactional
    public EmployeeDTO getOneEmployee(Integer id) {
        throw new java.lang.UnsupportedOperationException("implement getOneEmployee");
    }

    public List<TaskDTO> getTasksByEmployeeId(Integer id) {
        throw new java.lang.UnsupportedOperationException("implement getTasksByEmployeeId");
    }

    @Transactional
    public void changeTaskStatus(Integer taskId, TaskStatus status) {
        throw new java.lang.UnsupportedOperationException("implement changeTaskStatus");
    }

    @Transactional
    public void postNewTask(Integer employeeId, TaskDTO newTask) {
        throw new java.lang.UnsupportedOperationException("implement postNewTask");
    }
}
