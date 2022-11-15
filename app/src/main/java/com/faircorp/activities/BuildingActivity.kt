package com.faircorp.activities
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.faircorp.R
import com.faircorp.actions.BuildingAction
import com.faircorp.actions.OnBuildingSelectedListener
import com.faircorp.datamodels.RootDataModel
import com.faircorp.datamodels.BuildingDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext





class BuildingActivity : RootActivity(),OnBuildingSelectedListener {
    private val viewModel: BuildingDataModel by viewModels { BuildingDataModel.factory }

    val adapter = BuildingAction(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val recyclerView = findViewById<RecyclerView>(R.id.Building_List)

        val fab = findViewById<FloatingActionButton>(R.id.mainActionButton)
        fab.setOnClickListener {

            val intent= Intent(this, CreateBuildingActivity::class.java)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

    }




    override fun onBuildingSelected(id: Long) { val intent = Intent(this, RoomActivity::class.java)
        .putExtra(BUILDING_ID, id)
        startActivity(intent)
    }


    override fun onBuildingChange(id: Long) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_show)
        dialog.show()
        dialog.findViewById<Button>(R.id.btnDelete).setOnClickListener {

            viewModel.deleteBuilding(id).observe(this) {

                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@BuildingActivity, "Building deleted", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                }
            }

        }




        dialog.findViewById<Button>(R.id.btnEdit).setOnClickListener {
            val building =adapter.getBuildingById(id)
            val intent = Intent(this, CreateBuildingActivity::class.java).putExtra(BUILDING_ID, id)
                .putExtra(BUILDING_NAME, building?.name).putExtra(
                    BUILDING_ADDRESS, building?.address
                )

            startActivity(intent)
            dialog.dismiss()
        }



    }


    override fun onResume() {
        super.onResume()
        viewModel.findAll().observe(this) { buildings ->
            adapter.update(buildings)
            viewModel.networkState.observe(this) { state ->
                if(state == RootDataModel.State.OFFLINE) {
                    Toast.makeText(this,"Offline ", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

}