package vn.com.phongnguyen93.justdoit.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.AnimationsSet;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Scale;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.contants.TYPE;
import vn.com.phongnguyen93.justdoit.utilities.PreferencesUtility;
import vn.com.phongnguyen93.justdoit.R;
import vn.com.phongnguyen93.justdoit.utilities.Utilities;
import vn.com.phongnguyen93.justdoit.activities.EditActivity;

/**
 * Created by phongnguyen on 2/7/17.
 */

public class WelcomeFragment extends Fragment {
  public static WelcomeFragment newInstance() {
    Bundle args = new Bundle();
    WelcomeFragment fragment = new WelcomeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_welcome, container, false);

    final LinearLayout nameInputLayout = (LinearLayout) view.findViewById(R.id.name_input_layout);
    final EditText edtName = (EditText) view.findViewById(R.id.edt_name);
    Button button = (Button) view.findViewById(R.id.btn_start);

    button.setBackground(Utilities.generateGradientDrawable(new int[] {
        ContextCompat.getColor(getContext(), R.color.colorPrimary),
        ContextCompat.getColor(getContext(), R.color.colorAccent)
    }));

    final Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.ttf"));

    final TextSurface textSurface = (TextSurface) view.findViewById(R.id.welcome_textsurface);
    Text txtHi = TextBuilder.create(getString(R.string.welcome_1_msg))
        .setPaint(paint)
        .setSize(20)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtHowYourDay = TextBuilder.create(getString(R.string.welcome_2_msg))
        .setPaint(paint)
        .setSize(18)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtHowManyTask = TextBuilder.create(getString(R.string.welcome_3_msg))
        .setPaint(paint)
        .setSize(18)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtDoYou = TextBuilder.create(getString(R.string.welcome_4_msg))
        .setPaint(paint)
        .setSize(18)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtYup = TextBuilder.create(getString(R.string.welcome_5_msg))
        .setPaint(paint)
        .setSize(18)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtWhyNot = TextBuilder.create(getString(R.string.welcome_6_msg))
        .setPaint(paint)
        .setSize(20)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtJustDoIt = TextBuilder.create(getString(R.string.app_name))
        .setPaint(paint)
        .setSize(26)
        .setAlpha(0)
        .setColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtLetMe = TextBuilder.create(getString(R.string.welcome_7_msg))
        .setPaint(paint)
        .setSize(18)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    Text txtButFirst = TextBuilder.create(getString(R.string.welcome_8_msg))
        .setPaint(paint)
        .setSize(18)
        .setAlpha(0)
        .setColor(Color.WHITE)
        .setPosition(Align.SURFACE_CENTER)
        .build();

    textSurface.play(new Sequential(
        new AnimationsSet(TYPE.SEQUENTIAL, Alpha.show(txtHi, 1000), Alpha.hide(txtHi, 1000),
            Slide.showFrom(Side.LEFT, txtHowYourDay, 1000), Delay.duration(1000),
            Slide.hideFrom(Side.RIGHT, txtHowYourDay, 500),
            Slide.showFrom(Side.LEFT, txtHowManyTask, 1000), Delay.duration(1000),
            Slide.hideFrom(Side.RIGHT, txtHowManyTask, 500),
            Slide.showFrom(Side.LEFT, txtDoYou, 1000), Delay.duration(1000),
            Slide.hideFrom(Side.RIGHT, txtDoYou, 500),
            ShapeReveal.create(txtYup, 1000, SideCut.show(Side.LEFT), false),
            new Scale(txtYup, 500, 1, 2, 10), Alpha.hide(txtYup, 500),
            ShapeReveal.create(txtWhyNot, 1000, SideCut.show(Side.LEFT), false),
            Delay.duration(1000), Alpha.hide(txtWhyNot, 500),
            ShapeReveal.create(txtJustDoIt, 1000, SideCut.show(Side.CENTER), false), new Parallel(
            ChangeColor.fromTo(txtJustDoIt, 1500,
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.colorPrimary)),
            new Scale(txtJustDoIt, 1500, 1, 2, 10)), Delay.duration(1000),
            Alpha.hide(txtJustDoIt, 500), Slide.showFrom(Side.BOTTOM, txtLetMe, 1000),
            Delay.duration(1000), Slide.hideFrom(Side.BOTTOM, txtLetMe, 500),

            Slide.showFrom(Side.BOTTOM, txtButFirst, 1000), Delay.duration(1000),
            Slide.hideFrom(Side.BOTTOM, txtButFirst, 500))));

    final Handler handler = new Handler(Looper.getMainLooper());
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        nameInputLayout.setVisibility(View.VISIBLE);
      }
    }, 24200);

    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (!TextUtils.isEmpty(edtName.getText().toString())) {
          PreferencesUtility.getInstance(getContext())
              .setString(PreferencesUtility.PRE_KEY_USER, edtName.getText().toString());

          nameInputLayout.setVisibility(View.GONE);

          Text txtOke = TextBuilder.create(
              String.format(getString(R.string.welcome_9_msg), edtName.getText().toString()))
              .setPaint(paint)
              .setSize(18)
              .setAlpha(0)
              .setColor(Color.WHITE)
              .setPosition(Align.SURFACE_CENTER)
              .build();


          Text txtCreate = TextBuilder.create(getString(R.string.welcome_10_msg))
              .setPaint(paint)
              .setSize(18)
              .setAlpha(0)
              .setColor(Color.WHITE)
              .setPosition(Align.SURFACE_CENTER)
              .build();

          textSurface.play( new AnimationsSet(TYPE.SEQUENTIAL,ShapeReveal.create(txtOke, 1000, SideCut.show(Side.LEFT), false),
              Delay.duration(1000), Alpha.hide(txtOke, 500),
              ShapeReveal.create(txtCreate, 1000, SideCut.show(Side.LEFT), false),
              Delay.duration(1000), Alpha.hide(txtCreate, 500)));

          handler.postDelayed(new Runnable() {
            @Override public void run() {
              startActivity(new Intent(getContext(),EditActivity.class));
            }
          },5100);
        }
      }
    });

    return view;
  }
}
