package com.codewithniteesh.blog.repositories;

import com.codewithniteesh.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
