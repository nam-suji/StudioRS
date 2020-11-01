package photo.photoStudio.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photo.photoStudio.domain.Member;
import photo.photoStudio.exception.UserNotFoundException;
import photo.photoStudio.repository.MemberRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public List<Member> findName(String name) throws UserNotFoundException {
        return memberRepository.findByName(name);
    }

    public Member findEmailPwd(String email,String pwd) {
        Member member = memberRepository.findByEmailPwd(email,pwd);
        if(member==null){
            return member;
        }
        return member;
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public void updateMember(Long id, String pwd, String name, String phone){
        Member member = memberRepository.findOne(id);
        member.updateSetMember(pwd, name, phone);
    }

    public void deleteMember(Long id){
        Member member = memberRepository.findOne(id);
        memberRepository.delete(member);
    }
}
