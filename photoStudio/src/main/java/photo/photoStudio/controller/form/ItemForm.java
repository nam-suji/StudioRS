package photo.photoStudio.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemForm {

    private Long id;

    @NotEmpty(message = "서비스 명은 필수 입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입니다.")
    private int price;
}
