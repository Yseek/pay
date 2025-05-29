package com.pay.point.repository;

import com.pay.point.domain.PointWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointWalletRepository extends JpaRepository<PointWallet, Integer> {
    Optional<PointWallet> findByEmail(String email);

}
