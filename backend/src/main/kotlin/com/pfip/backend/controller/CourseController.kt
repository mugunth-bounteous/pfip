package com.pfip.backend.controller

import com.pfip.backend.dto.CreateCourseDto
import com.pfip.backend.dto.StudentDto
import com.pfip.backend.service.CourseService
import com.pfip.backend.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = ["http://localhost:3000"])
class CourseController(
    private val tokenService: TokenService,
    private val courseService: CourseService
) {

    @PostMapping("/createCourse")
    fun createCourse(@RequestBody req:CreateCourseDto, @RequestHeader(name = "Authorization") authorizationHeader: String): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return courseService.createCourse(typeId = typeId,req = req ,type = type)
    }

    @GetMapping("/getCourseByStudent")
    fun getCoursesByStudentId(@RequestBody req:StudentDto, @RequestHeader(name = "Authorization") authorizationHeader: String): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return courseService.findCoursesByStudentId(typeId = typeId, studentDto = req)
    }
    @GetMapping("/getCourseByFaculty")
    fun getCoursesByFaculty(@RequestHeader(name = "Authorization") authorizationHeader: String): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val typeId= data!!.typeId
        return courseService.findCoursesByFacultyId(typeId = typeId)
    }

}