package com.hyoungwoong.bottomdialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

class BottomDialog : DialogFragment, DialogInterface{
    companion object {
        var TAG = BottomDialog::class.java.simpleName
        lateinit var positiveText: CharSequence
        lateinit var negativeText: CharSequence
        private var positiveTextColor: Int = Color.parseColor("#28A0FF")
        private var negativeTextColor: Int = Color.parseColor("#000000")
        private var positiveClickListener: OnClickListener? = null
        private var positiveBlock:(DialogInterface)->Unit = {}
        private var negativeClickListener: OnClickListener? = null
        private var negativeBlock:(DialogInterface)->Unit = {}
        private var isPositiveTextView = false
        private var isNegativeTextView = false
        private var layoutResID: Int? = null
        private var messageBody:String = ""
        private var title:String = ""
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
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //dialog.setTitle("TEST")
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
            if(!title.isEmpty()){
                findViewById<TextView>(R.id.tv_title).text = title
            }
            else
                removeView(findViewById<TextView>(R.id.tv_title))

            if (layoutResID != null) {
                var userView = inflater.inflate(layoutResID!!, this, false)
                if(title.isEmpty())
                    addView(userView, 0)
                else
                    addView(userView,1)
            }

            if(!messageBody.isEmpty())
                findViewById<TextView>(R.id.tv_msg).text = messageBody
            else
                removeView(findViewById<TextView>(R.id.tv_msg))
        }
        return view
    }

    override fun cancel() {
        super.onCancel(this)
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
                        if(negativeClickListener==null)
                            negativeBlock(this@BottomDialog)
                        else
                            negativeClickListener?.OnClick(this@BottomDialog)
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
                        if(positiveClickListener == null)
                            positiveBlock(this@BottomDialog)
                        else
                            positiveClickListener?.OnClick(this@BottomDialog)
                    }
                }
                addView(positiveTextView)
            }
        }
        return horizontalLinearLayout
    }

    class Builder(private val context:Context) {
        private var cancel = true
        private val instance: BottomDialog

        init {
            instance = BottomDialog()
        }


        fun setPositiveTextView(@StringRes textId: Int, listener: OnClickListener? = null): Builder {
            positiveText = context.getText(textId)
            positiveClickListener = listener
            isPositiveTextView = true
            return this
        }

        fun setPositiveTextView(text: String, listener: OnClickListener? = null): Builder {
            positiveText = text
            positiveClickListener = listener
            isPositiveTextView = true
            return this
        }

        fun setPositiveTextView(@StringRes textId: Int, listener: (DialogInterface)->Unit): Builder {
            positiveText = context.getText(textId)
            positiveBlock = listener
            isPositiveTextView = true
            return this
        }

        fun setPositiveTextView(text: String, listener: (DialogInterface)->Unit): Builder {
            positiveText = text
            positiveBlock = listener
            isPositiveTextView = true
            return this
        }

        fun setPositiveTextViewColor(@ColorRes color:Int): Builder{
            positiveTextColor = ContextCompat.getColor(context,color)
            return this
        }

        fun setPositiveTextViewColor(color:String):Builder{
            positiveTextColor = Color.parseColor(color)
            return this
        }

        fun setNegativeTextView(@StringRes textId: Int, listener: OnClickListener? = null): Builder {
            negativeText =  instance.context?.getText(textId).toString()
            negativeClickListener = listener
            isNegativeTextView = true
            return this
        }

        fun setNegativeTextView(text: String, listener: OnClickListener? = null): Builder {
            negativeText = text
            negativeClickListener = listener
            isNegativeTextView = true
            return this
        }

        fun setNegativeTextView(@StringRes textId: Int, listener: (DialogInterface)->Unit): Builder {
            negativeText =  instance.context?.getText(textId).toString()
            negativeBlock = listener
            isNegativeTextView = true
            return this
        }

        fun setNegativeTextView(text: String, listener: (DialogInterface)->Unit): Builder {
            negativeText = text
            negativeBlock = listener
            isNegativeTextView = true
            return this
        }

        fun setNegativeTextViewColor(@ColorRes color:Int): Builder{
            negativeTextColor = ContextCompat.getColor(context,color)
            return this
        }

        fun setNegativeTextViewColor(color:String):Builder{
            negativeTextColor = Color.parseColor(color)
            return this
        }

        fun setLayout(layoutRes: Int): Builder {
            layoutResID = layoutRes
            return this
        }

        fun setCancelable(isCancelable: Boolean): Builder {
            cancel = isCancelable
            return this
        }

        fun setMessage(msg:String):Builder{
            messageBody = msg
            return this
        }

        fun setTitle(str:String):Builder{
            title = str
            return this
        }

        fun build(manager: FragmentManager) {
            instance.isCancelable = cancel
            instance.show(manager, TAG)
        }
    }

    interface OnClickListener{
        fun OnClick(listener:DialogInterface)
    }
}