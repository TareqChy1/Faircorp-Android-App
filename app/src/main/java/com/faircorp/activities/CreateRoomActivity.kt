package com.faircorp.activities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.faircorp.R
import com.faircorp.dto.RoomDto
import com.faircorp.datamodels.RoomDataModel



class CreateRoomActivity : RootActivity() {
    private val viewModel: RoomDataModel by viewModels { RoomDataModel.factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        val id = intent.getLongExtra(ROOM_ID, 0)
        val buildingId = intent.getLongExtra(BUILDING_ID, 0)
        val editTextName = findViewById<EditText>(R.id.CreateRoomName)
        val editTextFloor = findViewById<EditText>(R.id.CreateRoomFloor)
        val editTextCurrentTemperature = findViewById<EditText>(R.id.CreateCurrentTemperature)
        val editTextTargetTemperature = findViewById<EditText>(R.id.CreateTargetTemperature)
        val saveButton = findViewById<Button>(R.id.CreateRoom)
        if (intent.getStringExtra(ROOM_NAME) != null) {
            editTextName.setText(intent.getStringExtra(ROOM_NAME))
            editTextFloor.setText(intent.getLongExtra(ROOM_FLOOR,0).toString())
            editTextCurrentTemperature.setText(intent.getDoubleExtra(ROOM_CURRENT_TEMPERATURE, 0.0).toString())
            editTextTargetTemperature.setText(intent.getDoubleExtra(ROOM_TARGET_TEMPERATURE, 0.0).toString())
        }


        saveButton.setOnClickListener {
            val name = editTextName.text.toString()
            val floor = editTextFloor.text.toString().toLongOrNull()
            val currentTemperature = editTextCurrentTemperature.text.toString().toDoubleOrNull()
            val targetTemperature = editTextTargetTemperature.text.toString().toDoubleOrNull()
            viewModel.createRoom(
                RoomDto(
                    id = if (id == 0L) null else id,
                    name = name,
                    floor = floor,
                    currentTemperature = currentTemperature,
                    targetTemperature = targetTemperature,
                    buildingId = buildingId
                )
            )

                .observe(this) {
                    finish()
                }

        }


    }
}