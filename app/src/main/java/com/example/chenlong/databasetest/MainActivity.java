package com.example.chenlong.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_insert)
    Button mInsert;
    @BindView(R.id.button_delete)
    Button mDelete;
    @BindView(R.id.button_update)
    Button mUpdate;
    @BindView(R.id.button_select)
    Button mSelect;
    private BookStoreDB bookStoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bookStoreDB = new BookStoreDB(this, "Books.db", null, 2);

        Button button = (Button) findViewById(R.id.button_db);
        button.setOnClickListener(v -> bookStoreDB.getWritableDatabase());

        /**
         * 添加到数据库
         */
        mInsert.setOnClickListener(v -> {
            SQLiteDatabase database = bookStoreDB.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", "书的名字");
            values.put("author", "作者的名字");
            values.put("pages", "110");
            values.put("price", "10.20");
            database.insert("Book", null, values);

            Toast.makeText(this, "插入数据成功!", Toast.LENGTH_SHORT).show();
        });

        /**
         * 更新数据库
         */
        mUpdate.setOnClickListener(v -> {
            SQLiteDatabase database = bookStoreDB.getWritableDatabase();
            ContentValues value = new ContentValues();
            value.put("pages", "200");
            database.update("Book", value, "name = ?", new String[]{"书的名字"});

            Toast.makeText(this, "书的页面已更新为200", Toast.LENGTH_SHORT).show();
        });

        /**
         * 查询数据库
         */
        mSelect.setOnClickListener(v -> {
            SQLiteDatabase database = bookStoreDB.getReadableDatabase();
            Cursor cursor = database.query("Book", null, "name = ?", new String[]{"书的名字"}, null, null, null);
            if (cursor.moveToNext()) {
                Toast.makeText(this, cursor.getString(cursor.getColumnIndex("name")), Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

        /**
         * 删除数据库
         */
        mDelete.setOnClickListener(v -> {
            SQLiteDatabase database = bookStoreDB.getWritableDatabase();
            database.delete("Book", "pages=?", new String[]{"200"});

            Toast.makeText(this, "页数为200的书被删除了", Toast.LENGTH_SHORT).show();
        });
    }
}
