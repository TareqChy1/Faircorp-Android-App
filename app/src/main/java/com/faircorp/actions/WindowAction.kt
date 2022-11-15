package com.faircorp.actions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus



interface WindowListener {
    fun onWindowSwitched(id: Long)
    fun onWindowChange(id: Long)
}


class WindowAction(private val listener: WindowListener) : RecyclerView.Adapter<WindowAction.WindowViewHolder>() {
    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.window_name)
        val switch: SwitchCompat = view.findViewById(R.id.window_switch)
    }
    private val items = mutableListOf<WindowDto>()
    fun update(windows: List<WindowDto>) {
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.window_list, parent, false)
        return WindowViewHolder(view)
    }


    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {
        val window = items[position]
        holder.apply {
            name.text = window.name
            switch.isChecked = window.windowStatus == WindowStatus.OPEN
            switch.setOnCheckedChangeListener { _, _ ->
                listener.onWindowSwitched(window.id!!)
            }
            itemView.setOnClickListener {
                listener.onWindowChange(window.id!!)
            }
        }
    }


    override fun onViewRecycled(holder: WindowViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            switch.setOnCheckedChangeListener(null)
        }
    }
    fun getWindowById(id: Long): WindowDto? {
        return items.find { it.id == id }
    }


}