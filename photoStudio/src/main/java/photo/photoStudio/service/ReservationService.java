package photo.photoStudio.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photo.photoStudio.domain.InputStatus;
import photo.photoStudio.domain.Item;
import photo.photoStudio.domain.Member;
import photo.photoStudio.domain.Reservation;
import photo.photoStudio.repository.ItemRepository;
import photo.photoStudio.repository.MemberRepository;
import photo.photoStudio.repository.ReservationRepository;
import photo.photoStudio.repository.ReservationSearch;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 예약
     */
    @Transactional
    public Long saveReservation(Long memberId, Long itemId,String phone, String date, String time,String inputName){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        List<Reservation> reservations = reservationRepository.findByReservationDate(date,time);

        if(reservations.isEmpty()) {
            //예약 생성
            Reservation reservation = Reservation.createReservation(member, item, phone, inputName, date, time);
            //예약 저장
            reservationRepository.save(reservation);
            return reservation.getId();
        }else{
            return null ;
        }
    }

    /**
     * 예약 취소
     */
    @Transactional
    public void cancel(Long reservationId){
        //예약 엔티티 조회
        Reservation reservation = reservationRepository.findOne(reservationId);

        //예약 삭제
        reservationRepository.delete(reservation);
    }

    /**
     * 예약 입금 확인
     */
    @Transactional
    public void checkInput(Long reservationId){
        //예약 엔티티 조회
        Reservation reservation = reservationRepository.findOne(reservationId);

        //예약 입금 상태 변경
        reservation.input();
    }

    public List<Reservation> findAdminReservations(ReservationSearch reservationSearch){
        return reservationRepository.findAll(reservationSearch);
    }


    public List<Reservation> findReservations(Long memberId){
        return reservationRepository.findReservationList(memberId);
    }

    public Reservation findOne(Long reservationId){
        return reservationRepository.findOne(reservationId);
    }

}
