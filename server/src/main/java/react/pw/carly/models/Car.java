package react.pw.carly.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import react.pw.carly.utils.JsonDateDeserializer;
import react.pw.carly.utils.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Car implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static Car EMPTY = new Car();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long carId;

    @Column(name = "name")
    private String carName;

    @Column(name = "carModel")
    private String carModel;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

//    @Column(name = "pic1")
//    private String pic1;
//
//    @Column(name = "pic2")
//    private String pic2;
//
//    @Column(name = "pic3")
//    private String pic3;

    @Type( type = "json" )
    @Column(columnDefinition = "json" ,name = "images")
    private String[] images;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "startDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime startDateTime;

    @Column(name = "endDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime endDateTime;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

    @OneToMany(mappedBy="car",fetch=FetchType.LAZY)
    private List<CarOrder> orders = new ArrayList<CarOrder>();
}
