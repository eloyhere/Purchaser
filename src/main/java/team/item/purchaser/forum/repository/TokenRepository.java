package team.item.purchaser.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import team.item.purchaser.forum.entity.Token;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, String>, JpaSpecificationExecutor<Token> {
    @Query("select (count(t) > 0) from Token t where t.username = :username")
    boolean existsByUsername(@Param("username") @NonNull String username);

    @Query("select t from Token t where t.username = :username")
    Optional<Token> findByUsername(@Param("username") @NonNull String username);

    @Transactional
    @Modifying
    @Query("delete from Token t where t.username = :username")
    int deleteByUsername(@Param("username") @NonNull String username);



}