package com.genius.zydl.testproject.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.genius.zydl.testproject.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.Setting;

import java.util.List;


public class FragmentThree extends Fragment {
    private Button mButton;
    private Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, Object data, final RequestExecutor executor) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("您没有打开相机权限");
            dialog.setMessage("是否打开?");
            dialog.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    executor.execute();
                }
            });
            dialog.setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    executor.cancel();
                }
            });
            dialog.show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        mButton = view.findViewById(R.id.btn_fragment_three);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndPermission.with(getActivity())
                        .runtime()
                        .permission(Permission.CAMERA)
                        .rationale(mRationale)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                                startActivity(intent);
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                //如果永久忽略了这些权限
                                if(AndPermission.hasAlwaysDeniedPermission(getActivity(),data)){
                                    //弹窗提示
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                    dialog.setTitle("您永久的关闭了相机权限，程序无法运行");
                                    dialog.setMessage("是否去设置中打开?");
                                    dialog.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //去设置中打开
                                            AndPermission.with(getActivity())
                                                    .runtime()
                                                    .setting()
                                                    .onComeback(new Setting.Action() {
                                                        @Override
                                                        public void onAction() {
                                                            // 用户从设置回来了。
                                                            if(AndPermission.hasPermissions(getActivity(),Permission.CAMERA)){
                                                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                                                                startActivity(intent);
                                                            }else {
                                                                Toast.makeText(getActivity(),"您拒绝了相机权限，无法打开相机",Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    })
                                                    .start();
                                        }
                                    });
                                    dialog.setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(getActivity(),"您拒绝了相机权限，无法打开相机",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    dialog.show();
                                }else {//普通的拒绝

                                    Toast.makeText(getActivity(),"您拒绝了相机权限，无法打开相机",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .start();
            }
        });
        return view;
    }
}
