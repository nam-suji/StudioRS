package photo.photoStudio.domain;

import lombok.Getter;
import lombok.Setter;

import javax.jdo.annotations.Unique;
import javax.persistence.*;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    Long id;

    @Unique
    private String name;
    private int price;

    public void setItem(String name, int price){
        this.name = name;
        this.price = price;
    }

    public void setUpdateItem(Long id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
