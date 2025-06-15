package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
