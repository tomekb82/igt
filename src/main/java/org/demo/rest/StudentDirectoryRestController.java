package org.demo.rest;

import io.swagger.annotations.*;
import org.demo.dto.StudentDTO;
import org.demo.entity.Student;
import org.demo.exception.MyResourceNotFoundException;
import org.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student")
@Api(value="students", description="Get students")
public class StudentDirectoryRestController {

    @Autowired
    private StudentService service;

    @ApiOperation(value = "Get students", nickname = "Get students (paging)")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Page number", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Number of elements", required = true, dataType = "int", paramType = "path")
    })*/
    @RequestMapping(value = "/get", params = { "page", "size" }, method = RequestMethod.GET, produces = "application/json")
    public Page<StudentDTO> findPaginated(@ApiParam(name = "page", value = "Page number", defaultValue = "0")@RequestParam("page") int page,
                                          @ApiParam(name = "size", value = "Number of elements", defaultValue = "5")@RequestParam("size") int size) {

        Page<StudentDTO> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        return resultPage;
    }

}
