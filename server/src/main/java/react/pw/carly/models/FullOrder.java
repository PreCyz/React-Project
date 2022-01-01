package react.pw.carly.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import react.pw.carly.utils.JsonDateDeserializer;
import react.pw.carly.utils.JsonDateSerializer;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullOrder {


    private long orderId;


    private String booklyId;


    private String firstName;


    private String lastName;

    private String status;


    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate startDate;


    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate endDate;


    private long carId;

    private String carName;


    private String carModel;


    private BigDecimal price;


    private String location;


    private String description;


    private String pic1;


    private String pic2;


    private String pic3;
}
