package com.demon.yu.decoration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by yujintao on 2018/1/17.
 */
class MainActivity : AppCompatActivity() {


    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        initData()

    }


    //hotelroom
    private val hotelImage = arrayOf(R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3, R.drawable.hotel4, R.drawable.hotel5, R.drawable.hotel6)
    private val hotelWifi = arrayOf("没有wifi", "免费wifi", "付费wifi")
    private val hotelTitle = arrayOf("豪华大床房", "经典双人房", "标准房", "大床房", "家庭套房", "小型套房")
    private val hotelBed = arrayOf("一张大床", "一张大床或者两个单人床", "豪华大床(2.0米)", "一张小床", "两张小床")
    private val hotelMianJi = arrayOf("36m2", "18m2", "25m2", "15m2", "80m2")
    private val hotelPrice = arrayOf("360元每晚", "180元每晚", "250元每晚", "150元每晚", "300元每晚")

    //roommodel
    private val roomOta = arrayOf("携程", "去哪儿", "美团", "booking", "airbnb", "有鱼儿", "蚂蜂窝自由行")
    private val roomPersonNumber = arrayOf("最多两人", "最多一人", "最多三人")
    private val roomCancle = arrayOf("免费取消10点之前", "收取10%手续费", "免费取消12点之前", "收取20%手续费")
    private val roomBreakfirst = arrayOf("含早餐", "不含早餐", "超级豪华早餐", "低档早餐")
    private val roomPrice = arrayOf("357元每晚", "258元每晚", "615每晚", "115每晚")
    private val roomTax = arrayOf("税费30元", "税费25元", "税费21元", "税费10元")


    private val random = Random()

    private fun createHotelRoom(): HotelRoom {
        return HotelRoom(hotelImage.random(), hotelTitle.random(), hotelWifi.random(), hotelBed.random(), hotelMianJi.random(), hotelPrice.random())
    }

    private fun createRoomModel(): RoomModel {
        return RoomModel(roomOta.random(), roomPersonNumber.random(), roomCancle.random(), roomBreakfirst.random(), roomPrice.random(), roomTax.random())
    }

    private fun initData() {
        val listData = mutableListOf<Any>()
        for (i in 0..random.nextInt(4, 7)) {
            listData.add(createHotelRoom())
            for (j in 0..random.nextInt(2, 8)) {
                listData.add(createRoomModel())
            }
        }
        var adapter = Adapter(listData)
        recyclerView.addItemDecoration(TopDecoration(object : VirtualFamily {
            override fun isParentType(position: Int): Boolean {
                return listData[position] is HotelRoom
            }

            override fun parentChildren(position: Int): Int {
                var count = 0
                var p = position
                while (listData.size > position && isChildType(++p)) {
                    count++
                }
                return count

            }

            override fun isChildType(position: Int): Boolean {
                return listData[position] is RoomModel
            }

            override fun childParentPosition(childPosition: Int): Int {
                var parentPosition = childPosition
                while (parentPosition > 0 && !isParentType(--parentPosition)) {
                }
                return parentPosition
            }

        }, recyclerView, linearLayoutManager, frameLayout))
        recyclerView.adapter = adapter
    }


    private fun <T> randomValue(array: Array<T>) = array[random.nextInt(array.size)]


    inner class Adapter(private val dataList: MutableList<Any>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.onBind(dataList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return when (viewType) {
                0 -> {
                    HotelRoomViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.hotel_room_view_holder, null))
                }
                else -> {
                    RoomModelViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.room_model_view_holder, null))
                }
            }
        }

        override fun getItemCount() = dataList.size

        override fun getItemViewType(position: Int) = when (dataList[position]) {
            is HotelRoom -> 0
            else -> 1
        }

        private fun getData(position: Int) = dataList[position]

    }

    abstract class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun onBind(data: Any)
    }

    private fun <T> Array<T>.random() = randomValue(this)

    private fun Random.nextInt(start: Int, end: Int): Int {
        return start + this.nextInt(end - start)
    }
}