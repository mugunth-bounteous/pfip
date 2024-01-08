package com.pfip.backend.repository
import com.pfip.backend.model.Faculty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FacultyRepository: JpaRepository<Faculty, String> {

}