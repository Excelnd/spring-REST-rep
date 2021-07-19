package com.ihs2code.springdm.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihs2code.springdm.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {

	private List<Student> theStudents;
	
	@PostConstruct
	public void loadData() {
		
		theStudents = new ArrayList<>();
		
		theStudents.add(new Student("Poornima", "Patel"));
		theStudents.add(new Student("Mario", "Rossi"));
		theStudents.add(new Student("Mary", "Sim"));
	}
	
	// define endpoint for "/students"  - return list of students
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		
		return theStudents; 
	}
	
	@GetMapping("/students/{studentId}")
	public Student getStudent(@PathVariable int studentId) {
		
		// index into the list
		
		// check the studentId against list size
		if ( (studentId >= theStudents.size()) || (studentId < 0)) {
			throw new StudentNotFoundException("Students id not found - " + studentId);
		}
		
		return theStudents.get(studentId);
	}
	
	// Add an exception handler using @ExceptionHandler
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
		
		// create a StudentErrorResponse
		
		StudentErrorResponse error = new StudentErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	// add to catch all exception
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
	
	// create a StudentErrorResponse
	
	StudentErrorResponse error = new StudentErrorResponse();
	
	error.setStatus(HttpStatus.NOT_FOUND.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());
	
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	
	}
	
}











