package org.demo.dao;

import org.demo.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentDao {

    public Student save(Student student);

    public List<Student> findStudentByName(String name);

    public List<Student> findStudentByNameAndGender(String name, String gender);

    public List<Student> findStudentByNameInDescendingAgeOrder(String name);

    public int findMaxAge();

    public Map<String, Integer> findMaxAgeByName();

}
