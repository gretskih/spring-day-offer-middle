package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.exception.RepositoryException;
import com.onedayoffer.taskdistribution.exception.ServiceException;
import com.onedayoffer.taskdistribution.repositories.EmployeeRepository;
import com.onedayoffer.taskdistribution.repositories.TaskRepository;
import com.onedayoffer.taskdistribution.repositories.entities.Employee;
import com.onedayoffer.taskdistribution.repositories.entities.Task;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDTO> getEmployees(@Nullable String sortDirection) throws ServiceException {
        List<Employee> employees;
        try {
            if ("DESC".equals(sortDirection)) {
                employees = employeeRepository.findAllAndSort(Sort.by(Sort.Direction.DESC, "fio"));
            } else {
                employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "fio"));
            }
        } catch (Exception e) {
            throw new ServiceException("implement getEmployees", e);
        }
        Type listType = new TypeToken<List<EmployeeDTO>>() {}.getType();
        return modelMapper.map(employees, listType);
    }

    @Transactional
    public EmployeeDTO getOneEmployee(Integer id) throws ServiceException {
        try {
            var employeeOptional = employeeRepository.findById(id);
            return employeeOptional
                    .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                    .orElseThrow(() -> new RepositoryException(String.format("Employee id=%s not found", id)));
        } catch (Exception e) {
        throw new ServiceException("implement getOneEmployee", e);
        }
    }

    public List<TaskDTO> getTasksByEmployeeId(Integer id) throws ServiceException {
        try {
            var taskList = taskRepository.findTaskListByEmployeeId(id);
            Type listType = new TypeToken<List<TaskDTO>>() {}.getType();
            return modelMapper.map(taskList, listType);
        } catch (Exception e) {
            throw new ServiceException("implement getTasksByEmployeeId", e);
        }
    }

    @Transactional
    public void changeTaskStatus(Integer taskId, TaskStatus status) throws ServiceException {
        try {
            taskRepository.changeTaskStatusById(taskId, status);
        } catch (Exception e) {
            throw new ServiceException("implement changeTaskStatus", e);
        }
    }

    @Transactional
    public void postNewTask(Integer employeeId, TaskDTO newTaskDTO) throws ServiceException {
        try {
            Task newTask = modelMapper.map(newTaskDTO, Task.class);
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RepositoryException(String.format("Employee id=%s not found", employeeId)));
            newTask.setEmployee(employee);
            taskRepository.save(newTask);
        } catch (Exception e) {
            throw new ServiceException("implement postNewTask", e);
        }
    }

    /*
            Task task = modelMapper.map(newTask, Task.class);
        Employee employee = employeeRepository.findById(employeeId)
                                              .orElseThrow(() -> new NotFoundException(
                                                      String.format("Employee '%s' not found", employeeId)));
        task.setEmployee(employee);
        taskRepository.save(task);
     */
}
