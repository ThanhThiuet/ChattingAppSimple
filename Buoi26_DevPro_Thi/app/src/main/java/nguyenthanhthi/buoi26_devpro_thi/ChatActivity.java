package nguyenthanhthi.buoi26_devpro_thi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView lvChat;
    private EditText edtInputMessage;
    private Button btnSend;
    private UserEntity otherUserChat;
    private ArrayList<MessageEntity> listMessage = new ArrayList<>();
    private ChatAdapter chatAdapter;

    private FirebaseUser myUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference dataMessage = dbReference.child("Messages");
    private DatabaseReference data_myMessage = dataMessage.child(myUser.getUid());
    private DatabaseReference data_otherUserMessage;
    //Khi gửi tin nhắn thì tin đó đc gửi vào data_myMessage và data_otherUserMessage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
        getUserChat();
        getData();
    }

    private void getData() {
        data_myMessage.child(otherUserChat.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listMessage.add(dataSnapshot.getValue(MessageEntity.class));
                chatAdapter.notifyDataSetChanged();
                lvChat.setSelection(listMessage.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUserChat() {
        String email = getIntent().getStringExtra("EMAIL");
        String id = getIntent().getStringExtra("ID");
        otherUserChat = new UserEntity(email, id);
    }

    private void initView() {
        lvChat = (ListView) findViewById(R.id.lvChat);
        edtInputMessage = (EditText) findViewById(R.id.edtInputMessage);
        btnSend = (Button) findViewById(R.id.btnSend);

        chatAdapter = new ChatAdapter(listMessage, getLayoutInflater());
        lvChat.setAdapter(chatAdapter);

        btnSend.setOnClickListener(onSend_Click);
    }

    private View.OnClickListener onSend_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (edtInputMessage.getText().toString().trim().length() > 0) {
                //Gửi tin nhắn cho data_myMessage
                MessageEntity message = new MessageEntity(
                        myUser.getUid(),
                        myUser.getEmail(),
                        "",
                        edtInputMessage.getText().toString());

                //Khởi tạo key cho tin nhắn sao cho key ko trùng với bất kỳ mục nào trong Firebase
                //sử dụng lệnh push()
                DatabaseReference referenceMessage = data_myMessage.child(otherUserChat.getId()).push();
                message.setId(referenceMessage.getKey());

                //Gửi tin nhắn cho data_otherUserMessage
                data_myMessage.child(otherUserChat.getId())
                        .child(message.getId())
                        .setValue(message);

                //Ghi tin nhắn cho data_otherUserMessage
                data_otherUserMessage.child(myUser.getUid())
                        .child(message.getId())
                        .setValue(message);

                edtInputMessage.setText("");
            }
        }
    };
}
