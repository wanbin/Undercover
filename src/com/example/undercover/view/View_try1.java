package com.example.undercover.view;
public class View_try1 extends LinearLayout {
    private ImageView imageView;
    private TextView  textView;
    public View_try1(Context context) {
        // TODO Auto-generated constructor stub
        super(context);
    }
    public View_try1(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view, this);
        imageView=(ImageView) findViewById(R.id.imageView1);
        textView=(TextView)findViewById(R.id.textView1);
    }


}