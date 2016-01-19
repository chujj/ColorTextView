package library.colortextview.example;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;

import library.colortextview.view.JSONTextFormot;

/**
 * Created by zhujj on 16-1-16.
 */
public class FrescoBitmapFactory implements  JSONTextFormot.BitmapQueryFactory {

    @Override
    public JSONTextFormot.BitmapQuery create() {
        return new MyBitmapQueier();
    }


    public class MyBitmapQueier extends JSONTextFormot.BitmapQuery {

        @Override
        public void start(Object obj, int w, int h) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(ImageRequest.fromUri(mUrl), this);

            dataSource.subscribe(new BaseBitmapDataSubscriber() {

                                     @Override
                                     public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                         // You can use the bitmap in only limited ways
                                         // No need to do any cleanup.

                                         if (bitmap != null) {
                                             Bitmap copy = Bitmap.createBitmap(bitmap);
                                             MyBitmapQueier.this.onBitmapGet(bitmap);
                                         }

                                     }

                                     @Override
                                     public void onFailureImpl(DataSource dataSource) {
                                         // No cleanup required here.
                                     }
                                 },
                CallerThreadExecutor.getInstance());
        }
    }
}
