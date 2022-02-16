package fr.iutlyon1.theo.springproject.entities

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class User(
    val id : Long = -1,

    var userName : String = "John",
    var userLastName : String = "Doe",
    var gender : Gender = Gender.MALE,

    var birthdate : String = "1967/01/01",

    var phoneNumber : String = "00 00 00 00 00",
    var email : String = "ex@mple.com",
    var zipCode : String = "0000",
    var postalAddress : String = "0 avenue, Town",

    var imageName : String = "im_default",
) {



}