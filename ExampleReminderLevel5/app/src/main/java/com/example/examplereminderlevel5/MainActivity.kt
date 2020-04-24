package com.example.examplereminderlevel5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.examplereminderlevel5.db.ReminderRepository
import com.example.examplereminderlevel5.model.Reminder

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ADD_REMINDER_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel: MainActivityViewModel by viewModels()

    var reminders = arrayListOf<Reminder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        createItemTouchHelper().attachToRecyclerView(rvReminders)

        initViews()
        observeViewModel()
    }

    private fun initViews() {
        fabAddReminder.setOnClickListener { startAddActivity() }

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReminderAdapter(reminders)

        recyclerView = findViewById<RecyclerView>(R.id.rvReminders).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.reminders.observe(this, Observer { reminders ->
            this@MainActivity.reminders.clear()
            this@MainActivity.reminders.addAll(reminders)
            viewAdapter.notifyDataSetChanged()
        })

    }


    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, ADD_REMINDER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_REMINDER_REQUEST_CODE -> {
                    val reminder = data!!.getParcelableExtra<Reminder>(EXTRA_REMINDER)
                    viewModel.insertReminder(reminder)
                }
            }
        }
    }


    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val reminderToDelete = reminders[position]

                viewModel.deleteReminder(reminderToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        deleteAllProducts()

        return when (item.itemId) {
            R.id.menuDelete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllProducts() {
        viewModel.deleteAllReminders()
    }
}
