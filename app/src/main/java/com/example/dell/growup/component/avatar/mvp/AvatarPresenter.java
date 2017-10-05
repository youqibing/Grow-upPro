package com.example.dell.growup.component.avatar.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.growup.utils.AsyncTaskLoader;
import com.example.dell.growup.utils.IAsyncCallback;
import com.example.dell.growup.utils.ToastUtil;
import com.example.dell.growup.component.avatar.job_and_event.AvatarEvent;
import com.example.dell.growup.component.avatar.job_and_event.AvatarJob;
import com.example.dell.growupbase.base.GrowUpApplication;
import com.example.dell.growupbase.base.fragment.IPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;


public class AvatarPresenter extends IPresenter<IAvatarView> implements IAvatarView.IAvatarViewCallBack {

    private static final int FROM_LOCAL = 1;
    private static final int FROM_CUT = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 3;

    private Uri uriPath;

    private Activity activity;

    private JobManager jobManager;

    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static final String RAISING_PETS_FILE = BASE_PATH + "/" +"GrowUp" + "/" ;
    private static final String RAISING_PETS_UPLOAD = BASE_PATH + "/" +"GrowUp"+"/" + "headPhoto.png";
    private static final String RAISING_PETS_URL_PATH = "file://" + BASE_PATH + "/" +"GrowUp"+"/" + "headPhoto.png";

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    public AvatarPresenter(Context context, Activity activity) {
        super(context);
        this.activity = activity;

        EventBus.getDefault().register(this);
        jobManager = GrowUpApplication.getInstance().getJobManager();

        externalStorageState();//为我们储存用户头像的路径创造一个文件夹
    }

    private void externalStorageState(){
        File file = new File(RAISING_PETS_FILE);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void onAvatarClick() {
        if(Build.VERSION.SDK_INT >= 23 ){
            requestPermissions();
        }
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(galleryIntent, FROM_LOCAL);
    }

    /*
     * 适配6.0以上动态权限问题
     */
    private void requestPermissions(){
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {

                case FROM_LOCAL:
                    if (null != data) {
                        Uri uri = data.getData();
                        cutImage(uri);
                    }
                    break;

                case FROM_CUT:

                    final IAsyncCallback callback = new IAsyncCallback() {
                        Bitmap bitmap = null;

                        @Override
                        public void workToDo() {
                            if (null != data) {
                                try {
                                    /*
                                     * 由于这个读入图片的过程极其容易引起 闪退事件 (MI 5上面),因此这里用一个
                                     * AsyncTask将这个过程放到异步线程,这个东西真的很好用,别说小米了,大米现在也没有问题.
                                     */
                                    bitmap = BitmapFactory.decodeStream(
                                            activity.getContentResolver().openInputStream(uriPath));

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onComplete() {
                            /*
                             * 这里的mView是继承自IPresenter类的 "V mView",而这个mView是通过IPresenter类中的
                             *      public void setIView(V iView){ mView = iView; }
                             * 方法绑定的,iView是BaseComponent类中通过 mView =onCreatView(params, container);
                             * 创建的mView，具体到头像组件实现类AvatarComponent中就是:
                             * protected IAvatarView onCreatView(ComponentParams params, ViewGroup container) {
                             *      return new AvatarView(params.getActivity());
                             *  }
                             *  也就是说,这里的mView是一个实现了IAvatarView接口的AvatarView类对象,因此可以直接操作
                             *  IAvatarView接口中的方法
                             */
                            jobManager.addJobInBackground(new AvatarJob(Uri.parse(RAISING_PETS_UPLOAD).toString()));
                        }
                    };
                    new AsyncTaskLoader().execute(callback);

                    break;

                default:
                    break;
            }
        }
    }


    private void cutImage(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。
        //设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);

        uriPath = Uri.parse(RAISING_PETS_URL_PATH);
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, FROM_CUT);
    }

    @Subscribe
    public void onEventThread(AvatarEvent event){

        if(event.isSucess()){
            ToastUtil.showShortToast("头像上传成功!");
            mView.refreshAvatar();
        }else {
            ToastUtil.showShortToast("头像上传失败,请检查网络连接");
        }
    }
}
