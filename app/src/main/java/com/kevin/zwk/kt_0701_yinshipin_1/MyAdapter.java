package com.kevin.zwk.kt_0701_yinshipin_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * ----------BigGod be here!----------/
 * ***┏┓******┏┓*********
 * *┏━┛┻━━━━━━┛┻━━┓*******
 * *┃             ┃*******
 * *┃     ━━━     ┃*******
 * *┃             ┃*******
 * *┃  ━┳┛   ┗┳━  ┃*******
 * *┃             ┃*******
 * *┃     ━┻━     ┃*******
 * *┃             ┃*******
 * *┗━━━┓     ┏━━━┛*******
 * *****┃     ┃神兽保佑*****
 * *****┃     ┃代码无BUG！***
 * *****┃     ┗━━━━━━━━┓*****
 * *****┃              ┣┓****
 * *****┃              ┏┛****
 * *****┗━┓┓┏━━━━┳┓┏━━━┛*****
 * *******┃┫┫****┃┫┫********
 * *******┗┻┛****┗┻┛*********
 * ━━━━━━神兽出没━━━━━━
 * <p/>
 * 项目名称：kt_0701_yinshipin_1
 * 包名称：com.kevin.zwk.kt_0701_yinshipin_1
 * 类描述：
 * 创建人：Zhang Wenkai
 * 创建时间：2016/7/1 21:38
 * 修改人：Zhang Wenkai
 * 修改时间：
 * 修改备注：
 */
public class MyAdapter extends BaseAdapter {
    private List<MusicBean> list;
    private Context context;
    private ViewHolder mHolder;

    public MyAdapter(Context context, List<MusicBean> allAudioPaths) {
        this.context = context;
        this.list = allAudioPaths;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_1, null);
            mHolder = new ViewHolder();
            mHolder.textView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.textView.setText(list.get(position).getDisplayName());


        return convertView;
    }


    class ViewHolder {
        TextView textView;

    }
}
