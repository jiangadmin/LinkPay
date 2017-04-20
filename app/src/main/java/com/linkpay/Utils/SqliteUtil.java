package com.linkpay.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.linkpay.Application.KEY_NAME;

/**
 * Created by jiang
 * on 2017/1/22.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:SQLite 工具类
 */
public class SqliteUtil extends SQLiteOpenHelper {
    private static final String TAG = "SqliteUtil";

    public static final int VERSION = 1;

    public static String DBName = "Linkpay";
    public static String TableName;

    public static SQLiteDatabase db;


    //必须要有构造函数
    public SqliteUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TableName + "(id integer primary key," + KEY_NAME.PLID + " int," + KEY_NAME.推送标题 + " text," + KEY_NAME.推送简述 + " text," + KEY_NAME.推送内容 + " text," + KEY_NAME.推送时间 + " text," + KEY_NAME.推送类型 + " int," + KEY_NAME.推送已读 + " int)";
        //输出创建数据库的日志信息
        LogUtil.e(TAG, "create Database------------->");
        //execSQL函数用于执行SQL语句
        db.execSQL(sql);
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息
        LogUtil.e(TAG, "update Database------------->");
    }

    /**
     * 增加数据
     *
     * @param context
     * @param table   表名
     * @param cv      数据
     */
    public static void Insert(Context context, String table, ContentValues cv) {
        TableName = table;
        SqliteUtil dbHelper = new SqliteUtil(context, DBName, null, 1);
        //得到一个可写的数据库
        db = dbHelper.getWritableDatabase();
        //调用insert方法，将数据插入数据库
        db.insert(TableName, null, cv);
        Close();
    }

    /**
     * 删除数据
     *
     * @param context
     * @param table       表名
     * @param whereClause 删除条件
     * @param whereArgs   条件数组
     */
    public static void Delete(Context context, String table, String whereClause, String[] whereArgs) {
        TableName = table;
        SqliteUtil dbHelper = new SqliteUtil(context, DBName, null, 1);
        db = dbHelper.getReadableDatabase();
        db.delete(TableName, whereClause, whereArgs);
        Close();

    }


    /**
     * 更新数据
     *
     * @param context
     * @param table       表名
     * @param cv          更新内容
     * @param whereClause 更新的条件
     * @param whereArgs   更新的条件数组
     */
    public static void Update(Context context, String table, ContentValues cv, String whereClause, String[] whereArgs) {
        TableName = table;
        SqliteUtil dbHelper = new SqliteUtil(context, DBName, null, 1);
        db = dbHelper.getReadableDatabase();
        db.update(TableName, cv, whereClause, whereArgs);
        LogUtil.e(TAG, "更新数据");

        Close();
    }

    /**
     * 查询数据
     *
     * @param context
     * @param table       表名
     * @param columns     列名称数组
     * @param whereClause 条件
     * @param whereArgs   条件数组
     * @param groupBy     分组列
     * @param having      分组条件
     * @param orderBy     排序列
     * @return
     */

    public static Cursor Query(Context context, String table, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy) {
        TableName = table;
        SqliteUtil dbHelper = new SqliteUtil(context, DBName, null, 1);
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TableName, columns, whereClause, whereArgs, groupBy, having, orderBy);
        return cursor;
    }

    /**
     * 关闭数据库
     */
    public static void Close() {
        db.close();
    }


}