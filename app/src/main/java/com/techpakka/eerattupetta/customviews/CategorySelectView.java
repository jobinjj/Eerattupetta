package com.techpakka.eerattupetta.customviews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techpakka.eerattupetta.R;

public class CategorySelectView extends ConstraintLayout implements View.OnClickListener{

    private ConstraintLayout root;
    private ConstraintLayout container_home;
    private categoryItemSelectedListener categoryItemSelectedListener;

    public CategorySelectView(Context context) {
        super(context);
        init();
    }

    public CategorySelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategorySelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_category_select,this,true);
        root = view.findViewById(R.id.root);

        view.findViewById(R.id.container_home).setOnClickListener(this);
        view.findViewById(R.id.container_furniture).setOnClickListener(this);
        view.findViewById(R.id.container_car).setOnClickListener(this);
        view.findViewById(R.id.container_electronics).setOnClickListener(this);
        view.findViewById(R.id.container_mobile).setOnClickListener(this);
        view.findViewById(R.id.container_bike).setOnClickListener(this);


    }

    public void setOnCategoryClickListener(categoryItemSelectedListener categoryClickListener){
        this.categoryItemSelectedListener = categoryClickListener;
        ConstraintLayout homeContainer = (ConstraintLayout) root.getChildAt(3);
        TextView categoryText = (TextView) homeContainer.getChildAt(1);
        categoryClickListener.onCategorySelected(categoryText.getText().toString());
        homeContainer.setBackground(getResources().getDrawable(R.drawable.category_selected));

    }
    private void removeAllBgColor() {
        for (int i = 0; i < root.getChildCount(); i++) {
            ConstraintLayout container = (ConstraintLayout) root.getChildAt(i);
            container.setBackground(getResources().getDrawable(R.drawable.category_default));

        }
    }

    @Override
    public void onClick(View v) {
        removeAllBgColor();
        v.setBackground(getResources().getDrawable(R.drawable.category_selected));
        ViewGroup container = (ViewGroup) v;
        TextView categoryName = (TextView) container.getChildAt(1);
        categoryItemSelectedListener.onCategorySelected(categoryName.getText().toString());
    }

    public interface categoryItemSelectedListener{
        void onCategorySelected(String categoryName);
    }
}
