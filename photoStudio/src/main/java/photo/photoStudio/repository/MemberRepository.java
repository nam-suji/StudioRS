package photo.photoStudio.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import photo.photoStudio.domain.Member;
import photo.photoStudio.exception.UserNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public Member findByEmailPwd(String email,String pwd) {
        Member member = null;
        try {
            member = em.createQuery("select m from Member m where m.email= :email and m.pwd = :pwd", Member.class)
                    .setParameter("email", email)
                    .setParameter("pwd", pwd)
                    .getSingleResult();
        } catch (NoResultException e){
            return member;
        }
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

}
