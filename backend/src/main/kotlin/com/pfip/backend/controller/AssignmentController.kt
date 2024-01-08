package com.pfip.backend.controller

import com.pfip.backend.dto.*
import com.pfip.backend.service.AssignmentService
import com.pfip.backend.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/assignment")
@CrossOrigin(origins = ["http://localhost:3000"])
class AssignmentController(
    private val tokenService: TokenService,
    private val assignmentService: AssignmentService
) {

    // add method to add an assignment
    // add method to add assignment scores
    // add method to find assignments to a particular student and his scores in a course
    // add method to find assignment wrt course

    @PostMapping("/add-assignment")
    fun AddAssignment(@RequestHeader(name = "Authorization") authorizationHeader: String,@RequestBody req:CreateAssignmentDto): ResponseEntity<Any?>{
        // the course id and assignment name will be given from frontend
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type = data.type
        val typeId = data.typeId
        return assignmentService.createAssignment(typeId= typeId, courseId = req.courseId, assignmentName = req.assignmentName, isAdmin = type==AccountType.ADMIN )
    }

    @PostMapping("/add-assignment-score")
    fun AddAssignmentScore(@RequestHeader(name = "Authorization") authorizationHeader: String,@RequestBody req: AddScoresToAssignmentDto): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return assignmentService.addScoresToAnAssignment(typeId,req, isAdmin = type==AccountType.ADMIN)
    }

    @PostMapping("/find-student-assignment")
    fun findAssignmentByStudent(@RequestHeader(name = "Authorization") authorizationHeader: String ,@RequestBody req: CourseAndStudent): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return assignmentService.findAssignmentByStudentId(typeId,req, type)
    }

    @PostMapping("/assignment-score")
    fun studentAssignmentScore(@RequestHeader(name = "Authorization") authorizationHeader: String ,@RequestBody req: AssignmentStudentScore): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type = data.type
        val typeId = data.typeId
        return assignmentService.studentScoresOfAnAssignment(typeId,req, type)
    }

    @PostMapping("/assignment-of-course")
    fun findAssignmentByCourse(@RequestHeader(name = "Authorization") authorizationHeader: String ,@RequestBody req: CourseDto): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return assignmentService.findAssignmentsByCourse(typeId,req,type)
    }

}