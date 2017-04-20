package com.linkpay.Activity.Home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.Const;
import com.linkpay.Application.KEY_NAME;
import com.linkpay.Dialog.PushDialog;
import com.linkpay.R;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.SqliteUtil;
import com.linkpay.View.SwipeView;

import java.util.ArrayList;

/**
 * 创建人： jiang
 * 创建时间： 2016/11/14
 * 邮箱：www.fangmu@qq.com
 * 电话：186 6120 1018
 * 类描述：站内信
 */
public class NoticeActivity extends BaseActivity {
    private static final String TAG = "NoticeActivity";
    ListView noticeList;
    LinearLayout null_notice;
    private ArrayList<PustItem> mData;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setBack(true);
        setTitle("消息中心");
        noticeList = (ListView) findViewById(R.id.notice_list);
        null_notice = (LinearLayout) findViewById(R.id.notice_null);
        initData();

    }


    /**
     * 初始化数据
     */
    public void initData() {
        mData = new ArrayList<>();
        Cursor cursor = SqliteUtil.Query(this, "push", new String[]{"id", KEY_NAME.推送标题, KEY_NAME.推送简述, KEY_NAME.推送内容, KEY_NAME.推送时间, KEY_NAME.推送类型, KEY_NAME.推送已读}, KEY_NAME.PLID + "=?", new String[]{Const.PIID}, null, null,"id desc");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex(KEY_NAME.推送标题));
            String description = cursor.getString(cursor.getColumnIndex(KEY_NAME.推送简述));
            String message = cursor.getString(cursor.getColumnIndex(KEY_NAME.推送内容));
            String date = cursor.getString(cursor.getColumnIndex(KEY_NAME.推送时间));
            int type = cursor.getInt(cursor.getColumnIndex(KEY_NAME.推送类型));
            int show = cursor.getInt(cursor.getColumnIndex(KEY_NAME.推送已读));

            PustItem item = new PustItem();
            item.setId(id);
            item.setTitle(title);
            item.setDescription(description);
            item.setMessage(message);
            item.setDate(date);
            item.setType(type);
            item.setShow(show);

            mData.add(item);

        }
        adapter = new MyAdapter();
        noticeList.setAdapter(adapter);
        UpdateView();
        cursor.close();
        SqliteUtil.Close();

    }

    public void UpdateView() {
        //        有无信息 图标显示
        if (adapter.getCount() == 0) {
            noticeList.setVisibility(View.GONE);
            null_notice.setVisibility(View.VISIBLE);
        } else {
            null_notice.setVisibility(View.GONE);
            noticeList.setVisibility(View.VISIBLE);

        }

    }


    class MyAdapter extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(NoticeActivity.this, R.layout.swipe_swipeview, null);
                holder.item = (LinearLayout) convertView.findViewById(R.id.notice_item);
                holder.img = (ImageView) convertView.findViewById(R.id.push_list_type);
                holder.show = (ImageView) convertView.findViewById(R.id.push_list_show);
                holder.title = (TextView) convertView.findViewById(R.id.push_list_title);
                holder.message = (TextView) convertView.findViewById(R.id.push_list_message);
                holder.date = (TextView) convertView.findViewById(R.id.push_list_date);
                holder.delete = (TextView) convertView.findViewById(R.id.delete);
                holder.noread = (TextView) convertView.findViewById(R.id.noread);

                holder.delete.setOnClickListener(this);
                holder.noread.setOnClickListener(this);

                holder.swipeView = (SwipeView) convertView;
                // 设置左滑删掉的点击监听
                holder.swipeView.setOnSwipeChangeListener(mOnSwipe);
                holder.item.setOnClickListener(this);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img.setImageResource(mData.get(position).getType() == 0 ? R.drawable.ic_system_notice_icon : R.drawable.ic_user_notice_icon);
            holder.show.setImageResource(mData.get(position).getShow() == 0 ? R.drawable.ic_show : 0);
            holder.title.setText(mData.get(position).getTitle());
            holder.message.setText(mData.get(position).getDescription());
            holder.date.setText(mData.get(position).getDate());
            holder.noread.setText(mData.get(position).getShow() == 0 ? "标为已读" : "标为未读");
            holder.delete.setText("删除");
            //保存下标
            holder.noread.setTag(position);
            holder.delete.setTag(position);
            holder.item.setTag(position);
            return convertView;
        }

        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            switch (view.getId()) {
                case R.id.delete:
                    //取出保存的下标
                    LogUtil.e("TAG", "ID：" + mData.get(position).getId());
                    SqliteUtil.Delete(NoticeActivity.this, "push", KEY_NAME.PLID+"=? and id = ?", new String[]{Const.PIID, mData.get(position).getId() + ""});
                    mData.remove(position);

//                    删除此数据

                    adapter.notifyDataSetChanged();
                    UpdateView();
                    break;
                case R.id.noread:

                    // 2 自动转换
                    updataItem(position, 2);


//                    adapter.notifyDataSetChanged();
                    break;
                case R.id.notice_item:

                    PushDialog.mTitle = mData.get(position).getTitle();
                    PushDialog.mMessage = mData.get(position).getMessage();
                    PushDialog.mType = mData.get(position).getType();

                    Intent intent = new Intent(NoticeActivity.this, PushDialog.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //标为已读
                    updataItem(position, 1);
                    break;
            }
        }
    }

    /**
     * 左滑删掉
     */
    private SwipeView openedSwipeView;
    /**
     * 左滑删除监听
     */
    private SwipeView.OnSwipeChangeListener mOnSwipe = new SwipeView.OnSwipeChangeListener() {

        @Override
        public void onOpen(SwipeView swipeView) {
            // 保存swipView
            openedSwipeView = swipeView;
        }

        @Override
        public void onDown(SwipeView swipeView) {
            if (openedSwipeView != null && openedSwipeView != swipeView) {
                openedSwipeView.close();
            }
        }

        @Override
        public void onClose(SwipeView swipeView) {
            if (openedSwipeView != null && openedSwipeView == swipeView) {
                openedSwipeView = null;
            }
        }
    };

    /**
     * 更新 Item 显示
     *
     * @param position 标识
     * @param show     0 未读 1 已读
     */

    public void updataItem(int position, int show) {
        ContentValues cv = new ContentValues();
        int firstvisible = noticeList.getFirstVisiblePosition();
        int lastvisibale = noticeList.getLastVisiblePosition();
        if (position >= firstvisible && position <= lastvisibale) {
            View view = noticeList.getChildAt(position - firstvisible);
            //然后使用viewholder去更新需要更新的view。
            ViewHolder viewHolder = (ViewHolder) view.getTag();

            if (show == 0) {
                viewHolder.noread.setText("标为已读");
                viewHolder.show.setImageResource(R.drawable.ic_show);
                cv.put(KEY_NAME.推送已读, 0);
                LogUtil.e(TAG, "更新ITEM  更新为未读");

            } else if (show == 1) {
                viewHolder.noread.setText("标为未读");
                viewHolder.show.setImageResource(0);
                cv.put(KEY_NAME.推送已读, 1);
                LogUtil.e(TAG, "更新ITEM  更新为已读");

            } else {
                if (viewHolder.noread.getText() == "标为未读"){
                    viewHolder.noread.setText("标为已读");
                    viewHolder.show.setImageResource(R.drawable.ic_show);
                    cv.put(KEY_NAME.推送已读, 0);
                } else{
                    viewHolder.noread.setText("标为未读");
                    viewHolder.show.setImageResource(0);
                    cv.put(KEY_NAME.推送已读, 1);
                }
            }

            //更新此数据
            SqliteUtil.Update(NoticeActivity.this, "push", cv, KEY_NAME.PLID+"=? and id = ?", new String[]{Const.PIID, mData.get(position).getId() + ""});
        }

    }


    static class ViewHolder {
        LinearLayout item;
        ImageView img;
        ImageView show;
        TextView title;
        TextView message;
        TextView date;
        TextView delete;
        TextView noread;
        SwipeView swipeView;
    }

    class PustItem {
        private int id;
        private String title;
        private String message;
        private String description;
        private String date;
        private int type;
        private int show;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }
    }

}
