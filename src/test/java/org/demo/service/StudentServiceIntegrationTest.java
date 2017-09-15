package org.demo.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.demo.Application;
import org.demo.dao.StudentDao;
import org.demo.entity.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles(value="TEST")
public class StudentServiceIntegrationTest {

    private static final String ENDPOINT = "http://localhost:8080/student/get";

    @Autowired
    private StudentDao studentDao;

    @Test
    @Transactional
    public void givenExistingStudents_whenFindingPersonByFirstName_thenFound() {
        studentDao.save(new Student("Erich", "Male", 30));
        Student student = new Student("Kent", "Male", 23);
        studentDao.save(student);
        studentDao.save(new Student("Ralph", "Female", 23));

        Student personFromDb =  studentDao.findStudentByName("Kent").get(0);
        Assert.assertEquals(student.getId(), personFromDb.getId());
    }

    @Test
    @Transactional
    public void givenExistingPersons_whenFindingMaxAgeByName_thenFound() {
        studentDao.save(new Student("Kent", "Male", 20));
        studentDao.save(new Student("Ralph", "Male", 35));
        studentDao.save(new Student("Kent", "Male", 30));

        Map<String, Integer> maxAge = studentDao.findMaxAgeByName();
        Assert.assertTrue(maxAge.size() == 2);
        Assert.assertSame(35, maxAge.get("Ralph"));
        Assert.assertSame(30, maxAge.get("Kent"));
    }

    @Test
    @Transactional
    public void givenExistingPersons_whenDescendingAge_thenFound() {
        studentDao.save(new Student("Kent", "Male", 20));
        studentDao.save(new Student("Ralph", "Male", 35));
        studentDao.save(new Student("Kent", "Male", 30));

        List<Student> students = studentDao.findStudentByNameInDescendingAgeOrder("Kent");
        Assert.assertTrue(students.size() == 2);
        Assert.assertSame(30, students.get(0).getAge());
        Assert.assertSame(20, students.get(1).getAge());
    }

    @Test
    public void givenRequestForStudents_whenPageIsOne_expectContainsNames() {
        given().params("page", "0", "size", "2").get(ENDPOINT).then().assertThat()
          .body("content.name", hasItems("Bryan", "Ben"));
    }

    @Test
    public void givenRequestForStudents_whenSizeIsTwo_expectTwoItems() {
        given().params("page", "0", "size", "2").get(ENDPOINT).then().assertThat().body("size", equalTo(2));
    }

    @Test
    public void givenRequestForStudents_whenSizeIsTwo_expectNumberOfElementsTwo() {
        given().params("page", "0", "size", "2").get(ENDPOINT).then().assertThat().body("numberOfElements", equalTo(2));
    }

    @Test
    public void givenRequestForStudents_whenResourcesAreRetrievedPaged_thenExpect200() {
        given().params("page", "0", "size", "2").get(ENDPOINT).then().statusCode(200);
    }

    @Test
    public void givenRequestForStudents_whenPageOfResourcesAreRetrievedOutOfBounds_thenExpect500() {
        given().params("page", "1000", "size", "2").get(ENDPOINT).then().statusCode(500);
    }

    @Test
    public void givenRequestForStudents_whenPageNotValid_thenExpect500() {
        given().params("page", RandomStringUtils.randomNumeric(5), "size", "2").get(ENDPOINT).then().statusCode(500);
    }

    @Test
    public void givenRequestForStudents_whenPageSizeIsFive_expectFiveItems() {
        given().params("page", "0", "size", "5").get(ENDPOINT).then().body("content.size()", is(5));
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        given().params("page", "0", "size", "2").get(ENDPOINT).then().assertThat().body("first", equalTo(true));
    }
}
