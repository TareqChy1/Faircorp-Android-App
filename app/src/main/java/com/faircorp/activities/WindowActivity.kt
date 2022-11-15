package com.faircorp.activities
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.faircorp.R
import com.faircorp.actions.WindowAction
import com.faircorp.actions.WindowListener
import com.faircorp.serviceApi.ServicesApi
import com.faircorp.datamodels.RootDataModel
import com.faircorp.datamodels.WindowDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class WindowActivity : AppCompatActivity(), WindowListener {
    private val viewModel: WindowDataModel by viewModels { WindowDataModel.factory }
    var roomId = 0L
    private val adapter = WindowAction(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        val recyclerView = findViewById<RecyclerView>(R.id.Window_List)

        roomId = intent.getLongExtra(ROOM_ID, 0)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        val fab = findViewById<FloatingActionButton>(R.id.windowActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, CreateWindowActivity::class.java).putExtra(ROOM_ID, roomId)
            startActivity(intent)
        }


    }


    override fun onWindowSwitched(id: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            runCatching { ServicesApi.windowServiceApi.switchStatus(id).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Window switched",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on switching window $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }


    override fun onWindowChange(id: Long) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_show)
        dialog.show()
        dialog.findViewById<Button>(R.id.btnDelete).setOnClickListener {

            viewModel.deleteWindow(id).observe(this) {

                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@WindowActivity, "Window deleted", Toast.LENGTH_LONG)
                            .show()
                        dialog.dismiss()
                    }
                }
            }

        }
        dialog.findViewById<Button>(R.id.btnEdit).setOnClickListener {
            val window = adapter.getWindowById(id)
            val intent =
                Intent(this, CreateWindowActivity::class.java)
                    .putExtra(WINDOW_ID, id).putExtra(ROOM_ID, roomId)
                    .putExtra(Window_NAME, window?.name)
                    .putExtra(Window_STATUS, window?.windowStatus.toString())
            startActivity(intent)
            dialog.dismiss()
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.findByRoomId(intent.getLongExtra(ROOM_ID, 0)).observe(this) { windows ->
            adapter.update(windows)
            viewModel.networkState.observe(this) { state ->
                if (state == RootDataModel.State.OFFLINE) {
                    Toast.makeText(
                        this,
                        "Offline mode",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }
}