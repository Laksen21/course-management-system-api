package lk.cms.course_management_system.repository;

import lk.cms.course_management_system.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
}
