package org.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by tomek on 15.09.17.
 */
@Getter @Setter
@NoArgsConstructor
@ApiModel(description = "Student dto")
public class StudentDTO {

    private long id;
    @ApiModelProperty(name = "name", notes = "name of student")
    private String name;
    private String gender;
    private Integer age;
}
