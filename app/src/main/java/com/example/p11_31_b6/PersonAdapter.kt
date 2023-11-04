package com.example.p11_31_b6

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class PersonAdapter(private val context: Context, private val array: ArrayList<Person>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    lateinit var databaseHelper: DatabaseHelper

    init {
        // Initialize the databaseHelper here
        databaseHelper = DatabaseHelper(context)
    }

    inner class PersonViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.txt_name)
        val emailTxt: TextView = itemView.findViewById(R.id.txt_email)
        val phoneTxt: TextView = itemView.findViewById(R.id.txt_phone)
        val addressTxt: TextView = itemView.findViewById(R.id.txt_address)
        val mapBtn: ImageView = itemView.findViewById(R.id.button_map)
        val deleteBtn: ImageView = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item_view, parent, false)
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = array[position]
        holder.nameTxt.text = person.Name
        holder.emailTxt.text = person.EmailId
        holder.phoneTxt.text = person.PhoneNo
        holder.addressTxt.text = person.Address
        val obj = person as Serializable
        databaseHelper.insertPerson(person)

        holder.mapBtn.setOnClickListener {
            Intent(this@PersonAdapter.context, MapsActivity::class.java).apply {
                putExtra("Object", obj)
                this@PersonAdapter.context.startActivity(this)
            }
        }

        holder.deleteBtn.setOnClickListener {
            var count = databaseHelper.deletePerson(person.Id)
            if (count > 0) {
                Toast.makeText(this.context, "${person.Name}'s details deleted successfully!", Toast.LENGTH_SHORT).show()
                array.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }
}
