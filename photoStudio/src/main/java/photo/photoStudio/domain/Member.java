package photo.photoStudio.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    @NotNull
    private String pwd;
    @NotNull
    private String name;
    @NotNull
    private String phone;

    public void setMember(String email, String pwd, String name, String phone){
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
    }
    public void updateSetMember(String pwd, String name,String phone){
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
    }

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

}
