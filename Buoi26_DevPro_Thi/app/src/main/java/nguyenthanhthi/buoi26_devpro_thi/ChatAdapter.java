package nguyenthanhthi.buoi26_devpro_thi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Administrator on 13/06/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private ArrayList<MessageEntity> listMessage;
    private LayoutInflater inflater;
    private FirebaseUser myUser = FirebaseAuth.getInstance().getCurrentUser();

    public ChatAdapter(ArrayList<MessageEntity> listMessage, LayoutInflater inflater) {
        this.listMessage = listMessage;
        this.inflater = inflater;
    }

    public ArrayList<MessageEntity> getListMessage() {
        return listMessage;
    }

    public void setListMessage(ArrayList<MessageEntity> listMessage) {
        this.listMessage = listMessage;
    }

    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public MessageEntity getItem(int position) {
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private TextView txtContentChat, txtAvatarChat;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageEntity message = listMessage.get(position);
        boolean hasAvatar = false;

        if (position == 0) {
            if (message.getIdUser().equals(myUser.getUid())) {
                convertView = inflater.inflate(R.layout.item_chat_1, null);
            } else {
                convertView = inflater.inflate(R.layout.item_chat_3, null);
            }
            hasAvatar = true;
        } else {
            if (message.getIdUser().equals(myUser.getUid())) {
                if (message.getIdUser().equals(listMessage.get(position - 1).getIdUser())) {
                    convertView = inflater.inflate(R.layout.item_chat_2, null);
                    hasAvatar = false;
                } else {
                    convertView = inflater.inflate(R.layout.item_chat_1, null);
                    hasAvatar = true;
                }
            } else {
                if (message.getIdUser().equals(listMessage.get(position - 1).getIdUser())) {
                    convertView = inflater.inflate(R.layout.item_chat_4, null);
                    hasAvatar = false;
                } else {
                    convertView = inflater.inflate(R.layout.item_chat_3, null);
                    hasAvatar = true;
                }
            }
        }
        txtContentChat = (TextView) convertView.findViewById(R.id.txtChat);
        txtContentChat.setText(message.getContent());
        if (hasAvatar) {
            txtAvatarChat = (TextView) convertView.findViewById(R.id.txtAvatar);
            if (txtAvatarChat != null) {
                txtAvatarChat.setText(message.getEmailUser().substring(0, 1).toUpperCase());
            }
        }
        return convertView;
    }
}
