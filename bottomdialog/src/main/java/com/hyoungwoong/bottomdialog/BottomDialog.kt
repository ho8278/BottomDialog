package com.hyoungwoong.bottomdialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class BottomDialog : DialogFragment {
    companion object {
        var TAG = BottomDialog::class.java.simpleName
        lateinit var positiveText: String
        lateinit var negativeText: String
        private var positiveTextColor: Int = Color.parseColor("#28A0FF")
        private var negativeTextColor: Int = Color.parseColor("#000000")
        private var positiveClickListener: TextViewClickListener? = null
        private var negativeClickListener: TextViewClickListener? = null
        private var isPositiveTextView = false
        private var isNegativeTextView = false
        private var layoutResID: Int? = null
        private var messageBody:String = ""
    }
    private var defaultTextSize = 16.0f
    private constructor() : super()
    private val baseLayout = R.layout.basedialog
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
        var textLinearView: View? = null
        if (isNegativeTextView || isPositiveTextView) {
            textLinearView = createTextView()
        }
        var view = inflater.inflate(baseLayout,container,false) as LinearLayout
        view.apply {
            if (textLinearView != null)
                addView(textLinearView)
            if (layoutResID != null) {
                var userView = inflater.inflate(layoutResID!!, this, false)
                addView(userView, 0)
            }
            if(!messageBody.isEmpty())
                view.findViewById<TextView>(R.id.tv_msg).text = messageBody
        }
        return view
    }

    private fun createTextView(): View {
        var horizontalLinearLayout = LinearLayout(context)
        //Button 있는 리니어레이아웃
        horizontalLinearLayout.apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .run {
                    setMargins(0, 8, 0, 0)
                    this
                }


            if (isNegativeTextView) {
                val negativeTextView = TextView(context)
                negativeTextView.apply {
                    setText(negativeText)
                    setTextColor(negativeTextColor)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP,defaultTextSize)
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    setOnClickListener {
                        negativeClickListener?.onClick()
                    }
                }
                addView(negativeTextView)
            }


            if (isPositiveTextView) {
                val positiveTextView = TextView(context)
                positiveTextView.apply {
                    setText(positiveText)
                    setTextColor(positiveTextColor)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP,defaultTextSize)
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        .run {
                            setMargins(32, 0, 32, 0)
                            this
                        }
                    setOnClickListener {
                        positiveClickListener?.onClick()
                    }
                }
                addView(positiveTextView)
            }
        }
        return horizontalLinearLayout
    }

    class BottomDialogBuilder {
        private var cancel = true
        private val instance: BottomDialog

        init {
            instance = BottomDialog()
        }

        /*

        fun setPositiveTextView(text: String = "확인", color: Int = Color.parseColor("#28A0FF"), listener: TextViewClickListener? = null): BottomDialogBuilder {
            positiveText = text
            positiveTextColor = color
            positiveClickListener = listener
            isPositiveTextView = true
            return this
        }

        fun setNegativeTextView(text: String = "취소", color: Int = Color.parseColor("#000000"), listener: TextViewClickListener? = null): BottomDialogBuilder {
            negativeText = text
            negativeTextColor = color
            negativeClickListener = listener
            isNegativeTextView = true
            return this
        }

        */

        fun setPositiveTextView(text: String = "확인", color: Int = Color.parseColor("#28A0FF"), listener: () -> Unit = { instance.dismiss() }): BottomDialogBuilder {
            positiveText = text
            positiveTextColor = color
            positiveClickListener = object : TextViewClickListener {
                override fun onClick() {
                    listener()
                }
            }
            isPositiveTextView = true
            return this
        }

        fun setNegativeTextView(text: String = "취소", color: Int = Color.parseColor("#000000"), listener: () -> Unit = { instance.dismiss() }): BottomDialogBuilder {
            negativeText = text
            negativeTextColor = color
            negativeClickListener = object : TextViewClickListener {
                override fun onClick() {
                    listener()
                }
            }
            isNegativeTextView = true
            return this
        }

        fun setLayout(layoutRes: Int): BottomDialogBuilder {
            layoutResID = layoutRes
            return this
        }

        fun setCancelable(isCancelable: Boolean): BottomDialogBuilder {
            cancel = isCancelable
            return this
        }

        fun setMessage(msg:String):BottomDialogBuilder{
            messageBody = msg
            return this
        }

        fun build(manager: FragmentManager) {
            instance.isCancelable = cancel
            instance.show(manager, TAG)
        }
    }

    interface TextViewClickListener {
        fun onClick()
    }
}