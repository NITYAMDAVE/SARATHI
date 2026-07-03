package org.nityam.sarathi.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity



public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;
    private int x;
    private int y;
    @Enumerated(EnumType.STRING)
    private FacilityType type;
    private String description;
    private Boolean active;
    public enum FacilityType{

        TOILET,

        WATER,

        FOOD_COURT,

        LIFT,

        ESCALATOR,

        STAIRS,

        TICKET_COUNTER,

        MEDICAL,

        BENCH,

        RPF,

        HELPDESK,

        EXIT,

        ENTRY
    }
}
