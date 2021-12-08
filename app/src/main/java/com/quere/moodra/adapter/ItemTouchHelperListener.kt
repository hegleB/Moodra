package com.quere.moodra.adapter

interface ItemTouchHelperListener {
    fun onItemMove(fromPos:Int, targetPos: Int)
    fun onItemDismiss(pos:Int)
}