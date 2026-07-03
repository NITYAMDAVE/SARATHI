package org.nityam.sarathi.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nityam.sarathi.Repo.PlatformRepo;
import org.nityam.sarathi.Repo.StationRepo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String stationCode;
    @ManyToOne
    @JoinColumn(name="region_id")
    private Region region;
    private String city;
    private String state;
    @OneToMany(mappedBy = "station")
    private List<Platform> platforms;
    private String description;
    private Boolean active;
    private Double latitude;
    private Double longitude;
    private int intermediatePlatformDistance;
}
