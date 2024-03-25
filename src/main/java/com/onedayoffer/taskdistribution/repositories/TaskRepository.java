package com.onedayoffer.taskdistribution.repositories;

import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.repositories.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findTaskListByEmployeeId(Integer employeeId);

    @Modifying
    @Query("update Task t set t.status = :status where t.id = :id")
    void changeTaskStatusById(Integer id, TaskStatus status);


}
