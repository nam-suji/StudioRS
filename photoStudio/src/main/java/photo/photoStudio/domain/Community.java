package photo.photoStudio.domain;

import lombok.Getter;
import photo.photoStudio.domain.Member;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
public class Community {

    @Id @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private Long memberId;
    private String name;
    private String date;
    private String title;
    private String content;
    private String type;


    public void setCommunity(Long memberId,String name, String type, String title, String content){
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        this.memberId = memberId;//session으로부터 받아옴
        this.name = name;
        this.type = type;
        this.title = title;
        this.content = content;
        this.date = simpleDateFormat.format(nowDate);
    }

    public void updateSetCommunity(String title, String content){
        this.title = title;
        this.content = content;
    }
}
