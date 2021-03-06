package com.examples.school.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import static java.util.Arrays.asList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.examples.school.model.Student;
import com.examples.school.repository.StudentRepository;
import com.examples.school.view.StudentView;

public class SchoolControllerTest {

	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private StudentView studentView;
	
	@InjectMocks
	private SchoolController schoolController;
	
	private AutoCloseable closeable;
	
	@Before
	public void setup() {
		closeable=MockitoAnnotations.openMocks(this);
	}
	@After
	public void releaseMocks()throws Exception{
		closeable.close();
	}
	
	@Test
	public void testAllStudents() {
		List<Student> students = asList(new Student());
		when(studentRepository.findAll())
			.thenReturn(students);
		schoolController.allStudents();
		verify(studentView)
			.showAllStudents(students);
	}
	
	@Test
	public void testNewStudentWhenStudentDoesNotAlreadyExist() {
		Student student = new Student("1", "test");
		when(studentRepository.findById("1")).
			thenReturn(null);
		schoolController.newStudent(student);
		InOrder inOrder = Mockito.inOrder(studentRepository, studentView);
		inOrder.verify(studentRepository).save(student);
		inOrder.verify(studentView).studentAdded(student);
	}
	
	@Test
	public void testNewStudentWhenStudentAlreadyExist() {
		Student studentToAdd = new Student("1", "test");
		Student existingStudent = new Student("1", "test");
		when(studentRepository.findById("1")).
			thenReturn(existingStudent);
		schoolController.newStudent(studentToAdd);
		verify(studentView).showError("Already existing student with id 1", existingStudent);
		verifyNoMoreInteractions(ignoreStubs(studentRepository));
	}
	
	@Test
	public void testDeleteStudentWhenStudentExists() {
		Student studentToDelete = new Student("1", "test");
		when(studentRepository.findById("1")).
			thenReturn(studentToDelete);
		schoolController.deleteStudent(studentToDelete);
		InOrder inOrder = Mockito.inOrder(studentRepository, studentView);
		inOrder.verify(studentRepository).delete("1");
		inOrder.verify(studentView).studentRemoved(studentToDelete);
	}

	@Test
	public void testDeleteStudentWhenStudentDoesNotExists() {
		Student studentToDelete = new Student("1", "test");
		when(studentRepository.findById("1")).
			thenReturn(null);
		schoolController.deleteStudent(studentToDelete);
		verify(studentView).showError("No existing student with id 1", studentToDelete);
		verifyNoMoreInteractions(ignoreStubs(studentRepository));
	}

}
