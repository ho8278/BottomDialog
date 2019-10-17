# BottomDialog
BottomDialog는 Dialog를 하단에 위치시켜 중앙에 있을 때보다 한 손으로 터치하는게 쉬운 Dialog입니다. 또한 모든 layout을 간단하고 쉽게 Dialog에 포함 시킬 수 있습니다. 

AlertDialog처럼 Positive,NegativeButton을 지원합니다.

## Preview

## Usage
```kotlin
BottomDialog.Builder(applicationContext)
        .setLayout(R.layout.layout1)
        .setPositiveTextView("확인", object:BottomListener.OnClickListener{
            override fun OnClick(listener:BottomListener){
                
            }
        })
        .setPositiveTextViewColor(R.color.colorAccent)
        .setNegativeTextView(...)
        .setMessage("Message")
        .setCancelable(true)
        .build()
```

## Import


