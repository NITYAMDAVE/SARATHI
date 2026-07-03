package org.nityam.sarathi.Repo;

import org.nityam.sarathi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
