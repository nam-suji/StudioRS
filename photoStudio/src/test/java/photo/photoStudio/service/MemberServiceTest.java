package photo.photoStudio.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import photo.photoStudio.domain.Member;
import photo.photoStudio.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");

        //when
        Long id = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(id));
    }

    @Test
    public void 중복가입() throws Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");

        Member member1 = new Member();
        member1.setMember("abc@naver.com","zxasqw!12","nam","12345");

        //when
        memberService.join(member);
        try{
            memberService.join(member1);
        }catch(IllegalStateException e){
            return ;
        }
    }

    @Test
    public void 회원정보수정() throws  Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");
        Long id = memberService.join(member);

        //when
        memberService.updateMember(id,"1234","name","010-4753");

        //then
        assertEquals(true, member.equals(memberService.findOne(id)));
    }

    @Test
    public void 회원정보삭제() throws Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");
        Long id = memberService.join(member);

        //when
        memberService.deleteMember(id);

        //then
        try{
            memberRepository.findOne(id);
        } catch (EmptyResultDataAccessException e){
            return ;
        }
    }

}