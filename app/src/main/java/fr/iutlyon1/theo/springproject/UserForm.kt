package fr.iutlyon1.theo.springproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import fr.iutlyon1.theo.springproject.entities.Constants
import fr.iutlyon1.theo.springproject.entities.User

class UserForm : AppCompatActivity() {

    lateinit var validateBtn : FloatingActionButton
    lateinit var textViewName : TextInputLayout
    lateinit var textViewLastName : TextInputLayout
    lateinit var textViewPhone : TextInputLayout
    lateinit var textViewGenre : TextInputLayout
    lateinit var textViewEMail : TextInputLayout
    lateinit var textViewBirthDate : TextInputLayout
    lateinit var textViewAddress : TextInputLayout

    val intentResult = Intent()


    private fun initializeElements() {
        validateBtn   =  findViewById(R.id.validateBtn)

        textViewName  =  findViewById(R.id.textViewName)
        textViewLastName = findViewById(R.id.textViewLastName)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewGenre = findViewById(R.id.textViewGenre)
        textViewEMail = findViewById(R.id.textViewEMail)
        textViewBirthDate = findViewById(R.id.textViewBirthDate)
        textViewAddress = findViewById(R.id.textViewAddress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)

        initializeElements()

        validateBtn.setOnClickListener {
            leaving()
        }

    }

    private fun checkingTheFields(): Boolean {
        var verif = true

        if(textViewName.editText!!.text.isNullOrEmpty())
            verif = false
        else
            intentResult.putExtra(Constants.USER_NAME, textViewName.editText!!.text)

        if(textViewLastName.editText!!.text.isNullOrEmpty())
            verif = false
        else
            intentResult.putExtra(Constants.USER_LAST_NAME, textViewLastName.editText!!.text)

        if(!"""( *[0-9] *){10}""".toRegex().matches( textViewPhone.editText!!.text))
            verif = false
        else
            intentResult.putExtra(Constants.USER_PHONE, textViewPhone.editText!!.text)

        if(textViewGenre.editText!!.text.isNullOrEmpty())
            verif = false
        else
            intentResult.putExtra(Constants.USER_GENRE, textViewGenre.editText!!.text)

        if(!"""(\w*.?)*@\w*.(com)|(fr)|(net)""".toRegex().matches( textViewEMail.editText!!.text))
            verif = false
        else
            intentResult.putExtra(Constants.USER_EMAIL, textViewEMail.editText!!.text)

        if(textViewBirthDate.editText!!.text.isNullOrEmpty())
            verif = false
        else
            intentResult.putExtra(Constants.USER_BIRTHDATE, textViewBirthDate.editText!!.text)

        if(textViewAddress.editText!!.text.isNullOrEmpty())
            verif = false
        else
            intentResult.putExtra(Constants.USER_ADDRESSE, textViewAddress.editText!!.text)


        Log.e("check", verif.toString())

        return verif
    }

    private fun setDefaultValues() {

        if(textViewName.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_NAME, "name")

        if(textViewLastName.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_LAST_NAME, "last name")

        if(!"""( *[0-9] *){10}""".toRegex().matches( textViewPhone.editText!!.text))
            intentResult.putExtra(Constants.USER_PHONE, "00 00 00 00 00")

        if(textViewGenre.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_GENRE, "no genre")

        if(!"""(\w*.?)*@\w*.(com)|(fr)|(net)""".toRegex().matches( textViewEMail.editText!!.text))
            intentResult.putExtra(Constants.USER_EMAIL, "email@exemple.com")

        if(textViewBirthDate.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_BIRTHDATE, "01/01/1967")

        if(textViewAddress.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_ADDRESSE, "0100 Hollywood Avenue")
    }

    private fun launchError(){

        Log.e("check", "pass by launch")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("You didn't set all the values correctly")
        builder.setMessage("Do you want to use default values instead ?")

        builder.setPositiveButton("Yes, I do") { dialog, which ->
            //super.onBackPressed()

            setDefaultValues()

            setResult(RESULT_OK, intentResult)
            finish()
        }

        builder.setNegativeButton("No, let me write") { dialog, which ->
            Toast.makeText(applicationContext,
                "Please fill in the form with the correct values", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun leaving() {
        Log.e("check", "pass by leaving")


        if(!checkingTheFields()) {
            launchError()
        }
        else {
            setResult(RESULT_OK, intentResult)
            finish()
        }
    }


    override fun onBackPressed() {
        leaving()
    }

}