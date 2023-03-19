package uz.nurlibaydev.recyclerviewexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DividerItemDecoration
import uz.nurlibaydev.recyclerviewexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPerson.adapter = myAdapter
        binding.rvPerson.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val persons = mutableListOf<Person>()
        repeat(20) {
            persons.add(Person("Name ${it + 1}", "Surname ${it + 1}"))
        }
        myAdapter.list.addAll(persons)
        myAdapter.setOnItemClickListener {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
        myAdapter.setOnMoreBtnClickListener { person, view, position ->
            popupMenu(view, person, position)
        }
    }

    private fun popupMenu(view: View, person: Person, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_add -> {
                    Toast.makeText(this, "Add menu item clicked", Toast.LENGTH_SHORT).show()
                    myAdapter.addItem()
                    return@setOnMenuItemClickListener true
                }
                R.id.item_remove -> {

                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Remove Item")
                    dialog.setMessage("Aniq óshirejaqsańba?")
                    dialog.setPositiveButton("AWA") { dialog, id ->
                        myAdapter.removeItem(person, position)
                        Toast.makeText(this, "Óshirildi", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.setNegativeButton("YAQ") { dialog, id ->
                        Toast.makeText(this, "Óshirilmedi", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
        popupMenu.show()
    }
}