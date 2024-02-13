package com.pfip.backend.repository
import com.pfip.backend.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
public interface StudentRepository: JpaRepository<Student, String> {

    @Query("from Student where parentId=?1")
    fun fetchByParentId(parentId:Int):Student?

}