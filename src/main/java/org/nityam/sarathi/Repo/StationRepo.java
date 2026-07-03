package org.nityam.sarathi.Repo;

import org.nityam.sarathi.Model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station,Integer> {
    Station findByname(String station);
}
