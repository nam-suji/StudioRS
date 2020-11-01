package photo.photoStudio.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberForm {

    private Long id;

    @NotEmpty(message = "이메일은 필수 입니다.")
    @Email(message = "이메일 양식이 아닙니다")
    private String email;

    @NotEmpty(message = "이름은 필수 입니다.")
    @Size(min = 1, max = 10)
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Pattern(regexp = "[A-Za-z$@1-9]{8,}",
            message = "비밀번호는 최소 8자, 최소 하나의 문자, 하나의 숫자가 필요합니다.")
    private String pwd;

    @NotEmpty(message = "휴대전화 번호는 필수 입니다.")
    @Pattern(regexp = "[012]{3}-[0123456789]{3,4}-[0123456789]{3,4}",
            message = "휴대전화 번호 양식은 010-0000-0000 입니다.")
    private String phone;

    public void updateSetMember(Long id,String email, String pwd, String name,String phone){
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
    }

}
