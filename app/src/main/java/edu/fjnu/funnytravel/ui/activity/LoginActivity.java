package edu.sqchen.iubao.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.HttpResultT;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.UserService;
import edu.sqchen.iubao.model.entity.User;
import edu.sqchen.iubao.ui.util.Md5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.bt_go)
    Button mBtGo;
    @BindView(R.id.cv)
    CardView mCv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.tool_bar_login)
    Toolbar mToolBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                Intent i2 = new Intent(this, MainActivity.class);
                startActivity(i2, oc2.toBundle());

//                if(checkInput()) {
//                    userLogin();
//                } else {
//                    Toast.makeText(getApplicationContext(),"登录失败，用户名或密码错误！",Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }

    /**
     * 用户登录
     */
    private void userLogin() {
        //获取输入
        String userName = mEtUsername.getText().toString().trim();
        String password = Md5.digest(mEtPassword.getText().toString().getBytes());
        //创建对象
        User user = new User(userName,password);
        //转换成json字符串
        Gson gson = new Gson();
        String userInfo = gson.toJson(user);
        Log.d("login",userInfo);
        UserService userService = NetManager.getInstance().createWithUrl(UserService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(userService.userLogin(userInfo),
                new RxSubscriber<User>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(),
                                "登录失败！",
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    protected void _onNext(User user) {
                        //Activity切换动画
                        Explode explode = new Explode();
                        explode.setDuration(500);
                        getWindow().setExitTransition(explode);
                        getWindow().setEnterTransition(explode);
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                        Intent intent = new Intent(getContext(), MainActivity.class);

                        startActivity(intent, oc2.toBundle());
                    }
                });
    }

    /**
     * 检查输入的合法性
     * @return 输入是否合法
     */
    private boolean checkInput() {
        if(!mEtUsername.getText().toString().equals("")
                && !mEtPassword.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
