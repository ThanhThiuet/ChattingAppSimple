package nguyenthanhthi.buoi26_devpro_thi;

/**
 * Created by Administrator on 01/06/2017.
 */

public class UserEntity {
    private String name;
    private String id;

    public UserEntity() {}

    public UserEntity(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
