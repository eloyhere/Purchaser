package team.item.purchaser.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.item.purchaser.forum.entity.Permission;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}