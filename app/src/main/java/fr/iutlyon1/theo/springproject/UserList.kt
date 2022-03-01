package fr.iutlyon1.theo.springproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import fr.iutlyon1.theo.springproject.adapters.UserAdapter
import fr.iutlyon1.theo.springproject.entities.Constants
import fr.iutlyon1.theo.springproject.entities.User
import java.util.ArrayList

/*TODO list of objectives
    -> ask to access contact infos
    -> access contact infos and autofill the users with it
    -> add a user from the app to the real contact list
    -> do the form to add a user
*/
class UserList : AppCompatActivity() {

    private val listUsers = ArrayList<User>()


    lateinit var listView : ListView
    lateinit var addBtn : FloatingActionButton

    val adapter = UserAdapter(this, listUsers)


    private fun initializeElements() {

        listView =  findViewById(R.id.listView)
        addBtn   =  findViewById(R.id.addBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)


        initializeElements()
        fillUserList()


        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, i, l ->

            val intentOpen = Intent(this, UserForm::class.java)

            intentOpen.putExtra(Constants.USER_INFOS, i)
            intentOpen.putExtra(Constants.USER_NAME, listUsers[i].userName)
            intentOpen.putExtra(Constants.USER_LAST_NAME, listUsers[i].userLastName)
            intentOpen.putExtra(Constants.USER_GENDER, listUsers[i].gender)
            intentOpen.putExtra(Constants.USER_BIRTHDATE, listUsers[i].birthdate)
            intentOpen.putExtra(Constants.USER_PHONE, listUsers[i].phoneNumber)
            intentOpen.putExtra(Constants.USER_EMAIL, listUsers[i].email)
            intentOpen.putExtra(Constants.USER_ADDRESSE, listUsers[i].postalAddress)

            startActivityForResult(intentOpen, Constants.RESULT_CODE_FORM_USER_UPDATING)
        }

        listView.setOnItemLongClickListener { adapterView, view, i, l ->
            listUsers.removeAt(i)
            adapter.notifyDataSetChanged()
            true
        }


        addBtn.setOnClickListener {
            val intentOpen = Intent(this, UserForm::class.java)

            startActivityForResult(intentOpen, Constants.RESULT_CODE_FORM_USER_ADDING)
        }
    }


    //TODO remove function and search in the contact information file for that
    private fun fillUserList() {
        for(i in 0 until 5) {
            listUsers.add(User())
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            Constants.RESULT_CODE_FORM_USER_ADDING ->
                if(resultCode == RESULT_OK) {
                    Log.e("Activity results", "OK [add]")
                    if(data != null){
                        if(data.hasExtra(Constants.USER_NAME) && data.hasExtra(Constants.USER_LAST_NAME) && data.hasExtra(Constants.USER_PHONE) && data.hasExtra(Constants.USER_GENDER) && data.hasExtra(Constants.USER_EMAIL) && data.hasExtra(Constants.USER_BIRTHDATE) && data.hasExtra(Constants.USER_ADDRESSE)) {

                            val user = User(
                                data.getStringExtra(Constants.USER_NAME)!!,
                                data.getStringExtra(Constants.USER_LAST_NAME)!!,
                                data.getStringExtra(Constants.USER_GENDER)!!,
                                data.getStringExtra(Constants.USER_BIRTHDATE)!!,
                                data.getStringExtra(Constants.USER_PHONE)!!,
                                data.getStringExtra(Constants.USER_EMAIL)!!,
                                data.getStringExtra(Constants.USER_ADDRESSE)!!
                            )

                            listUsers.add(user)
                            adapter.notifyDataSetChanged()

                        }
                    }
                }
                else{
                    Log.e("Activity results", "an error occurred [add]")
                    Toast.makeText(applicationContext,
                        "There has been an error while trying to add your contact", Toast.LENGTH_SHORT).show()
                }

            Constants.RESULT_CODE_FORM_USER_UPDATING ->
                if(resultCode == RESULT_OK) {
                    Log.e("Activity results", "OK [update]")
                    if(data != null){
                        if(data.hasExtra(Constants.USER_NAME) && data.hasExtra(Constants.USER_LAST_NAME) && data.hasExtra(Constants.USER_PHONE) && data.hasExtra(Constants.USER_GENDER) && data.hasExtra(Constants.USER_EMAIL) && data.hasExtra(Constants.USER_BIRTHDATE) && data.hasExtra(Constants.USER_ADDRESSE) && data.hasExtra(Constants.USER_INFOS)) {

                            try {
                                listUsers[data.getIntExtra(Constants.USER_INFOS, -1)] = User(
                                    data.getStringExtra(Constants.USER_NAME)!!,
                                    data.getStringExtra(Constants.USER_LAST_NAME)!!,
                                    data.getStringExtra(Constants.USER_GENDER)!!,
                                    data.getStringExtra(Constants.USER_BIRTHDATE)!!,
                                    data.getStringExtra(Constants.USER_PHONE)!!,
                                    data.getStringExtra(Constants.USER_EMAIL)!!,
                                    data.getStringExtra(Constants.USER_ADDRESSE)!!
                                )
                            }
                            catch (e : Exception) {
                                Toast.makeText(applicationContext,
                                    "There has been an error while trying to update your contacts infos", Toast.LENGTH_SHORT).show()
                                Log.e("Activity results", "error while updating [update] : ${e.message}")
                            }

                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                else {
                    Log.e("Activity results", "an error occurred [update]")
                    Toast.makeText(applicationContext,
                        "There has been an error while trying to update your contacts infos", Toast.LENGTH_SHORT).show()
                }
        }
    }


}