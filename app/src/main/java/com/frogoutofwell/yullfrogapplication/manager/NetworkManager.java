package com.frogoutofwell.yullfrogapplication.manager;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.frogoutofwell.yullfrogapplication.MyApplication;
import com.frogoutofwell.yullfrogapplication.data.AccountInfoResult;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetailResult;
import com.frogoutofwell.yullfrogapplication.data.ActivityNameResult;
import com.frogoutofwell.yullfrogapplication.data.DoDetailResult;
import com.frogoutofwell.yullfrogapplication.data.FacebookUserResult;
import com.frogoutofwell.yullfrogapplication.data.InterDoReviewResult;
import com.frogoutofwell.yullfrogapplication.data.InterInfoResult;
import com.frogoutofwell.yullfrogapplication.data.InterTestReviewResult;
import com.frogoutofwell.yullfrogapplication.data.LikeStatusResult;
import com.frogoutofwell.yullfrogapplication.data.MainHomeDetailResult;
import com.frogoutofwell.yullfrogapplication.data.MainInterResult;
import com.frogoutofwell.yullfrogapplication.data.MainMypageResult;
import com.frogoutofwell.yullfrogapplication.data.MyDoReviewResult;
import com.frogoutofwell.yullfrogapplication.data.MyTestReviewResult;
import com.frogoutofwell.yullfrogapplication.data.NotificationResult;
import com.frogoutofwell.yullfrogapplication.data.PointCheckResult;
import com.frogoutofwell.yullfrogapplication.data.ReviewUploadResult;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.data.TestDetailResult;
import com.frogoutofwell.yullfrogapplication.data.UserAlarmResult;
import com.frogoutofwell.yullfrogapplication.login.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class NetworkManager {

    private static NetworkManager instance;
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private static final int DEFAULT_CACHE_SIZE = 50 * 1024 * 1024;
    private static final String DEFAULT_CACHE_DIR = "miniapp";
    OkHttpClient mClient;
    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        File dir = new File(context.getExternalCacheDir(), DEFAULT_CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        builder.cache(new Cache(dir, DEFAULT_CACHE_SIZE));

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mClient = builder.build();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);
        public void onFail(Request request, IOException exception);
    }

    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAIL = 2;

    class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkResult result = (NetworkResult)msg.obj;
            switch (msg.what) {
                case MESSAGE_SUCCESS :
                    result.listener.onSuccess(result.request, result.result);
                    break;
                case MESSAGE_FAIL :
                    result.listener.onFail(result.request, result.excpetion);
                    break;
            }
        }
    }

    NetworkHandler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class NetworkResult<T> {
        Request request;
        OnResultListener<T> listener;
        IOException excpetion;
        T result;
    }

    Gson gson = new Gson();

    // 홈 메인
    private static final String FROG_SERVER = "http://52.79.179.176:3000";
    private static final String FROG_MAIN_HOME = FROG_SERVER+"/homePage";
    public Request getFrogMainHomeFeed(Object tag,
                                    OnResultListener<MainHomeDetailResult> listener) {
        String url = String.format(FROG_MAIN_HOME);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MainHomeDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    MainHomeDetailResult data = gson.fromJson(text, MainHomeDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 메인 활동명 리스트
    private static final String MAIN_ACTIVITY_LIST = FROG_SERVER+"/mainActivityList";
    public Request getActivityNameList(Object tag, OnResultListener<ActivityNameResult> listener) {
        String url = String.format(MAIN_ACTIVITY_LIST);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<ActivityNameResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ActivityNameResult data = gson.fromJson(response.body().charStream(), ActivityNameResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 메인 USER 알림 lIST
    private static final String USER_NOTICE_LIST = FROG_SERVER+"/userNoticeList";
    public Request getUserNoticeList(Object tag, OnResultListener<NotificationResult> listener) {
        String url = String.format(USER_NOTICE_LIST);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<NotificationResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    NotificationResult data = gson.fromJson(response.body().charStream(), NotificationResult.class);
                    result.result = data;
                    //Log.i("notice","notice"+data.contents.length);
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }


    // 활동명 검색
    private static final String MAIN_ACTIVITY_SEARCH = FROG_SERVER+"/homeSearch/%s";
    public Request getActivitySearch(Object tag, String activityName,
                                     OnResultListener<ActivityDetailResult> listener) {
        String url = String.format(MAIN_ACTIVITY_SEARCH, activityName);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<ActivityDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ActivityDetailResult data = gson.fromJson(response.body().charStream(), ActivityDetailResult.class);
                    result.result = data;
                    //Log.i("search","search===================="+data.activityDetail.getName());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 대외활동 상단 정보
    private static final String FROG_INTER_INFO = FROG_SERVER+"/detailActivity/header/%s";
    public Request getFrogInterInfo(Object tag, int activitySeq, OnResultListener<InterInfoResult> listener) {
        String url = String.format(FROG_INTER_INFO, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<InterInfoResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InterInfoResult data = gson.fromJson(response.body().charStream(), InterInfoResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }


    // 모집요강
    private static final String FROG_INTER_GUIDE = FROG_SERVER+"/detailActivity/guide/%s";
    public Request getFrogInterGuide(Object tag, int activitySeq,
                                       OnResultListener<ActivityDetailResult> listener) {
        String url = String.format(FROG_INTER_GUIDE, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<ActivityDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ActivityDetailResult data = gson.fromJson(response.body().charStream(), ActivityDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 면접후기
    private static final String FROG_INTER_TEST_REVIEW = FROG_SERVER+"/detailActivity/interviews/%s";
    public Request getFrogInterTestReview(Object tag, int activitySeq,
                                     OnResultListener<InterTestReviewResult> listener) {
        String url = String.format(FROG_INTER_TEST_REVIEW, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<InterTestReviewResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InterTestReviewResult data = gson.fromJson(response.body().charStream(), InterTestReviewResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 활동후기
    private static final String FROG_INTER_DO_REVIEW = FROG_SERVER+"/detailActivity/postscripts/%s";
    public Request getFrogInterDoReview(Object tag, int activitySeq,
                                          OnResultListener<InterDoReviewResult> listener) {
        String url = String.format(FROG_INTER_DO_REVIEW, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<InterDoReviewResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InterDoReviewResult data = gson.fromJson(response.body().charStream(), InterDoReviewResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 면접후기 상세보기
    private static final String FROG_INTER_TEST_DETAIL = FROG_SERVER+"/detailInterview/%s";
    public Request getInterTestReviewDetail(Object tag, int interviewSeq,
                                          OnResultListener<TestDetailResult> listener) {
        String url = String.format(FROG_INTER_TEST_DETAIL, interviewSeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<TestDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    TestDetailResult data = gson.fromJson(response.body().charStream(), TestDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 활동후기 상세보기
    private static final String FROG_INTER_DO_DETAIL = FROG_SERVER+"/detailPostscript/%s";
    public Request getInterDoReviewDetail(Object tag, int activitySeq,
                                        OnResultListener<DoDetailResult> listener) {
        String url = String.format(FROG_INTER_DO_DETAIL, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<DoDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    DoDetailResult data = gson.fromJson(response.body().charStream(), DoDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 추천
    private static final String INTER_RECOMMEND = FROG_SERVER+"/recommandList";
    public Request getInterRecommend(Object tag, OnResultListener<MainInterResult> listener) {
        String url = String.format(INTER_RECOMMEND);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MainInterResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainInterResult data = gson.fromJson(response.body().charStream(), MainInterResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 대외활동 메인
    private static final String FROG_MAIN_INTER = FROG_SERVER+"/activityPage";
    public Request getFrogMainInter(Object tag, OnResultListener<MainInterResult> listener) {
        String url = String.format(FROG_MAIN_INTER);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MainInterResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainInterResult data = gson.fromJson(response.body().charStream(), MainInterResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 대외활동 조건별 검색
    private static final String FROG_MAIN_INTER_CONDITION = FROG_SERVER+"/conditionsActivity";
    public Request getFrogMainInterCondition(Object tag, String actClass, String indus, String term, String local, OnResultListener<MainInterResult> listener) {
        String url = String.format(FROG_MAIN_INTER_CONDITION, actClass, indus, term, local);

        RequestBody body = new FormBody.Builder()
                .add("actClass", actClass+"")
                .add("indus", indus+"")
                .add("term", term+"")
                .add("local", local+"")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<MainInterResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainInterResult data = gson.fromJson(response.body().charStream(), MainInterResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 대외활동 리뷰 많은 순
    private static final String INTER_HIGH_RATE = FROG_SERVER+"/highRateActivity";
    public Request getSortHighRate(Object tag,String actClass, String indus, String term, String local, OnResultListener<MainInterResult> listener) {
        String url = String.format(INTER_HIGH_RATE);

        RequestBody body = new FormBody.Builder()
                .add("actClass", actClass+"")
                .add("indus", indus+"")
                .add("term", term+"")
                .add("local", local+"")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final NetworkResult<MainInterResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainInterResult data = gson.fromJson(response.body().charStream(), MainInterResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 대외활동 리뷰 많은 순
    private static final String INTER_HIGH_STAR = FROG_SERVER+"/highStarActivity";
    public Request getSortHighStar(Object tag,String actClass, String indus, String term, String local, OnResultListener<MainInterResult> listener) {
        String url = String.format(INTER_HIGH_STAR);

        RequestBody body = new FormBody.Builder()
                .add("actClass", actClass+"")
                .add("indus", indus+"")
                .add("term", term+"")
                .add("local", local+"")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<MainInterResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainInterResult data = gson.fromJson(response.body().charStream(), MainInterResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 마이페이지
    private static final String FROG_MAIN_MYPAGE = FROG_SERVER+"/myPage";
    public Request getFrogMainMypage(Object tag, OnResultListener<MainMypageResult> listener) {
        String url = String.format(FROG_MAIN_MYPAGE);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MainMypageResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainMypageResult data = gson.fromJson(response.body().charStream(), MainMypageResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 찜 대외활동
    private static final String MYPAGE_LIKE_ITEM = FROG_SERVER+"/myPage/moreActivity";
    public Request getMypageLikeItem(Object tag, OnResultListener<MainInterResult> listener) {
        String url = String.format(MYPAGE_LIKE_ITEM);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MainInterResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainInterResult data = gson.fromJson(response.body().charStream(), MainInterResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 활동내역 나의 면접 후기
    private static final String MY_TEST_REVIEW = FROG_SERVER+"/myPage/interviews";
    public Request getMyTestReview(Object tag, OnResultListener<MyTestReviewResult> listener) {
        String url = String.format(MY_TEST_REVIEW);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MyTestReviewResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MyTestReviewResult data = gson.fromJson(response.body().charStream(), MyTestReviewResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 활동내역 나의 활동 후기
    private static final String MY_DO_REVIEW = FROG_SERVER+"/myPage/postscripts";
    public Request getMyDoReview(Object tag, OnResultListener<MyDoReviewResult> listener) {
        String url = String.format(MY_DO_REVIEW);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<MyDoReviewResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MyDoReviewResult data = gson.fromJson(response.body().charStream(), MyDoReviewResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 후기작성시 활동 정보 요청
    private static final String INTER_CLASS_INFO = FROG_SERVER+"/writeFormInfo/%s";
    public Request getInterClassInfo(Object tag, int activitySeq, OnResultListener<ActivityDetailResult> listener) {
        String url = String.format(INTER_CLASS_INFO, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<ActivityDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ActivityDetailResult data = gson.fromJson(response.body().charStream(), ActivityDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 활동 후기 작성
    private static final String FROG_DO_REVIEW_POST = FROG_SERVER + "/writePostscript";
    public Request getFrogDoReviewPost(Object tag,
                                   int activitySeq,
                                   float rate,
                                   String term,
                                   String comment,
                                   String commentGood,
                                   String commentBad,
                                   OnResultListener<String> listener) {
        String url = String.format(FROG_DO_REVIEW_POST);

        RequestBody body = new FormBody.Builder()
                .add("activitySeq", activitySeq+"")
                .add("rate", rate+"")
                .add("term", term)
                .add("comment", comment)
                .add("commentGood", commentGood)
                .add("commentBad",commentBad)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<String> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ReviewUploadResult data = gson.fromJson(text, ReviewUploadResult.class);
                    result.result = data.status;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 면접 후기 작성
    private static final String FROG_TEST_REVIEW_POST = FROG_SERVER + "/writeInterview";
    public Request getFrogTestReviewPost(Object tag,
                                       int activitySeq,
                                       int level, int test_result,
                                       String term,
                                       String question,
                                       String answer,
                                       String way,
                                       OnResultListener<String> listener) {
        String url = String.format(FROG_TEST_REVIEW_POST);

        RequestBody body = new FormBody.Builder()
                .add("activitySeq", activitySeq+"")
                .add("level", level+"")
                .add("result", test_result+"")
                .add("term", term)
                .add("question", question)
                .add("answer", answer)
                .add("way",way)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<String> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ReviewUploadResult data = gson.fromJson(text, ReviewUploadResult.class);
                    result.result = data.status;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 나의 개굴 포인트 확인
    private static final String FROG_POINT_CHECK = FROG_SERVER+"/myPointCheck/%s";
    public Request getMyPointCheck(Object tag,int seq, OnResultListener<PointCheckResult> listener) {
        String url = String.format(FROG_POINT_CHECK,seq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<PointCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    PointCheckResult data = gson.fromJson(response.body().charStream(), PointCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 찜한 활동 상태 변경
    private static final String LIKE_STATUS_CHANGE = FROG_SERVER+"/likeStatusChange/%s";
    public Request getLikeStatusChange(Object tag, int activitySeq, OnResultListener<LikeStatusResult> listener) {
        String url = String.format(LIKE_STATUS_CHANGE, activitySeq);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<LikeStatusResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    LikeStatusResult data = gson.fromJson(response.body().charStream(), LikeStatusResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 관심 대외활동 등록
    private static final String ACTCLASS_CHANGE = FROG_SERVER + "/actClassChange";
    public Request getMyActclassChange(Object tag,
                                       SparseBooleanArray array,
                                       OnResultListener<StatusCheckResult> listener) {
        String url = String.format(ACTCLASS_CHANGE);

        RequestBody body = new FormBody.Builder()
                .add("all", array.get(0)+"")
                .add("item", array.get(1)+"")
                .add("item", array.get(2)+"")
                .add("item", array.get(3)+"")
                .add("item", array.get(4)+"")
                .add("item", array.get(5)+"")
                .add("item", array.get(6)+"")
                .add("item", array.get(7)+"")
                .add("item", array.get(8)+"")
                .add("item", array.get(9)+"")
                .add("item", array.get(10)+"")
                .add("item", array.get(11)+"")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }


    // 관심 산업군 등록
    private static final String INDUS_CHANGE = FROG_SERVER + "/indusChange";
    public Request getMyIndusChange(Object tag,
                                       SparseBooleanArray array,
                                       OnResultListener<StatusCheckResult> listener) {
        String url = String.format(INDUS_CHANGE);

        RequestBody body = new FormBody.Builder()
                .add("all", array.get(0)+"")
                .add("item", array.get(1)+"")
                .add("item", array.get(2)+"")
                .add("item", array.get(3)+"")
                .add("item", array.get(4)+"")
                .add("item", array.get(5)+"")
                .add("item", array.get(6)+"")
                .add("item", array.get(7)+"")
                .add("item", array.get(8)+"")
                .add("item", array.get(9)+"")
                .add("item", array.get(10)+"")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 로그인
    private static final String URL_SIGN_IN = FROG_SERVER + "/login";
    public Request signIn(Object tag, String email, String password, String resId, OnResultListener<StatusCheckResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("pwd", password)
                .add("gcmToken", resId)
                .build();

        Request request = new Request.Builder()
                .url(URL_SIGN_IN)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 회원가입
    private static final String URL_SIGN_UP = FROG_SERVER + "/signUp";
    public Request signUp(Object tag, String email, String password, OnResultListener<StatusCheckResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("pwd", password)
                .build();

        Request request = new Request.Builder()
                .url(URL_SIGN_UP)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 페이스북 로그인
    private static final String URL_FACEBOOK_LOGIN = FROG_SERVER + "/facebookLogin";
    public Request facebookLogin(Object tag, String accessToken, String resId, OnResultListener<FacebookUserResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("gcmToken", resId)
                .build();

        Request request = new Request.Builder()
                .url(URL_FACEBOOK_LOGIN)
                .post(body)
                .build();

        final NetworkResult<FacebookUserResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    FacebookUserResult data = gson.fromJson(text, FacebookUserResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 약관 동의
    private static final String URL_USER_AGREEMENT = FROG_SERVER + "/quotation";
    public Request getUserAgreement(Object tag, int res, OnResultListener<StatusCheckResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("res", res+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_USER_AGREEMENT)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 대학생 인증
    private static final String URL_STUDENT_CONFIRM = FROG_SERVER + "/studentConfirm";
    public Request getStudentConfirm(Object tag, String sEmail, OnResultListener<StatusCheckResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("studentEmail", sEmail)
                .build();

        Request request = new Request.Builder()
                .url(URL_STUDENT_CONFIRM)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 비밀번호 변경
    private static final String USER_PASSWORD_CHANGE = FROG_SERVER + "/changePwd";
    public Request getUserPWChange(Object tag, String current, String newpwd, OnResultListener<StatusCheckResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("currentpwd", current)
                .add("pwd", newpwd)
                .build();

        Request request = new Request.Builder()
                .url(USER_PASSWORD_CHANGE)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 자동로그인 요청
    private static final String URL_AUTO_USER_LOGIN = FROG_SERVER+"/autoLogin";
    public Request getAutoUserLogin(Object tag, OnResultListener<StatusCheckResult> listener) {
        String url = String.format(URL_AUTO_USER_LOGIN);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    StatusCheckResult data = gson.fromJson(response.body().charStream(), StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 로그아웃 요청
    private static final String URL_USER_LOGOUT = FROG_SERVER+"/logout";
    public Request getUserLogout(Object tag, OnResultListener<StatusCheckResult> listener) {
        String url = String.format(URL_USER_LOGOUT);
        Request request = new Request.Builder().url(url).build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    StatusCheckResult data = gson.fromJson(response.body().charStream(), StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 알람 체크 정보 가져오기
    private static final String URL_USER_ALARM = FROG_SERVER + "/alramCheck";
    public Request getUserAlarm(Object tag, OnResultListener<UserAlarmResult> listener) {
        String url = String.format(URL_USER_ALARM);

        Request request = new Request.Builder()
                .url(URL_USER_ALARM)
                .build();

        final NetworkResult<UserAlarmResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    UserAlarmResult data = gson.fromJson(text, UserAlarmResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 알람 체크 설정
    public Request getUserAlarm(Object tag, int mobile, int notice, int likeact, OnResultListener<StatusCheckResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile+"")
                .add("notice", notice+"")
                .add("likeAct", likeact+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_USER_ALARM)
                .post(body)
                .build();

        final NetworkResult<StatusCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    StatusCheckResult data = gson.fromJson(text, StatusCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    // 계정 정보 가져오기
    private static final String ACCOUNT_USER_INFO = FROG_SERVER + "/accountInfo";
    public Request getAccountUserInfo(Object tag, OnResultListener<AccountInfoResult> listener) {
        String url = String.format(ACCOUNT_USER_INFO);

        Request request = new Request.Builder()
                .url(url)
                .build();

        final NetworkResult<AccountInfoResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    AccountInfoResult data = gson.fromJson(text, AccountInfoResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }


}
