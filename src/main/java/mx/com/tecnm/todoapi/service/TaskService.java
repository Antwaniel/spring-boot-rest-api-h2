package mx.com.tecnm.todoapi.service;

import mx.com.tecnm.todoapi.exceptions.ToDoExceptions;
import mx.com.tecnm.todoapi.mapper.TaskInDTOToTask;
import mx.com.tecnm.todoapi.persistence.entity.Task;
import mx.com.tecnm.todoapi.persistence.entity.TaskStatus;
import mx.com.tecnm.todoapi.persistence.repository.TaskRepository;
import mx.com.tecnm.todoapi.service.dto.TaskInDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskInDTOToTask mapper;

    public TaskService(TaskRepository repository, TaskInDTOToTask mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

//    Crete new task
    public Task createTask(TaskInDTO taskInDTO){
    Task task = mapper.map(taskInDTO);
    return    this.repository.save(task);
    }
    
//    List all task
    public List<Task> findAll(){
        return this.repository.findAll();
    }

    public List<Task> findAllByTaskStatus(TaskStatus status){
        return this.repository.findAllByTaskStatus(status);
    }

//  Mark task as finished
    @Transactional
    public void updateTaskAsFinished(Long id){
        Optional<Task> optionalTask =this.repository.findById(id);
        if(optionalTask.isEmpty()){
            throw new ToDoExceptions("task not found", HttpStatus.NOT_FOUND);
        }
        this.repository.markTaskAsFinished(id);
    }

//  Transaction for delete a task
    @Transactional
    public void deleteById(Long id){
        Optional<Task> optionalTask =this.repository.findById(id);
        if(optionalTask.isEmpty()){
            throw new ToDoExceptions("task not found", HttpStatus.NOT_FOUND);
        }
        this.repository.deleteById(id);
    }



}
