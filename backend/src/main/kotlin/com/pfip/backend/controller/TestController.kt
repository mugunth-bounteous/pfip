package com.pfip.backend.controller

import com.pfip.backend.dto.*
import com.pfip.backend.service.TestService
import com.pfip.backend.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/test")
@CrossOrigin(origins = ["http://localhost:3000"])
class TestController(
    private val tokenService: TokenService,
    private val testService: TestService
) {
    @PostMapping("/createTest")
    fun CreateTest(@RequestHeader(name = "Authorization") authorizationHeader: String,@RequestBody req: CreateTestDto): ResponseEntity<Any?>?{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return testService.createTest(typeId, req.courseId, req.testName, data.type==AccountType.ADMIN)
    }

    @PostMapping("/addTestScores")
    fun AddTestScores(@RequestHeader(name = "Authorization") authorizationHeader: String, @RequestBody req: AddScoresToTestDto): ResponseEntity<Any?>?{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return testService.addTestScores(typeId, req, isAdmin = type==AccountType.ADMIN)
    }

    @PostMapping("/courseTests")
    fun getTestsByCourseId(@RequestHeader(name = "Authorization") authorizationHeader: String, @RequestBody req: CourseDto): ResponseEntity<Any?>?{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return testService.findTestsByCourseId(typeId, req, type)
    }
    @PostMapping("/studentTestScores")
    fun getStudentScoresOfATest(@RequestHeader(name = "Authorization") authorizationHeader: String, @RequestBody req: TestStudentScore): ResponseEntity<Any?>?{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return testService.studentScoresOfATest(typeId ,req ,type)
    }

    @PostMapping("/getStudentTest")
    fun getTestsByStudentId(@RequestHeader(name = "Authorization") authorizationHeader: String, @RequestBody req: CourseAndStudent): ResponseEntity<Any?>?{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return testService.findTestsByStudentId(typeId, req, type)
    }
}