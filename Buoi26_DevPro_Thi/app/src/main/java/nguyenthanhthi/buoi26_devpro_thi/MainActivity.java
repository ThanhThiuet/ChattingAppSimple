package nguyenthanhthi.buoi26_devpro_thi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txtHello);

        //push data
//        reference.setValue("Hello");

        //get data from CauChao
        reference.child("CauChao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txt.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        reference.child("CauChao").setValue("Hello");
//        reference.child("SayHi").setValue("Hi"); //nếu cùng 1 key thì sẽ ghi đè lên cái trước

        //xóa value
        reference.child("CauChao").removeValue();

        //đẩy 1 đối tượng
        UserEntity user1 = new UserEntity("Nguyen Thanh Thi", "1");
        reference.child("Users").setValue(user1);

        // lấy dữ liệu
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                txt.setText(userEntity.getId() + " - " + userEntity.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
