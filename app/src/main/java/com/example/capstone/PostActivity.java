package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity implements ReplyAdapter.OnListItemSelectedInterface{

    ImageButton backBtn, etcBtn;
    TextView title, writer, write_date, content,writer_id;
    Button heartbtn1 ,replyBtn;
    ImageButton replyAddBtn;
    String[] boardResult, replyResult;
    EditText reply_write;
    View reply_line;
    Dialog dialog01;

    private List<ReplyData> replyLst;
    private ReplyAdapter replyAdapter;
    private RecyclerView reply_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        String bno = intent.getStringExtra("bno");
        String id = intent.getStringExtra("id");

        dialog01 = new Dialog(PostActivity.this);
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog01.setContentView(R.layout.dialog_layout);
        dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        title = findViewById(R.id.title);
        writer = findViewById(R.id.writer);
        write_date = findViewById(R.id.write_date);
        content = findViewById(R.id.content);
        heartbtn1 = findViewById(R.id.heartbtn1);
        replyBtn = findViewById(R.id.replyBtn);
        writer_id = findViewById(R.id.writer_id);
        reply_write = findViewById(R.id.reply_write);
        replyAddBtn = findViewById(R.id.replyAddBtn);
        reply_line = findViewById(R.id.reply_line);


        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(PostActivity.this,
                        FragmenthomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("checked", "community");
                startActivity(intent);
            }
        });

        try{
            String heartCheckResult;
            ConnectBoardLikeCheckActivity task = new ConnectBoardLikeCheckActivity();
            heartCheckResult = task.execute(bno, id).get();

            if(heartCheckResult.equals("success")){
                heartbtn1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_heart_f), null, null, null);
            }else if(heartCheckResult.equals("fail")){
                heartbtn1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_hearticon), null, null, null);
            }


        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        //게시글 내용
        try{
            String result;
            ConnectBoardActivity task = new ConnectBoardActivity();
            result = task.execute(bno).get();

            //[0] title : [1] content : [2] replycnt : [3] recommendcnt : [4] writerNickname : [5] regDate ; [6] userId
            boardResult = result.split("`");

            title.setText(boardResult[0].trim());
            content.setText(boardResult[1]);
            replyBtn.setText(boardResult[2]);
            heartbtn1.setText(boardResult[3]);
            writer.setText(boardResult[4]);
            write_date.setText(boardResult[5]);
            writer_id.setText(boardResult[6]);


        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        heartbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.equals(writer_id.getText().toString())){
                    Toast.makeText(PostActivity.this, "본인 게시글은 추천할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        String heartResult;
                        ConnectBoardLikeAddActivity task = new ConnectBoardLikeAddActivity();
                        heartResult = task.execute(bno, id).get();

                        if(heartResult.equals("success")){
                            finish();
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Toast.makeText(PostActivity.this, "추천했습니다", Toast.LENGTH_SHORT).show();
                        }else if (heartResult.equals("fail")){
                            Toast.makeText(PostActivity.this, "이미 추천한 게시글 입니다", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Log.i("DBtest", "Error");
                    }
                }
            }
        });

        etcBtn = findViewById(R.id.etcBtn);
        if(writer_id.getText().toString().equals(id)){
            etcBtn.setVisibility(View.VISIBLE);
        }

        reply_view = (RecyclerView) findViewById(R.id.reply_view);
        replyLst = new ArrayList<>();
        ArrayList<String[]> array = new ArrayList<>();


        try{
            String reply_result;
            ConnectReplyListActivity task = new ConnectReplyListActivity();
            reply_result = task.execute(bno).get().trim();

            if(reply_result.length() != 0){
                String[] sp = reply_result.split("#");

                for (int j = 0; j < sp.length; j++) {
                    String[] ssp = sp[j].split("`");
                    array.add(ssp);
                }
            }
        }catch (Exception e){
            Log.i("DBtest", "Error");
        }


        //[0] rno : [1] content : [2] Recommendcnt : [3] writerNickname : [4] getRegDate : [5] getUserid
        for (int i = 0; i < array.size(); i++) {
            //replyLst(img, writer, writeDate, content, heart, rno, id)
            replyLst.add(new ReplyData(R.drawable.circle_g, array.get(i)[3].trim(), array.get(i)[4].trim(), array.get(i)[1].trim(), array.get(i)[2].trim(), Integer.parseInt(array.get(i)[0].trim()), array.get(i)[5].trim()));
        }

        if(replyLst.size() > 0){
            reply_line.setVisibility(View.VISIBLE);
        }


        replyAdapter = new ReplyAdapter(replyLst, this);
        reply_view.setAdapter(replyAdapter);
        reply_view.setLayoutManager(new LinearLayoutManager(this));

        etcBtn = findViewById(R.id.etcBtn);
        etcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.popup3, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu1) {
                            Intent intent = new Intent(PostActivity.this, PostChangeActivity.class);
                            intent.putExtra("bno", bno);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.action_menu2) {
                            Toast.makeText(PostActivity.this, "URL이 복사되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog01.show();

                            Button noBtn = dialog01.findViewById(R.id.noBtn);
                            noBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog01.dismiss(); //닫기
                                }
                            });

                            Button DelBtn = dialog01.findViewById(R.id.DelBtn);
                            DelBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try{
                                        String delResult;
                                        ConnectBoardDelActivity task = new ConnectBoardDelActivity();
                                        delResult = task.execute(bno).get();

                                        if (delResult.equals("success")) {
                                            finish();
                                            Intent intent = new Intent(PostActivity.this,
                                                    FragmenthomeActivity.class);
                                            intent.putExtra("id", id);
                                            intent.putExtra("checked", "community");
                                            startActivity(intent);
                                        } else if (delResult.equals("fail")) {
                                            Toast.makeText(PostActivity.this, "게시글 삭제에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(PostActivity.this, delResult, Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        Log.i("DBtest", "Error");
                                    }
                                }
                            });

                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        replyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reply_write.getText().toString().length() != 0){
                    try{
                        String ReplayAddResult;
                        ConnectReplyAddActivity task = new ConnectReplyAddActivity();
                        ReplayAddResult = task.execute(bno, id, reply_write.getText().toString()).get();

                        if (ReplayAddResult.equals("success")) {
                            finish();
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                        } else if (ReplayAddResult.equals("fail")) {
                            finish();
                            Toast.makeText(PostActivity.this, "댓글 추가에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PostActivity.this, ReplayAddResult, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Log.i("DBtest", "Error");
                    }
                }else{
                    Toast.makeText(PostActivity.this, "댓글 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public void onItemSelected(View v, int position) {

    }


}