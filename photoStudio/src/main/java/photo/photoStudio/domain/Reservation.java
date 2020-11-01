package photo.photoStudio.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reservation_item")
    private Item item;

    private String inputName;
    private String phone;
    private String reservationDate; //html 에서 넘겨 받은 예약 날짜
    private String reservationTime; //html 에서 넘겨 받은 예약 시간
    private LocalDateTime reservationListDateTime; //예약 목록에 저장된 날짜시간

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Enumerated(EnumType.STRING)
    private InputStatus inputStatus;

    //== set 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getReservations().add(this);
    }
    //html에서 넘겨 받은 값
    public void setDateTime(String reservationDate, String reservationTime){
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }
    public void setReservation(Item item,String phone, String inputName, ReservationStatus reservationStatus, InputStatus inputStatus, LocalDateTime reservationListDateTime){
        this.item = item;
        this.phone = phone;
        this.inputName = inputName;
        this.reservationStatus = reservationStatus;
        this.inputStatus = inputStatus;
        this.reservationListDateTime = reservationListDateTime;
    }
    public void setInput(InputStatus inputStatus){
        this.inputStatus = inputStatus;
    }


    //==생성 메서드 ==//
    public static Reservation createReservation(Member member, Item item,String phone, String inputName, String reservationDate, String reservationTime){
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setDateTime(reservationDate, reservationTime);
        reservation.setReservation(item, phone, inputName, ReservationStatus.RESERVATION,InputStatus.INPUT_X, LocalDateTime.now());

        return reservation;
    }

    /**
     * 입금 확인
     */
    public void input(){
        this.setInput(InputStatus.INPUT_O);
    }
}