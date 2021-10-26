/* non fa
package com.examples.school.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.bson.Document;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.awaitility.Awaitility.*;
import com.examples.school.model.Student;
import com.examples.school.repository.StudentRepository;
import static com.examples.school.repository.mongo.StudentMongoRepository.SCHOOL_DB_NAME;
import com.examples.school.repository.mongo.StudentMongoRepository;
import com.examples.school.view.StudentView;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import static com.examples.school.repository.mongo.StudentMongoRepository.STUDENT_COLLECTION_NAME;

public class SchoolControllerRaceConditionIT {
	@Mock
	private StudentView studentView;

	private StudentRepository studentRepository;

	private AutoCloseable closeable;
	
	@InjectMocks
	private SchoolController schoolController;
	
	@Before
	public void setUp() {

		closeable = MockitoAnnotations.openMocks(this);
		MongoClient client = new MongoClient("localhost");
		MongoDatabase database = client.getDatabase(SCHOOL_DB_NAME);
		// make sure we always start with a clean database
		database.drop();
		MongoCollection<Document> studentCollection = database.getCollection(STUDENT_COLLECTION_NAME);
		// A unique index ensures that the indexed field
		// (in this case "id") does not store duplicate values:
		studentCollection.createIndex(Indexes.ascending("id"), new IndexOptions().unique(true));

		studentRepository = new StudentMongoRepository(client);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testNewStudentConcurrent() {
		Student student = new Student("1", "name");
		// start the threads calling newStudent concurrently
		// on different SchoolController instances, so 'synchronized'
		// methods in the controller will not help...
		List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> new Thread(() -> {
			try {
				new SchoolController(studentView, studentRepository).newStudent(student);
			} catch (MongoWriteException e) {
				e.printStackTrace();
			}
		})).peek(t -> t.start()).collect(Collectors.toList());
		// wait for all the threads to finish
		await().atMost(10, TimeUnit.SECONDS).until(() -> threads.stream().noneMatch(t -> t.isAlive()));
		// there should be a single element in the list
		assertThat(studentRepository.findAll()).containsExactly(student);
	}
}
*/