package com.pfip.backend.service

import com.pfip.backend.dto.*
import com.pfip.backend.model.Course
import com.pfip.backend.repository.CourseRepository
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
public class CourseService(
    private val courseRepository: CourseRepository,

) {
    fun findCoursesByStudentId(typeId: Int,studentDto: StudentDto):ResponseEntity<Any?>{
        val ret:List<Course> = courseRepository.fetchCourseByStudentId(studentDto.studentId)!!
        if(ret.isEmpty()){
            return ResponseEntity<Any?>(ResponseMessage("FAILED","No Courses Found"),HttpStatus.valueOf(400))
        }
        return ResponseEntity<Any?>(ResponseMessage("SUCCESS",ret),HttpStatus.valueOf(200))


    }
    fun findCoursesByFacultyId(typeId: Int):ResponseEntity<Any?>{
        val ret:List<Course> = courseRepository.fetchCourseByFacultyId(typeId)!!
        if(ret.isEmpty()){
            return ResponseEntity<Any?>(ResponseMessage("FAILED","No course assigned to faculty"),HttpStatus.valueOf(400))
        }
        return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret),HttpStatus.valueOf(200))

    }

    fun createCourse(typeId:Int,req:CreateCourseDto,type:AccountType):ResponseEntity<Any?>{
        if(type==AccountType.ADMIN){
            var course = Course()
            course.courseName = req.courseName
            course.facultyId = req.facultyId
            course.listOfStudents = mutableListOf()
            val ret=courseRepository.save(course)
           return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret),HttpStatus.valueOf(200))
        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Access restricted!"),HttpStatus.valueOf(400))
        }
    }
}