package com.pfip.backend.model

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Course {
    @Id
    var id: Int? = null;
    var courseName: String? = null;
    var facultyId: Int? = null;

    @ElementCollection
    var listOfStudents: MutableList<Int> = mutableListOf()

    fun appendToList(i: Int){
        listOfStudents.add(i)
    }
    fun removeFromList(i: Int){
        listOfStudents.remove(i)
    }
}