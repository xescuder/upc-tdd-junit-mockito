package talent.upc.edu.booking;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;;

import talent.upc.edu.booking.controller.RoomController;
import talent.upc.edu.booking.model.Room;
import talent.upc.edu.booking.service.RoomService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {
    @Autowired
    private MockMvcTester mockMvc;

    @MockitoBean
    private RoomService roomService;

    @Test
    void should_ReturnAvailableRooms_WhenRoomsStored() throws UnsupportedEncodingException, JSONException {
        // given
        Room room = Room.builder().pricePerNight(100.0).capacity(2).build();
        given(this.roomService.findAvailableRoom(any(LocalDate.class), any(LocalDate.class), anyInt())).willReturn(room);

        String expectedJson = """
            {
                "id":0,
                "capacity":2,
                "pricePerNight":100.0,
                "photo":null,
                "bookings":null
            }
        """;

        // when
        LocalDate checkInDate = LocalDate.of(2025, Month.APRIL, 2);
        LocalDate checkOutDate = LocalDate.of(2025, Month.APRIL, 5);

        MvcTestResult result = this.mockMvc.get().uri("/rooms/available")
                .param("checkInDate", checkInDate.toString())
                .param("checkOutDate", checkOutDate.toString())
                .param("totalGuests", "2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        assertThat(result).hasStatusOk();
        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), true);
    }

}
