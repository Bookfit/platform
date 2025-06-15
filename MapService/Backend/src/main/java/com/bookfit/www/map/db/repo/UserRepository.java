package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserBySocialUniqueIdAndSocialType(@NotNull String socialUniqueId, @NotNull String socialType);
}
