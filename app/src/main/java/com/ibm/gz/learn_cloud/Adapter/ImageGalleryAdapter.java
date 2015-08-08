package com.ibm.gz.learn_cloud.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.ibm.gz.learn_cloud.R;

/**
 * Created by host on 2015/8/9.
 */
public class ImageGalleryAdapter extends BaseAdapter {
    private Context context;
    // �������еķ�����ʾ���ǿ��Ը���ָ������ʾͼƬ������,����ÿ��ͼƬ�Ĵ���
    private int[] image = new int[] { R.drawable.qq, R.drawable.weibo,
            R.drawable.weixin };

    public ImageGalleryAdapter(Context context) {
        this.context = context;
    }

    public int getCount() { // ȡ��Ҫ��ʾ���ݵ�����
        return image.length;
    }

    public Object getItem(int position) { // ÿ����Դ��λ��
        return image[position];
    }

    public long getItemId(int position) { // ȡ��ÿ�����ID
        return image[position];
    }

    // ����Դ���õ�һ�����֮�У���������������ImageView
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(context);
        iv.setBackgroundColor(0xFFFFFFFF);
        iv.setImageResource(image[position]);// ��ImageView������Դ
        iv.setScaleType(ImageView.ScaleType.CENTER);// ���ö��뷽ʽ
        iv.setLayoutParams(new Gallery.LayoutParams(120,150));
        return iv;
    }
}