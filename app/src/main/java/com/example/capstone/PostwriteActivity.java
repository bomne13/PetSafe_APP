package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PostwriteActivity extends AppCompatActivity {

    ImageButton backBtn, checkBtn;
    EditText write_title, write_content;

    private List<PostphotoData> postphotoList;
    private PostphotoAdapter postphotoAdapter;
    private RecyclerView postphoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postwrite);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");


        backBtn = (ImageButton) findViewById(R.id.backBtn);
        checkBtn = (ImageButton) findViewById(R.id.checkBtn);
        write_title = (EditText) findViewById(R.id.write_title);
        write_content = (EditText)findViewById(R.id.write_content);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String postAddResult;

                    ConnectPostWriteActivity task = new ConnectPostWriteActivity();
                    postAddResult = task.execute(userId, write_title.getText().toString(), write_content.getText().toString()).get();

                    if (postAddResult.equals("success")) {
                        finish();
                        Intent intent = new Intent(PostwriteActivity.this,
                                FragmenthomeActivity.class);
                        intent.putExtra("id", userId);
                        intent.putExtra("checked", "community");
                        startActivity(intent);
                        Toast.makeText(PostwriteActivity.this, "게시글이 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else if (postAddResult.equals("fail")) {
                        finish();
                        Toast.makeText(PostwriteActivity.this, "게시글 추가에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PostwriteActivity.this, postAddResult, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", "Error");
                }
            }
        });

//        postphoto = (RecyclerView) findViewById(R.id.postphoto);
//
//
//        postphotoList = new ArrayList<>();
//        postphotoList.add(new PostphotoData(R.drawable.ic_imgicon_circle));
//        postphotoList.add(new PostphotoData(R.drawable.ic_imgicon_circle));
//        postphotoList.add(new PostphotoData(R.drawable.ic_imgicon_circle));
//        postphotoList.add(new PostphotoData(R.drawable.ic_imgicon_circle));
//        postphotoList.add(new PostphotoData(R.drawable.ic_imgicon_circle));
//        postphotoList.add(new PostphotoData(R.drawable.ic_imgicon_circle));
//
//        postphotoAdapter = new PostphotoAdapter(postphotoList);
//        postphoto.setAdapter(postphotoAdapter);
//        postphoto.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));





//        findViewById(R.id.communityBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
//                getMenuInflater().inflate(R.menu.popup2,popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        if (menuItem.getItemId() == R.id.action_menu2){
//                            Toast.makeText(PostwriteActivity.this, "자유게시판 클릭", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(PostwriteActivity.this, "갤러리 클릭", Toast.LENGTH_SHORT).show();
//                        }
//
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });

    }
}