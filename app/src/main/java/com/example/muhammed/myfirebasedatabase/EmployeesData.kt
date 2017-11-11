package com.example.muhammed.myfirebasedatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class EmployeesData : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var employeeList:MutableList<Employees>
    lateinit var listview:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_data)

        employeeList = mutableListOf()
        listview = findViewById(R.id.listview1)
        ref = FirebaseDatabase.getInstance().getReference("Employees")

        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
             }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()){
                    employeeList.clear()
                    for (e in p0.children){
                        val employee = e.getValue(Employees::class.java)
                        employeeList.add(employee!!)
                    }
         val adapter = EmployeeAdapter(this@EmployeesData,R.layout.employees,employeeList)
                    listview.adapter = adapter
                }
             }

        })
    }
}
