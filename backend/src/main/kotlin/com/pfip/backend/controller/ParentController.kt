package com.pfip.backend.controller

import com.pfip.backend.dto.CreateParent
import com.pfip.backend.service.ParentService
import com.pfip.backend.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/parent")
@CrossOrigin(origins = ["http://localhost:3000"])
class ParentController(
    private val tokenService: TokenService,
    private val parentService: ParentService
) {
    @PostMapping("/createParent")
    fun createParent(@RequestHeader(name = "Authorization") authorizationHeader: String,@RequestBody req:CreateParent): ResponseEntity<Any?>{
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret)
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
        return parentService.createParent(req,type)

    }

    @GetMapping("/getStudentDetails")
    fun getStudentDetails(@RequestHeader(name = "Authorization") authorizationHeader: String,){
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret)
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId
    }

    @GetMapping("/getDetails")
    fun getSelfDetails(@RequestHeader(name = "Authorization") authorizationHeader: String,){
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret)
        val username = data!!.username
        val type= data.type
        val typeId=data.typeId

    }

}