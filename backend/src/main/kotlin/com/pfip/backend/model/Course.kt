package com.pfip.backend.model

import jakarta.persistence.*
import org.springframework.boot.context.properties.bind.DefaultValue

@Entity
public class Course {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
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