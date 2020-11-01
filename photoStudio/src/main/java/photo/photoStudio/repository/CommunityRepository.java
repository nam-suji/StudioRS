package photo.photoStudio.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import photo.photoStudio.domain.Community;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityRepository {

    private final EntityManager em;

    public void save(Community community){
        em.persist(community);
    }

    public void delete(Community community){
        em.remove(community);
    }

    public List<Community> findAll(String type){
        return em.createQuery("select c from Community c where c.type = :type",Community.class)
                .setParameter("type", type)
                .getResultList();
    }

    public Community findOne(Long id){
        return em.createQuery("select c from Community c where c.id = :id",Community.class)
                .setParameter("id",id)
                .getSingleResult();
    }

    public List<Community> findListPaging(String type,int startIndex,int pageSize){
        return em.createQuery("select c from Community c where c.type = :type", Community.class)
                .setParameter("type", type)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
