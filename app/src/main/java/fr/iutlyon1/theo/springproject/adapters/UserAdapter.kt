package fr.iutlyon1.theo.springproject.adapters

import fr.iutlyon1.theo.springproject.R
import fr.iutlyon1.theo.springproject.entities.User


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.ArrayList

class UserAdapter(
    val context: Context,
    val listUser : ArrayList<User>
) : BaseAdapter() {


    override fun getCount(): Int {
        return listUser.size
    }

    override fun getItem(index : Int): Any {
        return listUser[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutItem : View
        val mInflater = LayoutInflater.from(context)

        if (convertView == null) {
            layoutItem = mInflater.inflate(R.layout.user_infos_layout, parent, false)
        } else {
            layoutItem = convertView
        }

        var viewHolder = ViewHolder (layoutItem.findViewById(R.id.name), layoutItem.findViewById(R.id.phoneNumber), layoutItem.findViewById(R.id.userPicture))
        if(layoutItem.tag != null) {
            viewHolder = layoutItem.tag as ViewHolder
        }

        layoutItem.tag = viewHolder


        val resID = context.resources.getIdentifier("im_" + listUser[position].imageName, "mipmap", context.packageName)
        if(resID != 0)
            viewHolder.imageView.setImageResource(resID)
        else
            viewHolder.imageView.setImageResource(resID) // TODO (R.drawable.im_default)

        viewHolder.name.text = "${listUser[position].userName} ${listUser[position].userLastName}"
        viewHolder.phoneNumber.text = listUser[position].phoneNumber


        return layoutItem
    }


}
class ViewHolder (
    var name: TextView,
    var phoneNumber: TextView,
    var imageView : ImageView
    )
