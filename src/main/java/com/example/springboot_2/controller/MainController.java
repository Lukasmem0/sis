package com.example.springboot_2.controller;

import com.example.springboot_2.entity.Student;
import com.example.springboot_2.entity.StudentScore;
import com.example.springboot_2.entity.Subject;
import com.example.springboot_2.repository.SubjectRepository;
import com.example.springboot_2.service.StudentScoreService;
import com.example.springboot_2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentScoreService scoreService;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/")
    public String scoreList(Model model) {
        List<StudentScore> scores = scoreService.findAllScores();
        model.addAttribute("scores", scores);
        model.addAttribute("scoreService", scoreService);
        return "score-list";
    }

    @GetMapping("/students")
    public String studentList(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "student-only-list"; // Trả về file HTML mới
    }
    @GetMapping("/student/add")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/student/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/score/add")
    public String showScoreForm(Model model) {
        List<Student> students = studentService.findAll();
        List<Subject> subjects = subjectRepository.findAll();

        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        return "score-form";
    }

    @PostMapping("/score/save")
    public String saveScore(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam("score1") BigDecimal score1,
            @RequestParam("score2") BigDecimal score2) {

        scoreService.save(studentId, subjectId, score1, score2);
        return "redirect:/";
    }
}