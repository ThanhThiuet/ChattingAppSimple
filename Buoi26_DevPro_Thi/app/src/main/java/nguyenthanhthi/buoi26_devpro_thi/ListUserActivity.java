package nguyenthanhthi.buoi26_devpro_thi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

    //lay duong dan den thu muc goc
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    //lay thong tin user dang nhap hien tai
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ArrayList<UserEntity> listUser = new ArrayList<>();
    private ListView lvUser;
    private ListUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        updateInfoUser();
        getDataUserChat();
    }

    private void initView() {
        lvUser = (ListView) findViewById(R.id.lvUser);
        adapter = new ListUserAdapter(listUser, getLayoutInflater());
        lvUser.setAdapter(adapter);

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListUserActivity.this, ChatActivity.class);
                intent.putExtra("EMAIL", listUser.get(position).getName());
                intent.putExtra("ID", listUser.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void getDataUserChat() {
        DatabaseReference userReference = databaseReference.child("Users");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listUser.add(snapshot.getValue(UserEntity.class));
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateInfoUser() {
        DatabaseReference userReference = databaseReference.child("Users");

        //khoi tao doi tuong UserEntity de day len Firebase
        UserEntity userEntity = new UserEntity(user.getEmail().toString(), user.getUid().toString());

        //Day UserEntity len
        userReference.child(user.getUid()).setValue(userEntity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ListUserActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
