package com.faircorp.actions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.dto.RoomDto



interface RoomListener {
    fun onWindowClicked(id: Long)
    fun onHeaterClicked(id: Long)
    fun onRoomChange(id: Long)
}


class RoomAction(private val listener: RoomListener): RecyclerView.Adapter<RoomAction.RoomViewHolder>() { inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.Building)
        val floor: TextView = view.findViewById(R.id.floor)
        val currentTemperature: TextView = view.findViewById(R.id.current_temperature)
        val targetTemperature: TextView = view.findViewById(R.id.target_temperature)
        val Windows: Button = view.findViewById(R.id.windows)
        val Heaters: Button = view.findViewById(R.id.heaters)
    }

    private val items = mutableListOf<RoomDto>()
    fun update(rooms: List<RoomDto>) {
        items.clear()
        items.addAll(rooms)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_list, parent, false)
        return RoomViewHolder(view)
    }


    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) { val room = items[position]
        holder.apply {
            name.text = room.name
            floor.text =  room.floor.toString()
            currentTemperature.text = room.currentTemperature.toString()
            targetTemperature.text = room.targetTemperature.toString()
            Windows.setOnClickListener {
                listener.onWindowClicked(room.id!!)
            }
            Heaters.setOnClickListener {
                listener.onHeaterClicked(room.id!!)
            }
            itemView.setOnClickListener {
                listener.onRoomChange(room.id!!)
            }

        }
    }



    override fun onViewRecycled(holder: RoomViewHolder) { super.onViewRecycled(holder)
        holder.apply {
            Windows.setOnClickListener(null)
            Heaters.setOnClickListener(null)
        }
    }
    fun getRoomById(id: Long): RoomDto? {
        return items.find { it.id == id }
    }


}