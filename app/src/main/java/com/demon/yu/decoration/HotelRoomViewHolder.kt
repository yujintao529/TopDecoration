package com.demon.yu.decoration

import android.view.View
import kotlinx.android.synthetic.main.hotel_room_view_holder.*

/**
 * @description
 * @author yujintao
 * @date 2018/1/18
 *
 */
class HotelRoomViewHolder(view: View) : MainActivity.ViewHolder(view) {
    override fun onBind(t: Any) {
        var hotelRoom = t as HotelRoom
        title.text = hotelRoom.hotelTitle
        hotelImage.setImageResource(hotelRoom.hotelImage)
        wifi.text=hotelRoom.wifi
        price.text=hotelRoom.price
    }
}
