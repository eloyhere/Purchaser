package team.item.purchaser.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import team.item.purchaser.forum.entity.Consumer;

import java.util.Optional;
import java.util.UUID;

public interface ConsumerRepository extends JpaRepository<Consumer, UUID>, JpaSpecificationExecutor<Consumer> {

    @Query("select (count(c) > 0) from Consumer c where c.phone = :phone")
    boolean existsByPhone(@Param("phone") @NonNull String phone);

    @Query("select c from Consumer c where c.phone = :phone")
    Optional<Consumer> findByPhone(@Param("phone") @NonNull String phone);

    @Query("select (count(c) > 0) from Consumer c where c.phone = :phone and c.password = :password")
    boolean existsByPhoneAndPassword(@Param("phone") @NonNull String phone, @Param("password") @NonNull String password);

    @Query("select c from Consumer c where c.phone = :phone and c.password = :password")
    Optional<Consumer> findByPhoneAndPassword(@Param("phone") @NonNull String phone, @Param("password") @NonNull String password);

    @Query("select (count(c) > 0) from Consumer c where c.email = :email")
    boolean existsByEmail(@Param("email") @NonNull String email);

    @Query("select c from Consumer c where c.email = :email")
    Optional<Consumer> findByEmail(@Param("email") @NonNull String email);

    @Query("select (count(c) > 0) from Consumer c where c.username = :username and c.password = :password")
    boolean existsByUsernameAndPassword(@Param("username") @NonNull String username, @Param("password") @NonNull String password);

    @Query("select c from Consumer c where c.username = :username and c.password = :password")
    Optional<Consumer> findByUsernameAndPassword(@Param("username") @NonNull String username, @Param("password") @NonNull String password);

    @Query("select (count(c) > 0) from Consumer c where c.email = :email and c.password = :password")
    boolean existsByEmailAndPassword(@Param("email") @NonNull String email, @Param("password") @NonNull String password);

    @Query("select c from Consumer c where c.email = :email and c.password = :password")
    Optional<Consumer> findByEmailAndPassword(@Param("email") @NonNull String email, @Param("password") @NonNull String password);

    @Query("select (count(c) > 0) from Consumer c where c.id = :id and c.password = :password")
    boolean existsByIdAndPassword(@Param("id") @NonNull UUID id, @Param("password") @NonNull String password);

    @Query("select c from Consumer c where c.id = :id and c.password = :password")
    Optional<Consumer> findByIdAndPassword(@Param("id") @NonNull UUID id, @Param("password") @NonNull String password);

    @Query("select (count(c) > 0) from Consumer c where c.username = :username")
    boolean existsByUsername(@Param("username") @NonNull String username);

    @Query("select c from Consumer c where c.username = :username")
    Optional<Consumer> findByUsername(@Param("username") @NonNull String username);




}