package com.pfip.backend.model

import jakarta.persistence.*

@Entity
class Student {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Int? = null

    var parentId: Int? = null

    var studentName: String? = null

    @ElementCollection
    var coursesEnrolled: MutableList<Int> = mutableListOf()

    fun appendToCoursesEnrolled(i:Int){
        coursesEnrolled.add(i)
    }
    fun removeFromCoursesEnrolled(i:Int){
        coursesEnrolled.remove(i)
    }

}