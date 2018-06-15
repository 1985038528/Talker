package com.panxianhao.talker.face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.ArrayMap;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.panxianhao.talker.R;
import com.panxianhao.talker.utils.StreamUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Louis on 2018/6/12 16:20.
 */
public class Face {
    private static final ArrayMap<String, Bean> FACE_MAP = new ArrayMap<>();
    private static List<FaceTab> FACE_TABS = null;

    private static void init(Context context) {
        if (FACE_TABS == null) {
            synchronized (Face.class) {
                if (FACE_TABS == null) {
                    ArrayList<FaceTab> faceTabs = new ArrayList<>();
                    FaceTab tab = initAssetsFace(context);
                    if (tab != null)
                        faceTabs.add(tab);
                    // init map
                    for (FaceTab faceTab : faceTabs) {
                        faceTab.copyToMap(FACE_MAP);
                    }

                    // init list 不可变的集合
                    FACE_TABS = Collections.unmodifiableList(faceTabs);
                }
            }
        }
    }
    private static FaceTab initAssetsFace(Context context) {
        String faceAsset = "face-t.zip";
        String faceCacheDir = String.format("%s/face/tf", context.getFilesDir());
        File faceFolder = new File(faceCacheDir);
        if (!faceFolder.exists()) {
            if (faceFolder.mkdirs()) {
                try {
                    InputStream inputStream = context.getAssets().open(faceAsset);
                    File faceSource = new File(faceFolder, "source.zip");
                    StreamUtil.copy(inputStream, faceSource);
                    unZipFile(faceSource, faceFolder);
                    StreamUtil.delete(faceSource.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File infoFile = new File(faceCacheDir, "info.json");
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = gson.newJsonReader(new FileReader(infoFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        FaceTab tab = gson.fromJson(reader, FaceTab.class);
        for (Bean face : tab.faces) {
            face.preview = String.format("%s/%s", faceCacheDir, face.preview);
            face.source = String.format("%s/%s", faceCacheDir, face.source);
        }

        return tab;
    }

    private static void unZipFile(File zipFile, File desDir) throws IOException {
        final String folderPath = desDir.getAbsolutePath();
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String name = entry.getName();
            if (name.startsWith("."))
                continue;
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + name;
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            StreamUtil.copy(in, desFile);
        }
    }
    // 获取所有的表情
    public static List<FaceTab> all(@NonNull Context context) {
        init(context);
        return FACE_TABS;
    }

    // 输入表情到editable
    public static void inputFace(@NonNull final Context context, final Editable editable,
                                 final Face.Bean bean, final int size) {
        Glide.with(context)
                .load(bean.preview)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(size, size) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Spannable spannable = new SpannableString(String.format("[%s]", bean.key));
                        ImageSpan span = new ImageSpan(context, resource, ImageSpan.ALIGN_BASELINE);
                        // 前后不关联
                        spannable.setSpan(span, 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        editable.append(spannable);
                    }
                });
    }


    // 拿一个Bean
    // key: ft001
    public static Bean get(Context context, String key) {
        init(context);
        if (FACE_MAP.containsKey(key)) {
            return FACE_MAP.get(key);
        }
        return null;
    }

    public static Spannable decode(@NonNull View target, final Spannable spannable, final int size) {
        if (spannable == null)
            return null;
        String str = spannable.toString();
        if (TextUtils.isEmpty(str))
            return null;


        final Context context = target.getContext();
        Pattern pattern = Pattern.compile("(\\[[^\\[\\]:\\s\\n]+\\])");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String key = matcher.group();
            if (TextUtils.isEmpty(key))
                continue;

            Bean bean = get(context, key.replace("[", "").replace("]", ""));
            if (bean == null)
                continue;

            final int start = matcher.start();
            final int end = matcher.end();
            ImageSpan span = new FaceSpan(context, target, bean.preview, size);
            spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannable;
    }


    // 表情标示
    public static class FaceSpan extends ImageSpan {
        // 自己真实绘制的
        private Drawable mDrawable;
        private View mView;
        private int mSize;
        public FaceSpan(Context context, View view, Object source, final int size) {
            // 虽然设置了默认的表情，但是并不显示，只是用于占位
            super(context, R.drawable.default_face, ALIGN_BOTTOM);
            this.mView = view;
            this.mSize = size;
            Glide.with(context)
                    .load(source)
                    .fitCenter()
                    .into(new SimpleTarget<GlideDrawable>(size, size) {

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            mDrawable = resource.getCurrent();
                            int width = mDrawable.getIntrinsicWidth();
                            int height = mDrawable.getIntrinsicHeight();
                            mDrawable.setBounds(0, 0, width > 0 ? width : size,
                                    height > 0 ? height : size);
                            mView.invalidate();
                        }
                    });
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            // 走我们自己的逻辑，进行测量
            Rect rect = mDrawable != null ? mDrawable.getBounds() :
                    new Rect(0, 0, mSize, mSize);

            if (fm != null) {
                fm.ascent = -rect.bottom;
                fm.descent = 0;

                fm.top = fm.ascent;
                fm.bottom = 0;
            }

            return rect.right;
        }

        @Override
        public Drawable getDrawable() {
            // 复写拿Drawable的方法，当然这里有可能返回的是null
            return mDrawable;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            // 增加判断
            if (mDrawable != null)
                super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        }
    }

    public static class FaceTab {
        public List<Bean> faces = new ArrayList<>();
        public String name;
        // 预览图, 包括了drawable下面的资源int类型
        public Object preview;

        FaceTab(String name, Object preview, List<Bean> faces) {
            this.faces = faces;
            this.name = name;
            this.preview = preview;
        }

        // 添加到Map
        void copyToMap(ArrayMap<String, Bean> faceMap) {
            for (Bean face : faces) {
                faceMap.put(face.key, face);
            }
        }
    }

    public static class Bean {
        Bean(String key, int preview) {
            this.key = key;
            this.source = preview;
            this.preview = preview;
        }
        public String key;
        public String desc;
        public Object source;
        public Object preview;
    }
}