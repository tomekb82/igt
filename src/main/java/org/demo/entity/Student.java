package org.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private Integer age;

    public Student(long id, String name, String gender, Integer age) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Student(String name, String gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
