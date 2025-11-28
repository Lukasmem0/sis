package com.example.springboot_2.service;

import com.example.springboot_2.entity.Student;
import com.example.springboot_2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    // --- Phương thức mới được thêm vào ---
    public void deleteById(Long id) {
        studentRepository.deleteById(Math.toIntExact(id));
    }
}