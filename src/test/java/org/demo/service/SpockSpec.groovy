package org.demo.service;


import org.demo.dto.StudentDTO;
import org.demo.entity.Student;
import org.dozer.DozerBeanMapper
import org.dozer.loader.api.BeanMappingBuilder
import spock.lang.Specification

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SpockSpec extends Specification {

    def "should return 1 from first element of list"() {
        given:
        List<Integer> list = new ArrayList<>()

        when:
        list.add(1)

        then:
        1 == list.get(0)
    }



    private Student student = Stub()

   /* BeanMappingBuilder builder = new BeanMappingBuilder() {

        @Override
        protected void configure() {
            mapping(Student.class, com.example.demo.dto.Album.class).fields("opis", "description");

        }
    };*/

    def "dozer mapper of album dto"() {
        given:
            configStudent name: "Rush", gender: "male"
            DozerBeanMapper mapper = Spy(DozerBeanMapper);
            //mapper.addMapping(builder);
        when:
            StudentDTO dest = mapper.map(student, StudentDTO.class);
        then:
            dest.getName() == "Rush"
            dest.getGender() == "male"
    }

    private configStudent(Map studentDetails) {
        student.getName() >> studentDetails['name']
        student.getGender() >> studentDetails['gender']
    }
}