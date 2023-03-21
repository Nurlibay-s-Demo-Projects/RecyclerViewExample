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

        val items = mutableListOf<Any>()
        for (i in 1..20) {
            if (i % 5 == 0) {
                items.add(Ads("Title $i"))
            } else {
                items.add(Person("Name $i", "Surname $i"))
            }
        }

        myAdapter.list.addAll(items)
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
                    dialog.apply {
                        setTitle("Remove Item")
                        setMessage("Aniq óshirejaqsańba?")
                        setPositiveButton("AWA") { dialog, id ->
                            myAdapter.removeItem(person, position)
                            Toast.makeText(this@MainActivity, "Óshirildi", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                        setNegativeButton("YAQ") { dialog, id ->
                            Toast.makeText(this@MainActivity, "Óshirilmedi", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                        show()
                    }
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