package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PostChangeActivity extends AppCompatActivity {

    ImageButton backBtn, checkBtn;
    EditText write_title, write_content;

    String[] boardResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_change);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("id");
        String bno = intent.getStringExtra("bno");


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

        try{
            String result;
            ConnectBoardActivity task = new ConnectBoardActivity();
            result = task.execute(bno).get();

            //[0] title : [1] content : [2] replycnt : [3] recommendcnt : [4] writerNickname : [5] regDate ; [6] userId
            boardResult = result.split("`");

            write_title.setText(boardResult[0].trim());
            write_content.setText(boardResult[1]);
        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String postChangeResult;
                    ConnectPostChangeActivity task = new ConnectPostChangeActivity();
                    postChangeResult = task.execute(bno, userId, write_title.getText().toString(), write_content.getText().toString()).get();

                    if (postChangeResult.equals("success")) {
                        finish();
                        Intent intent = new Intent(PostChangeActivity.this,
                                PostActivity.class);
                        intent.putExtra("id", userId);
                        intent.putExtra("bno", bno);
                        startActivity(intent);
                        Toast.makeText(PostChangeActivity.this, "게시글이 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else if (postChangeResult.equals("fail")) {
                        finish();
                        Toast.makeText(PostChangeActivity.this, "게시글 수정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PostChangeActivity.this, postChangeResult, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", "Error");
                }
            }
        });
    }
}