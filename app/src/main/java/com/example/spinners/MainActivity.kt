package com.example.spinners

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.spinners.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var mutableList : MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mutableList = mutableListOf("Names")
        binding.spStatic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = binding.spStatic.selectedItem as String
                val selectedPos = binding.spStatic.selectedItemPosition
                if(selectedPos != 0)
                Toast.makeText(this@MainActivity,"$selectedItem is Selected..",Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.fab.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custombg)
            dialog.setCancelable(false)
            val window = dialog.window
            window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
            val addBtn = dialog.findViewById<TextView>(R.id.positiveButton)
            addBtn.setOnClickListener {
                if(dialog.findViewById<TextInputEditText>(R.id.etName).text?.trim()?.isEmpty()!!){
                    dialog.findViewById<TextInputLayout>(R.id.textInputLayout).error = "Enter Name.."
                }
                else{
                    mutableList.add(dialog.findViewById<TextInputEditText>(R.id.etName).text?.trim().toString())
                    arrayAdapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity,"New Item Added..",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                dialog.findViewById<TextInputEditText>(R.id.etName).doOnTextChanged { _, _, _, _ ->
                    dialog.findViewById<TextInputLayout>(R.id.textInputLayout).error = null
                }
            }
            dialog.show()
        }
        arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,mutableList)
        binding.spDynamic.adapter = arrayAdapter
    }
}