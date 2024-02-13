package com.pfip.backend.model

import jakarta.persistence.*

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var firstName: String? = null
    var lastName: String? = null

    @ElementCollection
    var listOfCourses: MutableList<Int> = mutableListOf()

    fun appendToList(i:Int){
        listOfCourses.add(i)
    }
    fun removeFromList(i:Int){
        listOfCourses.remove(i)
    }

}