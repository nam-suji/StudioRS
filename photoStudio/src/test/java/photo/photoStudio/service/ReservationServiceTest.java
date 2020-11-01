package photo.photoStudio.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import photo.photoStudio.domain.InputStatus;
import photo.photoStudio.domain.Item;
import photo.photoStudio.domain.Member;
import photo.photoStudio.domain.Reservation;
import photo.photoStudio.repository.ItemRepository;
import photo.photoStudio.repository.MemberRepository;
import photo.photoStudio.repository.ReservationRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired EntityManager em;
    @Autowired ReservationService reservationService;
    @Autowired ReservationRepository reservationRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;


    @Test
    public void 예약() throws Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");
        Long id = memberService.join(member);

        Item item = new Item();
        item.setItem("증명사진", 20000);
        Long itemId = itemService.saveItem(item);

        //when
        Long rsId = reservationService.saveReservation(id,itemId,"2020-10-26","10:00");

        //then
        System.out.println(rsId);
        System.out.println(reservationRepository.findOne(rsId).getReservationListDateTime());
    }

    @Test
    public void 입금확인() throws Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");
        Long id = memberService.join(member);

        Item item = new Item();
        item.setItem("증명사진", 20000);
        Long itemId = itemService.saveItem(item);

        Long reservationId = reservationService.saveReservation(id,itemId,"2020-10-26","10:00");

        //when
        reservationService.checkInput(reservationId);

        //then
        Reservation getReservation = reservationRepository.findOne(reservationId);

        assertEquals(InputStatus.INPUT_O, getReservation.getInputStatus());

    }

    @Test
    public void 에약취소() throws Exception{
        //given
        Member member = new Member();
        member.setMember("abc@naver.com","zxasqw!12","nam","12345");
        Long id = memberService.join(member);

        Item item = new Item();
        item.setItem("증명사진", 20000);
        Long itemId = itemService.saveItem(item);

        Long reservationId = reservationService.saveReservation(id,itemId,"2020-10-26","10:00");

        //when
        reservationService.cancel(reservationId);

        //then
        try{
            reservationService.findOne(reservationId);
        } catch(EmptyResultDataAccessException e){
            System.out.println("오류");
            return ;
        }

    }
}