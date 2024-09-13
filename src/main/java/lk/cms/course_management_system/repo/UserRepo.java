package lk.cms.course_management_system.repo;

import lk.cms.course_management_system.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {

    User getUserByUsername(String username);
}
