package nguyenthanhthi.buoi26_devpro_thi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 08/06/2017.
 */

public class ListUserAdapter extends BaseAdapter {

    private ArrayList<UserEntity> listUser;
    private LayoutInflater inflater;

    public ListUserAdapter(ArrayList<UserEntity> listUser, LayoutInflater inflater) {
        this.listUser = listUser;
        this.inflater = inflater;
    }

    public ArrayList<UserEntity> getListUser() {
        return listUser;
    }

    public void setListUser(ArrayList<UserEntity> listUser) {
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public UserEntity getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private TextView txtAvatar, txtEmail;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_user, null);
        }
        txtAvatar = (TextView) convertView.findViewById(R.id.txtAvatar);
        txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);

        txtAvatar.setText(listUser.get(position).getName().substring(0, 1).toUpperCase());
        txtEmail.setText(listUser.get(position).getName());

        return convertView;
    }
}
