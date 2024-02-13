package com.pfip.backend.service

import com.pfip.backend.dto.*
import com.pfip.backend.model.Test
import com.pfip.backend.repository.CourseRepository
import com.pfip.backend.repository.ParentRepository
import com.pfip.backend.repository.TestRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
public class TestService(
    private val testRepository: TestRepository,
    private val courseRepository: CourseRepository,
    private val parentRepository: ParentRepository
) {
    fun createTest(typeId: Int, courseId: Int, testName: String, isAdmin: Boolean = false): ResponseEntity<Any?> {
        if(isAdmin){
            val test= Test()
            test.courseId = courseId
            test.TestName = testName
            test.studentScores = mutableMapOf()
            testRepository.save(test)
            return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Created an Test"), HttpStatus.valueOf(200))
        }
        else{
            val tempCourseModel=courseRepository.fetchCourseById(courseId)
            if(tempCourseModel == null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Cannot find faculty"), HttpStatus.valueOf(404))
            }
            else{
                if(tempCourseModel.facultyId!!.equals(typeId)){
                    val test= Test()
                    test.courseId = courseId
                    test.TestName = testName
                    test.studentScores = mutableMapOf()
                    testRepository.save(test)
                    return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Created an Test"), HttpStatus.valueOf(200))
                }
                else{
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Cannot find faculty"), HttpStatus.valueOf(404))
                }
            }
        }
    }

    fun addTestScores(typeId: Int, req: AddScoresToTestDto, isAdmin: Boolean = false): ResponseEntity<Any?>{
        if(isAdmin){
            val test=testRepository.findByIdOrNull(req.testId.toString())
            if(test!=null){
                req.ScoresList.forEach { s ->
                    run {
                        test.AddStudentScore(index = s.studentId, value = s.score)
                    }
                }
                testRepository.save(test)
                return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Added scores to the test"),HttpStatus.valueOf(200))
            }
            return ResponseEntity<Any?>(ResponseMessage("FAILED","Test not found!!"),HttpStatus.valueOf(404))
        }
        else{
            val tempCourseModel=courseRepository.fetchCourseById(req.courseId)
            if(tempCourseModel==null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Cannot find faculty"),HttpStatus.valueOf(404))
            }
            else{
                if(tempCourseModel.facultyId!!.equals(typeId)){
                    val test=testRepository.findByIdOrNull(req.testId.toString())
                    if(test!=null){
                        req.ScoresList.forEach { s ->
                            run {
                                test.AddStudentScore(index = s.studentId, value = s.score)
                            }
                        }
                        testRepository.save(test)
                        return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Added scores to the Test"),HttpStatus.valueOf(200))
                    }
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Test not found!!"),HttpStatus.valueOf(404))
                }
                else{
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Faculy not assigned to course"),HttpStatus.valueOf(400))
                }
            }
        }
    }

    fun studentScoresOfATest(typeId: Int, req: TestStudentScore, accountType: AccountType):ResponseEntity<Any?>{
        val testData=testRepository.findByIdOrNull(req.testId.toString())
        if(accountType!== AccountType.ADMIN){
            val courseData=courseRepository.fetchCourseById(req.courseId)

            if(courseData==null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Course not found"),HttpStatus.valueOf(400))
            }
            else {
                if(testData==null){
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Assignment not found"),HttpStatus.valueOf(400))
                }
                else{
                    if(testData.GetStudentScore(req.studentId).isNaN()){
                        return ResponseEntity<Any?>(ResponseMessage("FAILED","Student not found"),HttpStatus.valueOf(400))
                    }
                }
            }
        }
        return ResponseEntity<Any?>(ResponseMessage("SUCCESS",testData!!.studentScores!!.get(req.studentId)!!),HttpStatus.valueOf(200))
    }

    fun findTestsByCourseId(typeId: Int, req: CourseDto, accountType: AccountType):ResponseEntity<Any?>{
        val testData=testRepository.fetchAllTestsByCourseId(req.courseId)
        if(accountType==AccountType.FACULTY || accountType==AccountType.ADMIN) {
            if (accountType !== AccountType.ADMIN) {
                val courseData = courseRepository.fetchCourseById(req.courseId)
                if (courseData == null) {
                    return ResponseEntity<Any?>(ResponseMessage("FAILED", "Course not found"), HttpStatus.valueOf(400))
                } else {
                    if (testData == null) {
                        return ResponseEntity<Any?>(ResponseMessage("FAILED", "No tests in the course"), HttpStatus.valueOf(400))
                    }
                    if(courseData.facultyId!==typeId){
                        return ResponseEntity<Any?>(ResponseMessage("FAILED", "Faculty not assigned to the course"), HttpStatus.valueOf(400))
                    }
                }

            }
            return ResponseEntity<Any?>(ResponseMessage("SUCCESS", testData!!), HttpStatus.valueOf(200))
        }
        return ResponseEntity<Any?>( ResponseMessage("FAILED", "Restricted access!"), HttpStatus.valueOf(400))
        }

    fun findTestsByStudentId(typeId: Int,req:CourseAndStudent,accountType: AccountType):ResponseEntity<Any?>{
        if(accountType==AccountType.STUDENT){
            return ResponseEntity<Any?>( ResponseMessage("FAILED", "Restricted access!"), HttpStatus.valueOf(400))
        }
        else{
           if(accountType==AccountType.PARENT){
               val parent=parentRepository.findById(typeId.toString())
               if(parent.isEmpty()){
                   return ResponseEntity<Any?>( ResponseMessage("FAILED", "Parent not found!"), HttpStatus.valueOf(400))
               }
               if(parent.get().studentId !== req.studentId){
                   return ResponseEntity<Any?>( ResponseMessage("FAILED", "Parent not assigned to student!"), HttpStatus.valueOf(400))
               }
            }
            else if (accountType==AccountType.FACULTY){
                val courseData = courseRepository.fetchCourseById(req.courseId)
               if(courseData==null){
                   return ResponseEntity<Any?>( ResponseMessage("FAILED", "Course not found!"), HttpStatus.valueOf(400))
               }
               if(courseData.facultyId !== typeId){
                   return ResponseEntity<Any?>( ResponseMessage("FAILED", "Faculty not assigned to course!"), HttpStatus.valueOf(400))
               }
            }

            val tests=testRepository.fetchAllTestsByCourseId(req.courseId)
            if(tests==null){
                return ResponseEntity<Any?>( ResponseMessage("FAILED", "No tests in course!"), HttpStatus.valueOf(400))
            }

            val ret = tests.map { test->{
                TestScore(testId = test!!.id!!, score = test.GetStudentScore(req.studentId))
            } }
            return ResponseEntity<Any?>(ResponseMessage("SUCCESS",ret),HttpStatus.valueOf(200))
        }

    }


    }

