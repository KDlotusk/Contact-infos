package fr.iutlyon1.theo.springproject

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import fr.iutlyon1.theo.springproject.entities.Constants
import fr.iutlyon1.theo.springproject.popup.PopupLayoutPhotos


class UserForm : AppCompatActivity() {

    private lateinit var validateBtn : FloatingActionButton
    private lateinit var textViewName : TextInputLayout
    private lateinit var textViewLastName : TextInputLayout
    private lateinit var textViewPhone : TextInputLayout
    private lateinit var textViewGender : TextInputLayout
    private lateinit var textViewEmail : TextInputLayout
    private lateinit var textViewBirthDate : TextInputLayout
    private lateinit var textViewAddress : TextInputLayout
    private lateinit var imageButtonAvatar : ImageButton

    private val intentResult = Intent()


    private fun initializeElements() {
        validateBtn   =  findViewById(R.id.validateBtn)

        textViewName  =  findViewById(R.id.textViewName)
        textViewLastName = findViewById(R.id.textViewLastName)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewGender = findViewById(R.id.textViewGender)
        textViewEmail = findViewById(R.id.textViewEMail)
        textViewBirthDate = findViewById(R.id.textViewBirthDate)
        textViewAddress = findViewById(R.id.textViewAddress)
        imageButtonAvatar = findViewById(R.id.imageButtonAvatarDetail)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)

        initializeElements()




        //in case we're updating an existing user
        if(intent.hasExtra(Constants.USER_NAME) && intent.hasExtra(Constants.USER_LAST_NAME) && intent.hasExtra(Constants.USER_PHONE) && intent.hasExtra(Constants.USER_GENDER) && intent.hasExtra(Constants.USER_EMAIL) && intent.hasExtra(Constants.USER_BIRTHDATE) && intent.hasExtra(Constants.USER_ADDRESSE) && intent.hasExtra(Constants.USER_INFOS)) {
            setFields()
            Log.e("userForm", "passing through")
            intentResult.putExtra(Constants.USER_INFOS, intent.getIntExtra(Constants.USER_INFOS, -1))

        }


        validateBtn.setOnClickListener {
            leaving()
        }

        imageButtonAvatar.setOnClickListener {

            val intentOpen = Intent(this, PopupLayoutPhotos::class.java)

            startActivityForResult(intentOpen, Constants.RESULT_CODE_POPUP_PICTURE)
        }

    }

    private fun checkingTheFields(): Boolean {
        var verif = true

        if(textViewName.editText!!.text.isNullOrEmpty()) {
            verif = false
            textViewName.error = "you need to set this value"
        }
        else {
            intentResult.putExtra(Constants.USER_NAME, textViewName.editText!!.text.toString())
            textViewName.isErrorEnabled = false
        }

        if(textViewLastName.editText!!.text.isNullOrEmpty()) {
            verif = false
            textViewLastName.error = "you need to set this value"
        }
        else {
            intentResult.putExtra(Constants.USER_LAST_NAME, textViewLastName.editText!!.text.toString())
            textViewLastName.isErrorEnabled = false
        }

        if(!"""( *[0-9] *){10}""".toRegex().matches( textViewPhone.editText!!.text.toString())){
            verif = false
            textViewPhone.error = "you need to set this value \n format: \"06 52 13 20 29\""
        }
        else {
            intentResult.putExtra(Constants.USER_PHONE, textViewPhone.editText!!.text.toString())
            textViewPhone.isErrorEnabled = false
        }

        if(textViewGender.editText!!.text.isNullOrEmpty()){
            verif = false
            textViewGender.error = "you need to set this value"
        }
        else {
            intentResult.putExtra(Constants.USER_GENDER, textViewGender.editText!!.text.toString())
            textViewGender.isErrorEnabled = false
        }

        if(!"""(\w*.?)*@\w*.(com)|(fr)|(net)""".toRegex().matches( textViewEmail.editText!!.text.toString())){
            verif = false
            textViewEmail.error = "you need to set this value \n format: \"my.name@example.com\""
        }
        else {
            intentResult.putExtra(Constants.USER_EMAIL, textViewEmail.editText!!.text.toString())
            textViewEmail.isErrorEnabled = false
        }

        if(textViewBirthDate.editText!!.text.isNullOrEmpty()){
            verif = false
            textViewBirthDate.error = "you need to set this value"
        }
        else {
            intentResult.putExtra(Constants.USER_BIRTHDATE, textViewBirthDate.editText!!.text.toString())
            textViewBirthDate.isErrorEnabled = false
        }

        if(textViewAddress.editText!!.text.isNullOrEmpty()){
            verif = false
            textViewAddress.error = "you need to set this value"
        }
        else {
            intentResult.putExtra(Constants.USER_ADDRESSE, textViewAddress.editText!!.text.toString())
            textViewAddress.isErrorEnabled = false
        }


        return verif
    }

    private fun setDefaultValues() {

        if(textViewName.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_NAME, "name")

        if(textViewLastName.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_LAST_NAME, "last name")

        if(!"""( *[0-9] *){10}""".toRegex().matches( textViewPhone.editText!!.text))
            intentResult.putExtra(Constants.USER_PHONE, "00 00 00 00 00")

        if(textViewGender.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_GENDER, "no genre")

        if(!"""(\w*.?)*@\w*.(com)|(fr)|(net)""".toRegex().matches( textViewEmail.editText!!.text))
            intentResult.putExtra(Constants.USER_EMAIL, "email@exemple.com")

        if(textViewBirthDate.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_BIRTHDATE, "1967/01/01")

        if(textViewAddress.editText!!.text.isNullOrEmpty())
            intentResult.putExtra(Constants.USER_ADDRESSE, "0100 Default Avenue")
    }

    private fun setFields() {

        textViewName.editText!!.setText(intent.getStringExtra(Constants.USER_NAME))
        textViewLastName.editText!!.setText(intent.getStringExtra(Constants.USER_LAST_NAME))
        textViewPhone.editText!!.setText(intent.getStringExtra(Constants.USER_PHONE))
        textViewGender.editText!!.setText(intent.getStringExtra(Constants.USER_GENDER))
        textViewBirthDate.editText!!.setText(intent.getStringExtra(Constants.USER_BIRTHDATE))
        textViewEmail.editText!!.setText(intent.getStringExtra(Constants.USER_EMAIL))
        textViewAddress.editText!!.setText(intent.getStringExtra(Constants.USER_ADDRESSE))

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




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            Constants.RESULT_CODE_POPUP_PICTURE ->
                if(resultCode == RESULT_OK) {
                    Log.e("Activity results", "OK [picture]")
                    if(data != null){
                        if(data.hasExtra(Constants.USER_AVATAR)) {
                            val imagePath = data.getStringExtra(Constants.USER_AVATAR)
                            imageButtonAvatar.setImageBitmap(BitmapFactory.decodeFile(imagePath))

                            Toast.makeText(this, "YES", Toast.LENGTH_SHORT)
                            //imageButtonAvatar.setImageBitmap(data)
                        }
                    }
                }
                else{
                    Log.e("Activity results", "an error occurred [picture]")
                    Toast.makeText(applicationContext,
                        "There has been an error while trying to get your picture", Toast.LENGTH_SHORT).show()
                }

        }

    }
}