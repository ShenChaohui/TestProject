package com.genius.zydl.testproject.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.genius.zydl.testproject.R;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;


public class SpeechActivity extends BasicActivity implements View.OnClickListener {
    private EditText mEditText;
    private Button mButton;
    private SpeechSynthesizer mTts;

    @Override
    protected int getLayout() {
        return R.layout.activity_speech;
    }

    @Override
    protected void initView() {
        mEditText = findViewById(R.id.et_test);
        mButton = findViewById(R.id.bt_test);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void main() {
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_test) {
            if (mEditText.getText().toString().length() > 0) {
                mTts.startSpeaking(mEditText.getText().toString(), null);
            }

        }
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.destroy();
    }
}
