package nguyenthanhthi.buoi26_devpro_thi;

/**
 * Created by Administrator on 13/06/2017.
 */

public class MessageEntity {

    private String idUser;
    private String emailUser;
    private String id;
    private String content;

    public MessageEntity() {
    }

    public MessageEntity(String idUser, String emailUser, String id, String content) {
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.id = id;
        this.content = content;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
