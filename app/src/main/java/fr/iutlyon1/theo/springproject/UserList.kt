package fr.iutlyon1.theo.springproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
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


    private fun initializeElements() {

        listView =  findViewById(R.id.listView)
        addBtn   =  findViewById(R.id.addBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)


        initializeElements()
        fillUserList()


        val adapter = UserAdapter(this, listUsers)

        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, i, l ->
            Snackbar.make(view, listUsers[i].userName + " " + listUsers[i].userLastName, Snackbar.LENGTH_LONG).show()
        }

        listView.setOnItemLongClickListener { adapterView, view, i, l ->
            listUsers.removeAt(i)
            adapter.notifyDataSetChanged()
            true
        }


        addBtn.setOnClickListener {
            val intentOpen = Intent(this, UserForm::class.java)

            intentOpen.putExtra(Constants.USER_INFOS, listUsers.size)
            startActivityForResult(intentOpen, Constants.RESULT_CODE_FORM_USER)
        }
    }


    //TODO remove function and search in the contact information file for that
    private fun fillUserList() {
        for(i in 0 until 20) {
            listUsers.add(User())
        }

    }


}