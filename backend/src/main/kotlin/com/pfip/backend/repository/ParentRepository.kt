package com.pfip.backend.repository
import com.pfip.backend.model.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParentRepository: JpaRepository<Parent, String> {
}