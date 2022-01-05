package react.pw.carly.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import react.pw.carly.utils.JsonDateDeserializer;
import react.pw.carly.utils.JsonDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullOrder {
    public static FullOrder EMPTY = new FullOrder();
    public FullOrder(long orderId, String booklyId, String firstName, String lastName, String status, LocalDateTime startDate, LocalDateTime
            endDate, long carId, String carName, String carModel, BigDecimal price, String location, String description, Object images) {
        this.orderId = orderId;
        this.booklyId = booklyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.carId = carId;
        this.carName = carName;
        this.carModel = carModel;
        this.price = price;
        this.location = location;
        this.description = description;
        this.images = (String[])images;
    }

    private long orderId;


    private String booklyId;


    private String firstName;


    private String lastName;

    private String status;


    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime startDate;


    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime endDate;


    private long carId;

    private String carName;


    private String carModel;


    private BigDecimal price;


    private String location;


    private String description;

    private String[] images;
}
