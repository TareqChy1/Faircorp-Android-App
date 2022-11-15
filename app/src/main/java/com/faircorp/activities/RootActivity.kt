package com.faircorp.activities
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.faircorp.R





val BUILDING_ID = "buildingId"
val BUILDING_NAME = "buildingName"
val BUILDING_ADDRESS = "buildingAddress"
val ROOM_ID = "roomId"
val ROOM_NAME = "roomName"
val ROOM_FLOOR = "roomFloor"
val ROOM_TARGET_TEMPERATURE = "roomTargetTemperature"
val ROOM_CURRENT_TEMPERATURE = "roomCurrentTemperature"
val WINDOW_ID= "windowId"
val Window_NAME = "windowName"
val Window_STATUS = "windowStatus"
val HEATER_ID = "heaterId"
val HEATER_NAME = "heaterName"
val HEATER_POWER = "heaterPower"
val HEATER_STATUS = "heaterStatus"



open class RootActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.menu_website -> startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://faircorp-tareq-chy.cleverapps.io/"))
            )
            R.id.menu_email -> startActivity(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto://tareqfarhadbd@gmail.com"))
            )

        }
        return super.onContextItemSelected(item)
    }

}