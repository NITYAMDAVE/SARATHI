package org.nityam.sarathi.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
    @OneToMany(mappedBy = "platform")
    private List<Facility> facilities;
    private Boolean active;
    private String description;
    private int platformNumber;
}
