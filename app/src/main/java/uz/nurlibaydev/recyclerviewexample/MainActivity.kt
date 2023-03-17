package uz.nurlibaydev.recyclerviewexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DividerItemDecoration
import uz.nurlibaydev.recyclerviewexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPerson.adapter = adapter
        binding.rvPerson.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val persons = mutableListOf<Person>()
        repeat(50) {
            persons.add(Person("Name ${it + 1}", "Surname ${it + 1}"))
        }
        adapter.list.addAll(persons)
        adapter.setOnItemClickListener {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnMoreBtnClickListener { person, view ->
            popupMenu(view, person)
        }
    }

    private fun popupMenu(view: View, person: Person) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_add -> {
                    Toast.makeText(this, "Add menu item clicked", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.item_remove -> {
                    Toast.makeText(this, "Remove menu item clicked", Toast.LENGTH_SHORT).show()
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