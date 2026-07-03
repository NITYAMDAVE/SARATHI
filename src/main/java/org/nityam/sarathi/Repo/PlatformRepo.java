package org.nityam.sarathi.Repo;

import org.nityam.sarathi.Model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepo extends JpaRepository<Platform,Integer> {
    Platform findByPlatformNumber(int platformNumber);
}
