package org.demo.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Page number", required = true, dataType = "int", paramType = "request"),
            @ApiImplicitParam(name = "size", value = "Number of elements", required = true, dataType = "int", paramType = "request")
    })
    @RequestMapping(value = "/get", params = { "page", "size" }, method = RequestMethod.GET, produces = "application/json")
    public Page<StudentDTO> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {

        Page<StudentDTO> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        return resultPage;
    }

}
