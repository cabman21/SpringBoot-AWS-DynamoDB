package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Student;
import com.springboot.repository.DynamoDbRepository;

@RestController
@RequestMapping("/students")
public class DynamoDbController {

	@Autowired
	private DynamoDbRepository repository;

	@GetMapping
	public ResponseEntity<List<Student>> getStudents(@RequestParam String lastName) {
		List<Student> students = repository.getStudents(lastName);
		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}

	@GetMapping(value = "/{studentId}")
	public ResponseEntity<Student> getStudent(@PathVariable("studentId") String studentId) {
		Student student = repository.getStudent(studentId);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> insertStudent(@RequestBody Student student) {
		repository.insertStudent(student);
		return new ResponseEntity<Object>("Successfully inserted into DynamoDB table", HttpStatus.OK);
	}

	@PutMapping(value = "/{studentId}")
	public ResponseEntity<Object> updateStudent(@PathVariable("studentId") String studentId,
			@RequestBody Student student) {
		student.setStudentId(studentId);
		repository.updateStudent(student);
		return new ResponseEntity<Object>("Successfully updated into DynamoDB table", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{studentId}")
	public ResponseEntity<Object> deleteStudentDetails(@PathVariable("studentId") String studentId) {
		Student student = new Student();
		student.setStudentId(studentId);
		repository.deleteStudent(student);
		return new ResponseEntity<Object>("Successfully deleted into DynamoDB table", HttpStatus.OK);
	}
}