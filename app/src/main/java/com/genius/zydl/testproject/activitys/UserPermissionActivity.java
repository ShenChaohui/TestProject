package com.genius.zydl.testproject.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.utils.PathUtil;
import com.genius.zydl.testproject.utils.RealPathFromUriUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.Setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class UserPermissionActivity extends BasicActivity {
    private Button mButtonCamera, mButtonAlbum;
    private ImageView mIvPhoto;
    private TextView mTvPhotoPath;

    private String mPhotoPath;
    private Uri mPhotoUri;

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

    @Override
    protected int getLayout() {
        return R.layout.activity_user_permission;
    }

    @Override
    protected void initView() {
        initTitle();
        mButtonCamera = findViewById(R.id.btn_camera);
        mButtonAlbum = findViewById(R.id.btn_album);
        mIvPhoto = findViewById(R.id.iv_photo);
        mTvPhotoPath = findViewById(R.id.tv_photo_path);
    }

    @Override
    protected void main() {
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndPermission.with(context)
                        .runtime()
                        .permission(Permission.CAMERA)
                        .rationale(mRationale)//拒绝过一次，再请求权限，会进入这个
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                //获去到了权限，启动相机
                                openCamera();
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                //没有获去到权限
                                //如果是永久忽略了这些权限，提示去设置中打开
                                if (AndPermission.hasAlwaysDeniedPermission(context, data)) {
                                    List<String> permissions = Permission.transformText(context, data);
                                    StringBuffer sb = new StringBuffer();
                                    for (int i = 0; i < permissions.size(); i++) {
                                        sb.append(permissions.get(i) + "权限,");
                                    }
                                    //弹窗提示
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                    dialog.setTitle("您永久的关闭了" + sb + "程序无法运行");
                                    dialog.setMessage("是否去设置中打开?");
                                    dialog.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //去设置中打开
                                            AndPermission.with(context)
                                                    .runtime()
                                                    .setting()
                                                    .onComeback(new Setting.Action() {
                                                        @Override
                                                        public void onAction() {
                                                            // 用户从设置回来了。
                                                            if (AndPermission.hasPermissions(context, Permission.CAMERA)) {
                                                                openCamera();
                                                            } else {
                                                                Toast.makeText(context, "您拒绝了相机权限，无法打开相机", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    })
                                                    .start();
                                        }
                                    });
                                    dialog.setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(context, "您拒绝了相机权限，无法打开相机", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    dialog.show();
                                } else {//普通的拒绝
                                    Toast.makeText(context, "您拒绝了相机权限，无法打开相机", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .start();
            }
        });
        mButtonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbums();
            }
        });
    }

    private void openAlbums() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0x02);
    }

    private void openCamera() {
        mPhotoPath = PathUtil.getPicDirPath(context) + "/" + System.currentTimeMillis() + ".jpg";
        File photoFile = new File(mPhotoPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        mPhotoUri = AndPermission.getFileUri(context, photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        startActivityForResult(intent, 0x01);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x01 && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(mPhotoUri));
                mIvPhoto.setImageBitmap(bitmap);
                mTvPhotoPath.setText(mPhotoPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 0x02 && resultCode == RESULT_OK) {
            mPhotoUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(mPhotoUri));
                mIvPhoto.setImageBitmap(bitmap);
                mPhotoPath = RealPathFromUriUtil.getRealPathFromUri(context, mPhotoUri);
                mTvPhotoPath.setText(mPhotoPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
