package com.faircorp.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.viewModels
import com.faircorp.R
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus
import com.faircorp.datamodels.WindowDataModel



class CreateWindowActivity : AppCompatActivity() {

    private val viewModel: WindowDataModel by viewModels { WindowDataModel.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_window)
        val windowID = intent.getLongExtra(WINDOW_ID, 0)
        val roomID = intent.getLongExtra(ROOM_ID, 0)
        val editTextName = findViewById<EditText>(R.id.CreateWindowName)
        val radioGroupWindow = findViewById<RadioGroup>(R.id.radioGroupWindowStatus)
        val saveButton = findViewById<Button>(R.id.buttonSaveWindow)
        if (intent.getStringExtra(Window_NAME) != null) {
            editTextName.setText(intent.getStringExtra(Window_NAME))
            when (intent.getStringExtra(Window_STATUS)) {
                "OPEN" -> radioGroupWindow.check(R.id.radioButtonWindowOpen)
                "CLOSED" -> radioGroupWindow.check(R.id.radioButtonWindowClosed)
            }
        }


        saveButton.setOnClickListener {
            val name = editTextName.text.toString()
            val status = when (radioGroupWindow.checkedRadioButtonId) {
                R.id.radioButtonWindowOpen -> WindowStatus.OPEN
                R.id.radioButtonWindowClosed -> WindowStatus.CLOSED
                else -> WindowStatus.CLOSED
            }

            viewModel.createWindow(WindowDto(
                id=if (windowID == 0L) null else windowID,
                name = name,
                windowStatus = status,
                roomId = roomID))
                .observe(this) {
                    finish()
                }
        }

    }
}