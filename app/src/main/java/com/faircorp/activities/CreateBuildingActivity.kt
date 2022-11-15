package com.faircorp.activities
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.faircorp.R
import com.faircorp.dto.BuildingDto
import com.faircorp.datamodels.BuildingDataModel




class CreateBuildingActivity : AppCompatActivity() {
    private val viewModel: BuildingDataModel by viewModels {BuildingDataModel.factory}

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_building)
        val editTextBuildingName = findViewById<EditText>(R.id.CreateBuildingName)
        val editTextBuildingAddress = findViewById<EditText>(R.id.CreateBuildingAddress)
        val submitButton = findViewById<Button>(R.id.CreateBuilding)
        val id = intent.getLongExtra(BUILDING_ID, 0)
        if (intent.getStringExtra(BUILDING_NAME) != null) {
            editTextBuildingName.setText(intent.getStringExtra(BUILDING_NAME))
            editTextBuildingAddress.setText(intent.getStringExtra(BUILDING_ADDRESS))
        }
        submitButton.setOnClickListener {
            val buildingName = editTextBuildingName.text.toString()
            val buildingAddress = editTextBuildingAddress.text.toString()
            if (id != 0L) {
                viewModel.createBuilding(
                    BuildingDto(
                        id=id,
                        name = buildingName,
                        address = buildingAddress
                    )
                ).observe(this) {

                    Toast.makeText(this, "Building Updated", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                viewModel.createBuilding(
                    BuildingDto(
                        name = buildingName,
                        address = buildingAddress
                    )
                ).observe(this) {

                    Toast.makeText(this, "Building created", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

}