package edu.sqchen.iubao.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.UserService;
import edu.sqchen.iubao.model.entity.User;
import edu.sqchen.iubao.ui.util.Md5;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_repeatpassword)
    EditText mEtRepeatpassword;
    @BindView(R.id.bt_go)
    Button mBtGo;
    @BindView(R.id.cv_add)
    CardView mCvAdd;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tool_bar_register)
    Toolbar mToolBarRegister;

    private static final int SHOW_RESPONSE = 1;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Log.d("response",response.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
        initView();
    }

    private void initView() {
        mBtGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    Log.d("register","进行注册");
                    userRegister();
                } else {
                    Log.d("register","输入错误");
                    Toast.makeText(getApplicationContext(),"输入信息有误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 用户注册
     */
    private void userRegister() {
        Log.d("register","点击了下一步");
        UserService userService = NetManager.getInstance().createWithUrl(UserService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(userService.userRegister(mEtUsername.getText().toString(), Md5.digest(mEtPassword.getText().toString().getBytes())),
                new RxSubscriber<User>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(),
                                "注册失败！",
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    protected void _onNext(User user) {
                        Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    /**
     * 检查输入的合法性
     * @return
     */
    private boolean checkInput() {
        Log.d("register",mEtRepeatpassword.getText().toString());
        Log.d("register",mEtPassword.getText().toString());
        if(!mEtUsername.getText().toString().equals("")
                && !mEtPassword.getText().toString().equals("")
                && mEtRepeatpassword.getText().toString().equals(mEtPassword.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                mCvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCvAdd, mCvAdd.getWidth() / 2, 0, mFab.getWidth() / 2, mCvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCvAdd, mCvAdd.getWidth() / 2, 0, mCvAdd.getHeight(), mFab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
