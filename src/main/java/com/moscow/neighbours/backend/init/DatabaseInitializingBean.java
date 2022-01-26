package com.moscow.neighbours.backend.init;

import com.moscow.neighbours.backend.db.datasource.RoleRepository;
import com.moscow.neighbours.backend.db.model.DBRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializingBean implements InitializingBean {

    private final Environment environment;
    private final RoleRepository roleRepository;

    /**
     * Creates new {@link DatabaseInitializingBean}
     * @param environment environment
     * @param roleRepository repository class for DB logic with roles
     */
    @Autowired
    public DatabaseInitializingBean(
            Environment environment,
            RoleRepository roleRepository
    ) {
        this.environment = environment;
        this.roleRepository = roleRepository;
    }

    @Override
    public void afterPropertiesSet() {
        if (roleRepository.findAll().size() == 0) {
            roleRepository.saveAll(List.of(new DBRole("ROLE_ADMIN"), new DBRole("ROLE_USER")));
        }
    }
}
