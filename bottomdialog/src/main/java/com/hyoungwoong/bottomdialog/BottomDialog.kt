package com.hyoungwoong.bottomdialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class BottomDialog : DialogFragment {
    var positiveText: String? = null
    var negativeText: String? = null
    var positiveTextColor: Int = Color.parseColor("#28A0FF")
    var negativeTextColor: Int = Color.parseColor("#000000")
    var positiveClickListener : ButtonClickListener? = null
    var negativeClickListener : ButtonClickListener? = null
    var isButtonView: Boolean = false
    var layoutResID: Int? = null
    private constructor():super()
    //Builder 클래스 추가
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomDialog)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val params = window?.attributes
        params?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
            window.attributes = this
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        //기능 확장: 타이틀 붙일수 있게 Builder 함수 추가
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var buttonLinearView: View? = null
        if (isButtonView) {
            buttonLinearView = createButtonView()
        }
        var view = LinearLayout(context)
        view.apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if(buttonLinearView != null)
                view.addView(buttonLinearView)
            if(layoutResID != null){
                var userView = inflater.inflate(layoutResID!!,this,false)
                addView(userView,0)
            }
        }
        return view
    }

    private fun createButtonView(): View {
        var horizontalLinearLayout = LinearLayout(context)
        //Button 있는 리니어레이아웃
        horizontalLinearLayout.apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).run {
                setMargins(0,8,0,0)
                this
            }
            val positiveTextView = TextView(context)
            positiveTextView.apply {
                setText(positiveText ?: "확인")
                setTextColor(positiveTextColor)
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).run {
                    setMargins(32, 0, 32, 0)
                    this
                }
                setOnClickListener {
                    positiveClickListener?.onClick()
                }
            }
            val negativeTextView = TextView(context)
            negativeTextView.apply {
                setText(negativeText ?: "취소")
                setTextColor(negativeTextColor)
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    negativeClickListener?.onClick()
                }
            }
            addView(negativeTextView)
            addView(positiveTextView)
        }
        return horizontalLinearLayout
    }


    interface ButtonClickListener{
        fun onClick()
    }
}