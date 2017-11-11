package com.example.muhammed.myfirebasedatabase

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by muhammed on 11/11/17.
 */
class EmployeeAdapter(val mCtx : Context , val layoutId:Int , val employeeList:List<Employees>)
    :ArrayAdapter<Employees>(mCtx,layoutId,employeeList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(layoutId,null)

        val firstname = view.findViewById<TextView>(R.id.firstnameText)
        val lastname = view.findViewById<TextView>(R.id.lastnametext)
        val address = view.findViewById<TextView>(R.id.addresstext)
        val department = view.findViewById<TextView>(R.id.depttext)

        val updateBtn = view.findViewById<TextView>(R.id.update)
        val deleteBtn = view.findViewById<TextView>(R.id.delete)

        val employee = employeeList[position]

        firstname.text = employee.firstname
        lastname.text = employee.lastname
        address.text = employee.address
        department.text = employee.department

        updateBtn.setOnClickListener {
            updateInfo(employee)
        }

        deleteBtn.setOnClickListener {
            deleteInfo(employee)
        }

        return view
    }

    private  fun updateInfo(employee:Employees){

        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update Info")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.employee_update,null)

        val firstname = view.findViewById<TextView>(R.id.firstnameUpdate)
        val lastname = view.findViewById<TextView>(R.id.lastnameUpdate)
        val address = view.findViewById<TextView>(R.id.addressUpdate)
        val department = view.findViewById<TextView>(R.id.departmentUpdate)

        firstname.setText(employee.firstname)
        lastname.setText(employee.lastname)
        address.setText(employee.address)
        department.setText(employee.department)

        builder.setView(view)

        builder.setPositiveButton("Update",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

              val  myDatabase = FirebaseDatabase.getInstance().getReference("Employees")

                val firstname1    = firstname.text.toString().trim()
                val lastname2     = lastname.text.toString().trim()
                val address3     = address.text.toString().trim()
                val department4   = department.text.toString().trim()

                if (firstname1.isEmpty()){
                    firstname.error = "Please enter your firstname"
                    return
                }
                if (lastname2.isEmpty()){
                    lastname.error = "Please enter your lastname"
                    return
                }
                if (address3.isEmpty()){
                    address.error = "Please enter your address"
                    return
                }
                if (department4.isEmpty()){
                    department.error = "Please enter your department"
                    return
                }

                val employee = Employees(employee.id,firstname1,lastname2,address3,department4)
                myDatabase.child(employee.id).setValue(employee)
                    Toast.makeText(mCtx,"Updated :) ", Toast.LENGTH_LONG).show()



        }})

        builder.setNegativeButton("cancel",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })

        val alert = builder.create()
        alert.show()

    }


    private fun deleteInfo(employee:Employees){
        val myDatabase = FirebaseDatabase.getInstance().getReference("Employees")
        myDatabase.child(employee.id).removeValue()
        Toast.makeText(mCtx,"Deleted !",Toast.LENGTH_LONG).show()
    }

}