package com.demon.yu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by yujintao on 2018/1/17.
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }

    inner class Adapter(private val dataList: MutableList<Any>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.onBind(dataList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return when(viewType){
                0-> {
                    HotelRoomViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.hotel_room_view_holder,null))
                }
                else->{
                    RoomModelViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.room_model_view_holder,null))
                }
            }
        }

        override fun getItemCount() = dataList.size

        override fun getItemViewType(position: Int) = when (dataList[position]) {
            is HotelRoom -> 0
            else -> 1
        }

        private fun getData(position:Int)=dataList[position]

    }

    abstract class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        abstract fun onBind(data:Any)
    }


}