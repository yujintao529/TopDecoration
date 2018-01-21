package com.demon.yu

import android.view.View
import kotlinx.android.synthetic.main.room_model_view_holder.*

/**
 * @description
 * @author yujintao
 * @date 2018/1/18
 *
 */
class RoomModelViewHolder(view: View) : MainActivity.ViewHolder(view) {

    override fun onBind(t: Any) {
        var roomModel: RoomModel = t as RoomModel
        ota.text = roomModel.ota
        personNumber.text = roomModel.person
        cancle.text = roomModel.cancle
        breakFirst.text = roomModel.breakfirst
        price.text = roomModel.price
        tax.text = roomModel.tax
    }

}