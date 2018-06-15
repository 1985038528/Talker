package com.panxianhao.talker.Base;

import com.panxianhao.talker.network.APIRetrofit;
import com.panxianhao.talker.utils.HashUtil;
import com.panxianhao.talker.utils.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Louis on 2018/6/13 18:34.
 */
public class FileCache<Holder> {
    private File baseDir;
    private String ext;
    private CacheListener<Holder> cacheListener;
    private SoftReference<Holder> holderSoftReference;

    public FileCache(String baseDir, String ext, CacheListener<Holder> cacheListener) {
        this.baseDir = new File(Application.getCacheDirFile(), baseDir);
        this.ext = ext;
        this.cacheListener = cacheListener;
    }

    public File buildCacheFile(String path) {
        String key = HashUtil.getMD5String(path);
        return new File(baseDir, key + "." + ext);
    }

    public void download(Holder holder, String path) {
        if (path.startsWith(Application.getCacheDirFile().getAbsolutePath())) {
            cacheListener.onDownLoadSucceed(holder, new File(path));
            return;
        } else {
            File cacheFile = buildCacheFile(path);
            if (cacheFile.exists() && cacheFile.length() > 0) {
                cacheListener.onDownLoadSucceed(holder,cacheFile);
                return;
            }
            holderSoftReference = new SoftReference<>(holder);
            OkHttpClient client = APIRetrofit.getClient();
            Request request = new Request.Builder()
                    .url(path)
                    .get()
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new NetCallback(holder, cacheFile));
        }
    }

    private Holder getLastHolderAndClear() {
        if (holderSoftReference == null) {
            return null;
        } else {
            Holder holder = holderSoftReference.get();
            holderSoftReference.clear();
            return holder;
        }
    }

    private class NetCallback implements Callback {
        private final SoftReference<Holder> holderSoftReference;
        private final File file;

        public NetCallback(Holder holder, File file) {
            this.holderSoftReference = new SoftReference<>(holder);
            this.file = file;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Holder holder = holderSoftReference.get();
            if (holder != null && holder == getLastHolderAndClear()) {
                FileCache.this.cacheListener.onDownLoadFailed(holder);
            }
        }

        @Override
        public void onResponse(Call call, Response response) {
            InputStream inputStream = response.body().byteStream();
            if (inputStream != null && StreamUtil.copy(inputStream, file)) {
                Holder holder = holderSoftReference.get();
                if (holder != null && holder == getLastHolderAndClear()) {
                    FileCache.this.cacheListener.onDownLoadSucceed(holder, file);
                }
            } else {
                onFailure(call, null);
            }

        }
    }


    public interface CacheListener<Holder> {
        void onDownLoadSucceed(Holder holder, File file);

        void onDownLoadFailed(Holder holder);
    }
}
