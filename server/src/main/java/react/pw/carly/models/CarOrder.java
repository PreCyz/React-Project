package react.pw.carly.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import react.pw.carly.utils.JsonDateDeserializer;
import react.pw.carly.utils.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "carOrder")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarOrder implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static CarOrder EMPTY = new CarOrder();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @Column(name = "booklyId")
    private String booklyId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "status")
    private String status;

    @Column(name = "startDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate startDate;

    @Column(name = "endDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate endDate;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "carId")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne (fetch = FetchType.LAZY)
    private Car car;

}
