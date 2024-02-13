package com.pfip.backend.service
import com.pfip.backend.dto.CreateUserDto
import com.pfip.backend.dto.LoginResponse
import com.pfip.backend.dto.ResponseMessage
import com.pfip.backend.dto.UserCredentials
import com.pfip.backend.model.Account
import com.pfip.backend.repository.AccountRepository
import com.pfip.backend.service.PasswordService
import com.pfip.backend.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
public class AuthService(
    private val passwordService: PasswordService,
    private val tokenService: TokenService,
    private val accountRepository: AccountRepository
    ) {
    fun login(req:UserCredentials): ResponseEntity<Any?>{
        val rawPassword = req.password
        val account = accountRepository.findById(req.username).getOrNull()
        if(account != null){
            val passwordInDb = account.password!!
            val loginPass = passwordService.checkPassword(rawPassword,passwordInDb)
            if(loginPass){
                //success,send data
                return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = LoginResponse(token = tokenService.createToken(account), username = account.username!!, typeId = account.id!!, type = account.type!!)), HttpStatus.valueOf(200))
            }
            else{
                //send passwords are not matching
                return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Password in the system is not matching with server"), HttpStatus.valueOf(400))
            }
        }
        else{
            // send username does not exist!!
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED",data="Username is not found"), HttpStatus.valueOf(404))
        }


    }
    fun createUser(req:CreateUserDto): ResponseEntity<Any?>{
        val newAccount = Account()
        newAccount.type = req.type
        newAccount.password = passwordService.encodePassword(req.password)
        newAccount.username = req.username
        newAccount.id = 1
        val account = accountRepository.findById(req.username).getOrNull()
        if(account!=null){
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", data = "Username already exist"), HttpStatus.valueOf(404))
        }
        else{
            val ret = accountRepository.save(newAccount)
            return ResponseEntity<Any?>(ResponseMessage(status = "SUCCESS", data = ret), HttpStatus.valueOf(200))
        }
    }



}
