package photo.photoStudio.repository;

import lombok.Getter;
import lombok.Setter;
import photo.photoStudio.domain.InputStatus;

@Getter @Setter
public class ReservationSearch {

    private String memberName;
    private InputStatus inputStatus;
}
