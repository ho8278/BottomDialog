package com.hyoungwoong.bottomdialog

interface BottomListener{
    fun cancel()
    fun dismiss()
    interface OnClickListener{
        fun OnClick(listener:BottomListener)
    }
}