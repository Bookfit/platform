package com.bookfit.www.map.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_id", nullable = false)
    private Integer sampleId;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 20)
    @NotNull
    @ColumnDefault("'PENDING'")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private com.bookfit.www.map.db.entity.User user;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 50)
    @Column(name = "weekday_hours", length = 50)
    private String weekdayHours;
    @Size(max = 50)
    @Column(name = "weekend_hours", length = 50)
    private String weekendHours;

/*
 TODO [Reverse Engineering] create field to map the 'location' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "location", columnDefinition = "geometry")
    private Object location;
*/
}