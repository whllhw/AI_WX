package xyz.whllhw.label;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<LabelUserEntity, Long> {
    List<LabelUserEntity> findAllByUser(String user);
}
