package lk.cms.course_management_system.repository;

import lk.cms.course_management_system.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User getUserByUsername(String username);
}
