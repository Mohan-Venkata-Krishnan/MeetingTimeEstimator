package com.app.meetingtimeestimator.visualizer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.meetingtimeestimator.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TotalOwnerCountVisualizer {

    public static void renderChartData(TextView ownerCountText,
                                       LinearLayout ownerLinearLayout,
                                       Context context,
                                       boolean defaultAcceptanceCriteria,
                                       HashMap<String, Integer> calendarOwnerCountMap) {
        if (defaultAcceptanceCriteria) {
            ownerCountText.setText("Top Contributors Count - Accepted Events");
        } else {
            ownerCountText.setText("Top Contributors Count - Overall Events");
        }
        ownerCountText.setVisibility(View.VISIBLE);
        calendarOwnerCountMap = StringUtils.sortByValue(calendarOwnerCountMap);

        for (Map.Entry<String, Integer> entrySet : calendarOwnerCountMap.entrySet()) {
            CardView cardView = new CardView(context);
            cardView.setCardElevation(10f);
            cardView.setMaxCardElevation(12f);
            cardView.setUseCompatPadding(true);
            CardView.LayoutParams cardViewLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cardViewLayoutParams.leftMargin = 20;
            cardViewLayoutParams.rightMargin = 20;
            cardView.setLayoutParams(cardViewLayoutParams);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(3);
            TextView ownerText = new TextView(context);
            String ownerEmail = entrySet.getKey();
            if (ownerEmail.contains("@")) {
                ownerEmail = ownerEmail.substring(0, ownerEmail.indexOf("@"));
            }
            ownerText.setText(ownerEmail);
            ownerText.setTextSize(15f);
            ownerText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ownerText.setPadding(30, 30, 30, 30);
            LinearLayout.LayoutParams ownerTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1);
            ownerTextLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            ownerText.setLayoutParams(ownerTextLayoutParams);
            linearLayout.addView(ownerText, 0);

            TextView countText = new TextView(context);
            countText.setText(entrySet.getValue().toString());
            countText.setTextSize(15f);
            countText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            countText.setPadding(30, 30, 30, 30);
            countText.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams countTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 2);
            countTextLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            countText.setLayoutParams(countTextLayoutParams);
            linearLayout.addView(countText, 1);
            cardView.addView(linearLayout);
            ownerLinearLayout.addView(cardView);
        }

    }
}
