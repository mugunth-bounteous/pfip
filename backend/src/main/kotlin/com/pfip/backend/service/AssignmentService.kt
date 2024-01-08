package com.pfip.backend.service

import com.pfip.backend.dto.*
import com.pfip.backend.model.Assignment
import com.pfip.backend.repository.AssignmentRepository
import com.pfip.backend.repository.CourseRepository
import com.pfip.backend.repository.ParentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
// add method to find assignments to a particular student and his scores in a course
@Service
class AssignmentService(
    private val courseRepository: CourseRepository,
    private val assignmentRepository: AssignmentRepository,
    private val parentRepository: ParentRepository
){
    fun createAssignment(typeId: Int , courseId: Int , assignmentName: String , isAdmin: Boolean = false): ResponseEntity<Any?>{
        if(isAdmin){
            val assignment=Assignment()
            assignment.courseId = courseId
            assignment.assignmentName = assignmentName
            assignment.studentScores = mutableMapOf()
            assignmentRepository.save(assignment)
            return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Created an assignment"),HttpStatus.valueOf(200))
        }
        else{
            val tempCourseModel=courseRepository.fetchCourseById(courseId)
            if(tempCourseModel == null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Cannot find faculty"),HttpStatus.valueOf(404))
            }
            else{
                if(tempCourseModel.facultyId!!.equals(typeId)){
                    val assignment=Assignment()
                    assignment.courseId=courseId
                    assignment.assignmentName=assignmentName
                    assignment.studentScores= mutableMapOf()
                    assignmentRepository.save(assignment)
                    return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Created an assignment"),HttpStatus.valueOf(200))
                }
                else{
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Cannot find faculty"),HttpStatus.valueOf(404))
                }
            }
        }

    }

    fun addScoresToAnAssignment(typeId: Int , req: AddScoresToAssignmentDto , isAdmin: Boolean = false): ResponseEntity<Any?>{
        if(isAdmin){
            val assignment=assignmentRepository.findByIdOrNull(req.assignmentId.toString())
            if(assignment!=null){
                req.ScoresList.forEach { s ->
                    run {
                        assignment.AddStudentScore(index = s.studentId, value = s.score)
                    }
                }
                assignmentRepository.save(assignment)
                return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Added scores to the assignment"),HttpStatus.valueOf(200))
            }
            return ResponseEntity<Any?>(ResponseMessage("FAILED","Assignment not found!!"),HttpStatus.valueOf(404))
        }
        else{
            val tempCourseModel=courseRepository.fetchCourseById(req.courseId)
            if(tempCourseModel==null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Cannot find faculty"),HttpStatus.valueOf(404))
            }
            else{
                if(tempCourseModel.facultyId!!.equals(typeId)){
                    val assignment=assignmentRepository.findByIdOrNull(req.assignmentId.toString())
                    if(assignment!=null){
                        req.ScoresList.forEach { s ->
                            run {
                                assignment.AddStudentScore(index = s.studentId, value = s.score)
                            }
                        }
                        assignmentRepository.save(assignment)
                        return ResponseEntity<Any?>(ResponseMessage("SUCCESS","Added scores to the assignment"),HttpStatus.valueOf(200))
                    }
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Assignment not found!!"),HttpStatus.valueOf(404))
                }
                else{
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Faculy not assigned to course"),HttpStatus.valueOf(400))
                }
            }
        }
    }

    fun studentScoresOfAnAssignment(typeId: Int, req: AssignmentStudentScore, accountType: AccountType):ResponseEntity<Any?>{
        val assignmentData=assignmentRepository.findByIdOrNull(req.assignmentId.toString())
        if(accountType!==AccountType.ADMIN){
            val courseData=courseRepository.fetchCourseById(req.courseId)

            if(courseData==null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Course not found"),HttpStatus.valueOf(400))
            }
            else {
                if(assignmentData==null){
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Assignment not found"),HttpStatus.valueOf(400))
                }
                else{
                    if(assignmentData.GetStudentScore(req.studentId).isNaN()){
                        return ResponseEntity<Any?>(ResponseMessage("FAILED","Student not found"),HttpStatus.valueOf(400))
                    }
                }
            }
        }
        return ResponseEntity<Any?>(ResponseMessage("SUCCESS",assignmentData!!.studentScores!!.get(req.studentId)!!),HttpStatus.valueOf(200))
    }

    fun findAssignmentsByCourse(typeId: Int, req: CourseDto, accountType: AccountType):ResponseEntity<Any?>{
        val assignmentData=assignmentRepository.fetchAllByCourseId(req.courseId)
        if(accountType!==AccountType.ADMIN){
            val courseData=courseRepository.fetchCourseById(req.courseId)
            if(courseData==null){
                return ResponseEntity<Any?>(ResponseMessage("FAILED","Course not found"),HttpStatus.valueOf(400))
            }
            else {
                if(assignmentData==null){
                    return ResponseEntity<Any?>(ResponseMessage("FAILED","Assignment not found"),HttpStatus.valueOf(400))
                }
            }
        }
        return ResponseEntity<Any?>(ResponseMessage("SUCCESS",assignmentData!!), HttpStatus.valueOf(200))

    }

    fun findAssignmentByStudentId(typeId: Int,req:CourseAndStudent,accountType: AccountType):ResponseEntity<Any?>{
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

            val assignments=assignmentRepository.fetchAllByCourseId(req.courseId)
            if(assignments==null){
                return ResponseEntity<Any?>( ResponseMessage("FAILED", "No Assignments in course!"), HttpStatus.valueOf(400))
            }

            val ret = assignments.map { assignment ->{
                TestScore(testId = assignment!!.id!!, score = assignment.GetStudentScore(req.studentId))
            } }
            return ResponseEntity<Any?>(ResponseMessage("SUCCESS",ret),HttpStatus.valueOf(200))
        }

    }



}







