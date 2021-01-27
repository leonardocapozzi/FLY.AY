package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.SubCategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.flam.flyay.R.color.colorAccent;

public class AddEventSubCategoryFragment extends Fragment {

    private LinearLayout linearLayout;
    private String btnString;

    private List<String> freeTimeSubCategoryList;
    private List<String> studySubCategoryList;
    private List<String> wellnessSubCategoryList;
    private List<String> festivitySubCategoryList;
    private List<String> financesSubCategoryList;


    public AddEventSubCategoryFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_sub_category_fragment, container, false);

        linearLayout = view.findViewById(R.id.add_event_sub_category_fragment);

        Bundle arguments = getArguments();
        btnString = arguments.getString("btnString");

        freeTimeSubCategoryList = Arrays.asList(SubCategoryEnum.FRIENDS.name, SubCategoryEnum.FAMILY.name, SubCategoryEnum.HOBBY.name,
                SubCategoryEnum.TRAVELS.name, SubCategoryEnum.FILMS_TV_SERIES.name, SubCategoryEnum.THEATRE.name, SubCategoryEnum.MUSIC.name,
                SubCategoryEnum.SPORTIVE_EVENTS.name, SubCategoryEnum.SPORT.name, SubCategoryEnum.OTHER.name);

        studySubCategoryList = Arrays.asList(SubCategoryEnum.EXAM.name, SubCategoryEnum.STUDY_TIME.name, SubCategoryEnum.LESSONS.name,
                SubCategoryEnum.TEACHERS_OFFICE_HOURS.name, SubCategoryEnum.STUDY_GROUP.name, SubCategoryEnum.INTERSHIP.name);

        wellnessSubCategoryList = Arrays.asList(SubCategoryEnum.BODY_CARE.name, SubCategoryEnum.MEDICINES.name, SubCategoryEnum.MED_APPOINTMENT.name);

        festivitySubCategoryList = Arrays.asList(SubCategoryEnum.BIRTHDAY.name, SubCategoryEnum.LONG_WEEKEND.name, SubCategoryEnum.HOLIDAYS.name);

        financesSubCategoryList = Arrays.asList(SubCategoryEnum.REVENUE.name, SubCategoryEnum.OUTFLOW.name);

        if (btnString == CategoryEnum.FREE_TIME.name) {
            addTextViewAndButtons("Which subcategory?", freeTimeSubCategoryList);
        }
        if (btnString == CategoryEnum.STUDY.name) {
            addTextViewAndButtons("Which subcategory?", studySubCategoryList);
        }
        if (btnString == CategoryEnum.WELLNESS.name) {
            addTextViewAndButtons("Which subcategory?", wellnessSubCategoryList);
        }
        if (btnString == CategoryEnum.FINANCES.name) {
            addTextViewAndButtons("Which subcategory?", financesSubCategoryList);
        }
        if (btnString == CategoryEnum.FESTIVITY.name) {
            addTextViewAndButtons("Which subcategory?", festivitySubCategoryList);
        }

        return view;
    }


    public void addTextView(LinearLayout layout, String text, Integer marginLeft, Integer marginTop) {

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop), 0, 0);
        textView.setLayoutParams(textParams);

        layout.addView(textView);
    }

    public void horizontalScrollView(LinearLayout mainLayout, LinearLayout scrollableLayout) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        horizontalScrollView.setLayoutParams(scrollParams);
        horizontalScrollView.addView(scrollableLayout);

        mainLayout.addView(horizontalScrollView);
    }

    public void addButtons(LinearLayout layout, List<String> subCategoryList) {

        final LinearLayout buttonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(buttonsParams);

        horizontalScrollView(layout, buttonsLayout);

        final List<Button> buttons = new ArrayList<>();

        for (final Object i : subCategoryList) {
            final Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            buttons.add(btn);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btnparams.setMargins(Utils.convertDpToPixel(8), Utils.convertDpToPixel(8), 0, 0);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setLayoutParams(btnparams);
            buttonsLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                public void onClick(View view) {

                    Drawable background = btn.getBackground();
                    int color = Color.TRANSPARENT;

                    if(background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    clearOtherButtonBackground(buttons, btn);
                    switch(btnString) {
                        case "FINANCES":
                            changeStatusButton(color, btn, CategoryEnum.FINANCES);
                            break;
                        case "WELLNESS":
                            changeStatusButton(color, btn, CategoryEnum.WELLNESS);
                            break;
                        case "FESTIVITY":
                            changeStatusButton(color, btn, CategoryEnum.FESTIVITY);
                            break;
                        case "STUDY":
                            changeStatusButton(color, btn, CategoryEnum.STUDY);
                            break;
                        case "FREE_TIME":
                            changeStatusButton(color, btn, CategoryEnum.FREE_TIME);
                            break;
                    }
                }
            });

        }
    }

    private void changeStatusButton(int color, Button btn, CategoryEnum categoryEnum) {
        if(color == Color.TRANSPARENT) {
            //TODO implements 'GET VALUE' logic
            btn.setBackgroundColor(Color.parseColor(categoryEnum.color));
        } else
            btn.setBackgroundColor(Color.TRANSPARENT);
    }

    private void clearOtherButtonBackground(List<Button> buttons, Button excluding) {
        for(Button btn: buttons){
            if(btn != excluding)
                btn.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void addTextViewAndButtons(String text, final List<String> categoryList) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addTextView(layout, text, 16, 16);
        addButtons(layout, categoryList);

    }

}
