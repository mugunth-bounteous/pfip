package com.pfip.backend.repository


import com.pfip.backend.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
public interface CourseRepository: JpaRepository<Course, String> {

    @Query("from Course where id=?1")
    fun fetchCourseByStudentId(id:Int?): List<Course>?

    @Query("from Course where id=?1")
    fun fetchCourseById(id: Int?): Course?

    @Query("from Course where facultyId=?1")
    fun fetchCourseByFacultyId(id:Int?): List<Course>?
}