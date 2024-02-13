package com.pfip.backend.repository

import com.pfip.backend.model.Assignment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
public interface AssignmentRepository: JpaRepository<Assignment, String> {

    @Query("from Assignment where courseId=?1")
    fun fetchAllByCourseId(courseId: Int?): List<Assignment?>?

}