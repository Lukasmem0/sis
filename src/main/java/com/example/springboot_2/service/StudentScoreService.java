package com.example.springboot_2.service;

import com.example.springboot_2.entity.Student;
import com.example.springboot_2.entity.StudentScore;
import com.example.springboot_2.entity.Subject;
import com.example.springboot_2.repository.StudentRepository;
import com.example.springboot_2.repository.StudentScoreRepository;
import com.example.springboot_2.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class StudentScoreService {

    @Autowired
    private StudentScoreRepository scoreRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // thêm điểm
    public StudentScore save(Integer studentId, Integer subjectId, BigDecimal score1, BigDecimal score2) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));

        StudentScore score = new StudentScore();
        score.setStudent(student);
        score.setSubject(subject);
        score.setScore1(score1);
        score.setScore2(score2);

        return scoreRepository.save(score);
    }
    public BigDecimal calculateAvgScore(BigDecimal score1, BigDecimal score2) {
        BigDecimal weight1 = new BigDecimal("0.3");
        BigDecimal weight2 = new BigDecimal("0.7");

        BigDecimal avg = score1.multiply(weight1)
                .add(score2.multiply(weight2));

        // Làm tròn đến 2 chữ số thập phân
        return avg.setScale(2, RoundingMode.HALF_UP);
    }


    public String convertToGrade(BigDecimal avgScore) {
        if (avgScore.compareTo(new BigDecimal("8.0")) >= 0) {
            return "A";
        } else if (avgScore.compareTo(new BigDecimal("6.0")) >= 0) {
            return "B";
        } else if (avgScore.compareTo(new BigDecimal("4.0")) >= 0) {
            return "D";
        } else {
            return "F";
        }
    }


    public List<StudentScore> findAllScores() {
        return scoreRepository.findAll();
    }


    public List<StudentScore> findScoresByStudentId(Integer studentId) {
        return scoreRepository.findByStudent_StudentId(studentId);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}