package com.pfip.backend.repository


import com.pfip.backend.model.Assignment
import com.pfip.backend.model.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

public interface TestRepository: JpaRepository<Test, String> {
    @Query("from Test where courseId=?1")
    fun fetchAllTestsByCourseId(courseId: Int?): List<Assignment?>?
}