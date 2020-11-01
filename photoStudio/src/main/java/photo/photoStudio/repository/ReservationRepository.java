package photo.photoStudio.repository;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import photo.photoStudio.domain.InputStatus;
import photo.photoStudio.domain.QMember;
import photo.photoStudio.domain.QReservation;
import photo.photoStudio.domain.Reservation;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    QReservation reservation;
    QMember member;

    public void save(Reservation reservation){
        em.persist(reservation);
    }

    public void delete(Reservation reservation){
        em.remove(reservation);
    }

    //회원 로그인 시 자신의 예약 목록 반환
    public Reservation findOne(Long id){
        return em.createQuery("select r from Reservation r where r.id = :id",Reservation.class)
                .setParameter("id",id)
                .getSingleResult();
    }
    public List<Reservation> findReservationList(Long id){
        return em.createQuery("select r from Reservation r where r.member.id = :id",Reservation.class)
                .setParameter("id",id)
                .getResultList();
    }

    //admin 로그인 시 모든 예약 목록 반환
    public List<Reservation> findAll(ReservationSearch reservationSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);

        reservation = QReservation.reservation;
        //member = QMember.member;

        return query
                .selectFrom(reservation)
                //.join(reservation.member, member)
                .where(statusEq(reservationSearch.getInputStatus())
                        //nameLike(reservationSearch.getMemberName())
                        )
                .limit(100)
                .fetch();
    }

//    private BooleanExpression nameLike(String memberName) {
//        if(memberName == null){
//            return  null;
//        }
//        return  member.name.like(memberName);
//    }

    private BooleanExpression statusEq(InputStatus inputStatus) {
        if(inputStatus == null){
            return null;
        }
        return reservation.inputStatus.eq(inputStatus);
    }

    public List<Reservation> findByReservationDate(String reservationDate,String reservationTime){
        return  em.createQuery("select r from Reservation r " +
                "where r.reservationDate = :reservationDate " +
                "and r.reservationTime = :reservationTime",Reservation.class)
                .setParameter("reservationDate", reservationDate)
                .setParameter("reservationTime", reservationTime)
                .getResultList();
    }
}
