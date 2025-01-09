package wruh.pkmn.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wruh.pkmn.entities.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByFirstNameAndFamilyNameAndSurName(String firstName, String familyName, String surName);

    List<StudentEntity> findAllByGroup(String group);
}
