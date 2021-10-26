package com.examples.school.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.awaitility.Awaitility.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

public class SchoolControllerRaceConditionTest {
	

	@Mock
	 private StudentRepository studentRepository;
	
	 @Mock
	 private StudentView studentView;
	 
	 @InjectMocks
	 private SchoolController schoolController;
	
	 private AutoCloseable closeable;
	
	 @Before
	 public void setUp() throws Exception {
	 closeable = MockitoAnnotations.openMocks(this);
	 }
	
	 @After
	 public void releaseMocks()throws Exception{
			closeable.close();
		}
	
	 @Test
	 public void testNewStudentConcurrent() {
	 List<Student> students = new ArrayList<>();
	 Student student = new Student("1", "name");
	 // stub the StudentRepository
	 when(studentRepository.findById(anyString()))
	 .thenAnswer(invocation -> students.stream()
	 .findFirst().orElse(null));
	 doAnswer(invocation -> {
	 students.add(student);
	 return null;
	 }).when(studentRepository).save(any(Student.class));
	 // start the threads calling newStudent concurrently
	 List<Thread> threads = IntStream.range(0, 10)	
	 .mapToObj(i -> new Thread(() -> schoolController.newStudent(student)))
	 .peek(t -> t.start())
	 .collect(Collectors.toList());
	 // wait for all the threads to finish
	 await().atMost(10,TimeUnit.SECONDS)
	 .until(() -> threads.stream().noneMatch(t -> t.isAlive()));
	 // there should be a single element in the list
	 assertThat(students)
	 .containsExactly(student);
	 }
	
	 }

