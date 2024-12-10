package com.example.springbatchbasic.job.repository_read_write.domain.account.repository;

import com.example.springbatchbasic.job.repository_read_write.domain.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
}
