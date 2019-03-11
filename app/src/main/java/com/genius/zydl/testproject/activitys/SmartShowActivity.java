package com.genius.zydl.testproject.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreatorFactory;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.topbar.SmartTopbar;
import com.genius.zydl.testproject.R;

public class SmartShowActivity extends BasicActivity implements View.OnClickListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_smartshow;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_smartshow_toast_default).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_showAtTop).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_showInCenter).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_showAtLocation).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_info).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_warning).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_success).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_error).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_fail).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_complete).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_forbid).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_toast_waiting).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_snackbar_default).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_snackbar_long).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_snackbar_indefinite_default).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_snackbar_indefinite_Linsterner).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_topbar_default).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_topbar_long).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_topbar_indefinite_default).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_topbar_indefinite_Linsterner).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_notification).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_ensure).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_ensure_delay).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_input).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_loading_large).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_loading_middle).setOnClickListener(this);
        findViewById(R.id.btn_smartshow_dialog_loading_small).setOnClickListener(this);
    }

    @Override
    protected void main() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_smartshow_toast_default:
                SmartToast.show("Test");
                break;
            case R.id.btn_smartshow_toast_showAtTop:
                SmartToast.showAtTop("Test");
                break;
            case R.id.btn_smartshow_toast_showInCenter:
                SmartToast.showInCenter("Test");

                break;
            case R.id.btn_smartshow_toast_showAtLocation:
                SmartToast.showAtLocation("Test", Gravity.LEFT | Gravity.CENTER, 10, 10);
                break;
            case R.id.btn_smartshow_toast_info:
                SmartToast.info("Test");
                break;
            case R.id.btn_smartshow_toast_warning:
                SmartToast.warning("Test");

                break;
            case R.id.btn_smartshow_toast_success:
                SmartToast.success("Test");

                break;
            case R.id.btn_smartshow_toast_error:
                SmartToast.error("Test");

                break;
            case R.id.btn_smartshow_toast_fail:
                SmartToast.fail("Test");

                break;
            case R.id.btn_smartshow_toast_complete:
                SmartToast.complete("Test");

                break;
            case R.id.btn_smartshow_toast_forbid:
                SmartToast.forbid("Test");

                break;
            case R.id.btn_smartshow_toast_waiting:
                SmartToast.waiting("Test");
                break;
            case R.id.btn_smartshow_snackbar_default:
//                SmartSnackbar.get((Activity) context).show("Text");
                SmartSnackbar.get((Activity) context).show("Text", "yes");
                break;
            case R.id.btn_smartshow_snackbar_long:
                SmartSnackbar.get((Activity) context).showLong("Text");
                break;
            case R.id.btn_smartshow_snackbar_indefinite_default:
                //只传入消息文本时，会显示默认的动作文本"确定"，点击动作文本，执行默认行为——Snackbar消失
                SmartSnackbar.get((Activity) context).showIndefinite("不点确定不会自动消失");
                break;
            case R.id.btn_smartshow_snackbar_indefinite_Linsterner:
                SmartSnackbar.get(this).showIndefinite("点击后有回调事件", "好的", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SmartToast.show("Snackbar回调成功");
                    }
                });
                break;
            case R.id.btn_smartshow_topbar_default:
                SmartTopbar.get((Activity) context).show("Text");
//                SmartTopbar.get((Activity) context).show("Text","yes");
                break;
            case R.id.btn_smartshow_topbar_long:
                SmartTopbar.get((Activity) context).showLong("Text");
                break;
            case R.id.btn_smartshow_topbar_indefinite_default:
                //只传入消息文本时，会显示默认的动作文本"确定"，点击动作文本，执行默认行为——Snackbar消失
                SmartTopbar.get((Activity) context).showIndefinite("不点确定不会自动消失");
                break;
            case R.id.btn_smartshow_topbar_indefinite_Linsterner:
                SmartTopbar.get(this).showIndefinite("点击后有回调事件", "好的", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SmartToast.show("SmartTopbar回调成功");
                    }
                });
                break;
            case R.id.btn_smartshow_dialog_notification:
                SmartDialog smartDialog1 = SmartDialog.newInstance(
                        DialogCreatorFactory
                                .notification()
                                .cancelable(false)
                                .cancelableOnTouchOutside(false)
                                .message("Test"))
                        .reuse(true);
                smartDialog1.show((Activity) context);
                break;
            case R.id.btn_smartshow_dialog_ensure:
                SmartDialog smartDialog2 = SmartDialog.newInstance(
                        DialogCreatorFactory
                                .ensure()
                                .cancelable(false)
                                .cancelableOnTouchOutside(false)
                                .message("Test")
                                .confirmBtn("确定")
                                .cancelBtn("取消"))
                        .reuse(true);
                smartDialog2.show((Activity) context);
                break;
            case R.id.btn_smartshow_dialog_ensure_delay:
                SmartDialog smartDialog3 = SmartDialog.newInstance(
                        DialogCreatorFactory
                                .ensure()
                                .cancelable(false)
                                .cancelableOnTouchOutside(false)
                                .message("Test")
                                .confirmBtn("确定", new DialogBtnClickListener() {
                                    @Override
                                    public void onBtnClick(Dialog dialog, int i, Object o) {
                                        SmartToast.show("点击了确定");
                                        dialog.dismiss();
                                    }
                                })
                                .cancelBtn("取消")
                                .secondsDelayConfirm(5))
                        .reuse(true);
                smartDialog3.show((Activity) context);
                break;
            case R.id.btn_smartshow_dialog_input:
                SmartDialog smartDialog4 = SmartDialog.newInstance(
                        DialogCreatorFactory
                                .input()
                                .inputAtMost(30)
                                .cancelable(false)
                                .cancelableOnTouchOutside(false)
                                .hint("请输入")
                                .textOfDefaultFill("我提个建议")
                                .clearInputPerShow(true)
                                .confirmBtn("提交", new DialogBtnClickListener() {
                                    @Override
                                    public void onBtnClick(Dialog dialog, int i, Object data) {
                                        if (data.toString().length() > 30) {
                                            SmartToast.showInCenter("最多只能输入30个字符");
                                            return;
                                        } else {
                                            dialog.dismiss();
                                            SmartToast.showInCenter(data.toString());
                                        }
                                    }
                                })
                ).reuse(true);
                smartDialog4.show((Activity) context);
                break;
            case R.id.btn_smartshow_dialog_loading_large:
                SmartDialog smartDialog5 = SmartDialog.newInstance(
                        DialogCreatorFactory.loading().large().message("Test")).reuse(true);
                smartDialog5.show((Activity) context);
                break;
            case R.id.btn_smartshow_dialog_loading_middle:
                SmartDialog smartDialog6 = SmartDialog.newInstance(
                        DialogCreatorFactory.loading().middle().message("Test")).reuse(true);
                smartDialog6.show((Activity) context);
                break;
            case R.id.btn_smartshow_dialog_loading_small:
                SmartDialog smartDialog7 = SmartDialog.newInstance(
                        DialogCreatorFactory.loading().small().message("Test")).reuse(true);
                smartDialog7.show((Activity) context);
                break;
        }
    }
}
