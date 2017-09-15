package org.demo.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.demo.entity.QStudent;
import org.demo.entity.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDaoImpl implements StudentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Student save(final Student student) {
        System.out.println(student);
        em.persist(student);
        return student;
    }

    @Override
    public List<Student> findStudentByName(final String name) {
        final JPAQuery<Student> query = new JPAQuery<>(em);
        final QStudent student = QStudent.student;

        return query
                .from(student)
                .where(student.name.eq(name))
                .fetch();
    }

    @Override
    public List<Student> findStudentByNameAndGender(final String name, final String gender) {
        final JPAQuery<Student> query = new JPAQuery<>(em);
        final QStudent student = QStudent.student;

        return query
                .from(student)
                .where(student.name.eq(name)
                        .and(student.gender.eq(gender)))
                .fetch();
    }

    @Override
    public List<Student> findStudentByNameInDescendingAgeOrder(final String name) {
        final JPAQuery<Student> query = new JPAQuery<>(em);
        final QStudent student = QStudent.student;

        return query
                .from(student)
                .where(student.name.eq(name))
                .orderBy(student.age.desc())
                .fetch();
    }

    @Override
    public int findMaxAge() {
        final JPAQuery<Student> query = new JPAQuery<>(em);
        final QStudent student = QStudent.student;

        return query
                .from(student)
                .select(student.age.max())
                .fetchFirst();
    }

    @Override
    public Map<String, Integer> findMaxAgeByName() {
        final JPAQuery<Student> query = new JPAQuery<>(em);
        final QStudent student = QStudent.student;

        return query
                .from(student)
                .transform(GroupBy.groupBy(student.name).as(GroupBy.max(student.age)));
    }

}
