package com.pfip.backend.service

import com.pfip.backend.dto.*
import com.pfip.backend.model.Parent
import com.pfip.backend.repository.AssignmentRepository
import com.pfip.backend.repository.CourseRepository
import com.pfip.backend.repository.ParentRepository
import com.pfip.backend.repository.StudentRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ParentService(
    private val courseRepository: CourseRepository,
    private val parentRepository: ParentRepository,
    private val studentRepository: StudentRepository
    ) {

   fun createParent(req: CreateParent,type: AccountType): ResponseEntity<Any?> {
       if (type!=AccountType.ADMIN) {
           return ResponseEntity<Any? >(ResponseMessage(status = "FAILED", data = "Access restricted!"), HttpStatus.valueOf(400))
       }
       else {
           var parent= Parent()
           parent.parentName = req.name
           parent.email = req.emailId
           parent.studentId = req.studentId
           val ret = parentRepository.save(parent)
           return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret), HttpStatus.valueOf(200))
       }
   }

   fun getStudentDetails(typeId:Int,type: AccountType): ResponseEntity<Any?>{
       if(type!=AccountType.PARENT){
           return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Access restricted!"), HttpStatus.valueOf(400))
       }
       else{
           val parentData = parentRepository.findById(typeId.toString())
           if (parentData.isEmpty()){
               return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Parent not found!"), HttpStatus.valueOf(400))
           }
           val parent=parentData.get()
           val courses = courseRepository.fetchCourseByStudentId(parent.studentId)!!
           val student = studentRepository.fetchByParentId(typeId)!!
           val returnData = StudentDetails(courses ,student)
           return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = returnData),HttpStatus.valueOf(200))
       }
   }


}