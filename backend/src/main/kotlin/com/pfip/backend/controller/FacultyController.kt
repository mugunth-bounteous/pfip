package com.pfip.backend.controller

import com.pfip.backend.dto.CreateCourseDto
import com.pfip.backend.dto.CreateFaculty
import com.pfip.backend.dto.FacultyCourse
import com.pfip.backend.service.CourseService
import com.pfip.backend.service.FacultyService
import com.pfip.backend.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin(origins = ["http://localhost:3000"])
class FacultyController(
    private val tokenService: TokenService,
    private val facultyService: FacultyService
) {
    @PostMapping("/createFaculty")
    fun createFaculty(@RequestBody req: CreateFaculty, @RequestHeader(name = "Authorization") authorizationHeader: String) :ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type = data.type

        return facultyService.createFaculty(req , type)
    }

    @PostMapping("/addCourseToFaculty")
    fun addCourseToFaculty(@RequestBody req: FacultyCourse, @RequestHeader(name = "Authorization") authorizationHeader: String) :ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type = data.type
        return facultyService.addCourseToFaculty(req, type)
    }
    @PostMapping("/removeCourseFromFaculty")
    fun removeCourseFromFaculty(@RequestBody req: FacultyCourse, @RequestHeader(name = "Authorization") authorizationHeader: String) :ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type = data.type
        return facultyService.removeCourseFromFaculty(req, type)
    }
}