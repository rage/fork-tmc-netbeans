package fi.helsinki.cs.tmc.model;

import java.util.ArrayList;
import java.util.List;
import hy.tmc.core.domain.Course;
import fi.helsinki.cs.tmc.data.CourseListUtils;
import hy.tmc.core.domain.Exercise;
import fi.helsinki.cs.tmc.events.TmcEventBus;
import fi.helsinki.cs.tmc.events.TmcEventListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import java.util.logging.Level;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourseDbTest {
    
    private Level oldLogLevel;

    private TmcEventBus eventBus;
    private ConfigFile file;
    
    private CourseDb db;
    
    @Before
    public void setUp() {
        oldLogLevel = CourseDb.logger.getLevel();
        CourseDb.logger.setLevel(Level.OFF);

        eventBus = TmcEventBus.createNewInstance();
        file = new ConfigFile("CourseDbTest.json");
        db = new CourseDb(eventBus, file);
    }
    
    @After
    public void tearDown() throws IOException {
        file.getFileObject().delete();
        CourseDb.logger.setLevel(oldLogLevel);
    }
    
    @Test
    public void itShouldPersitItsCourseList() throws IOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("one"));
        courses.add(new Course("two"));
        courses.get(0).getExercises().add(new Exercise("ex1"));
        
        db.setAvailableCourses(courses);
        db = new CourseDb(eventBus, file);
        
        assertEquals("one", db.getAvailableCourses().get(0).getName());
        assertEquals("ex1", db.getAvailableCourses().get(0).getExercises().get(0).getName());
    }
    
    @Test
    public void itShouldPersistTheCurrentCourse() throws IOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("one"));
        courses.add(new Course("two"));
        
        db.setAvailableCourses(courses);
        db.setCurrentCourseName("one");
        db = new CourseDb(eventBus, file);
        
        assertEquals("one", db.getCurrentCourse().getName());
        assertSame(db.getAvailableCourses().get(0), db.getCurrentCourse());
    }
    
    @Test
    public void itShouldPersistTheChecksumsOfDownloadedVersionsOfCourses() throws IOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("course1"));
        Exercise ex = new Exercise("ex1", "course1");
        courses.get(0).getExercises().add(ex);
        ex.setChecksum("foo");
        
        db.setAvailableCourses(courses);
        db.exerciseDownloaded(ex);
        db = new CourseDb(eventBus, file);
        
        assertEquals("foo", db.getDownloadedExerciseChecksum(ex.getKey()));
    }
    
    @Test
    public void itShouldBeEmptyWhenFailingToLoadTheFile() throws IOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("one"));
        courses.add(new Course("two"));
        db.setAvailableCourses(courses);
        
        file.writeContents("oops!");
        
        db = new CourseDb(eventBus, file);
        assertTrue(db.getAvailableCourses().isEmpty());
    }
    
    @Test
    public void theCurrentCourseShouldHaveAnIdentityOfOneOfTheAvailableCourses() throws IOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("one"));
        courses.add(new Course("two"));
        db.setAvailableCourses(courses);
        db.setCurrentCourseName("two");
        
        db = new CourseDb(eventBus, file);
        assertSame("current course has the wrong object identity", db.getAvailableCourses().get(1), db.getCurrentCourse());
    }
    
    @Test
    public void itCanConvenientlyReturnTheExercisesFromTheCurrentCourse() {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("one"));
        courses.add(new Course("two"));
        db.setAvailableCourses(courses);
        
        assertNull(db.getCurrentCourse());
        assertTrue(db.getCurrentCourseExercises().isEmpty());
        
        db.setCurrentCourseName("two");
        CourseListUtils.getCourseByName(courses, "two").getExercises().add(new Exercise("ex1"));
        assertEquals("ex1", db.getCurrentCourseExercises().get(0).getName());
    }
    
    @Test
    public void itShouldPostAnEventWhenChanged() {
        final AtomicInteger received = new AtomicInteger(0);
        eventBus.subscribeStrongly(new TmcEventListener() {
            public void receive(CourseDb.ChangedEvent ev) {
                received.incrementAndGet();
            }
        });
        
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("one"));
        courses.add(new Course("two"));
        db.setAvailableCourses(courses);
        db.setCurrentCourseName("one");
        
        assertEquals(2, received.get());
    }
}
