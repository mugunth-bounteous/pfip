package com.pfip.backend.service

import com.pfip.backend.dto.*
import com.pfip.backend.model.Course
import com.pfip.backend.model.Faculty
import com.pfip.backend.repository.CourseRepository
import com.pfip.backend.repository.FacultyRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FacultyService (
    private val facultyRepository:FacultyRepository,
    private val courseRepository: CourseRepository
){
    fun createFaculty(req: CreateFaculty, type: AccountType) :ResponseEntity<Any?> {
        if(type==AccountType.ADMIN){
            var faculty = Faculty()
            faculty.firstName = req.firstName
            faculty.lastName = req.lastName
            faculty.listOfCourses = mutableListOf()
            var ret = facultyRepository.save(faculty)
            return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret), HttpStatus.valueOf(200))
        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Access restricted!"), HttpStatus.valueOf(400))
        }
    }

    fun addCourseToFaculty(req: FacultyCourse, type: AccountType ) :ResponseEntity<Any?>{
        if(type==AccountType.ADMIN){
            val course = courseRepository.fetchCourseById(req.courseId)
            if(course!==null){
                var facultyObj = facultyRepository.findById(req.facultyId.toString())
                if(facultyObj.isPresent){
                    var faculty = facultyObj.get()
                    faculty.appendToList(req.courseId)
                    val ret = facultyRepository.save(faculty)
                    return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret), HttpStatus.valueOf(200))
                }
                return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Faculty not found!"), HttpStatus.valueOf(400))
            }
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Invalid course"), HttpStatus.valueOf(400))
        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Access restricted!"), HttpStatus.valueOf(400))
        }
    }

    fun removeCourseFromFaculty(req: FacultyCourse, type: AccountType ) :ResponseEntity<Any?>{
        if(type==AccountType.ADMIN){
                var facultyObj = facultyRepository.findById(req.facultyId.toString())
                if(facultyObj.isPresent){
                    var faculty = facultyObj.get()
                    faculty.removeFromList(req.courseId)
                    val ret = facultyRepository.save(faculty)
                    return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret), HttpStatus.valueOf(200))
                }
                return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Faculty not found!"), HttpStatus.valueOf(400))
        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Access restricted!"), HttpStatus.valueOf(400))
        }
    }
}