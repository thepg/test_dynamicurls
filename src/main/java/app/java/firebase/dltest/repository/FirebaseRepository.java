package app.java.firebase.dltest.repository;

import app.java.firebase.dltest.entity.FirebaseBody;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirebaseRepository extends CrudRepository<FirebaseBody, Long> {
    Optional<FirebaseBody> findById(Long id);

    @Override
    Iterable<FirebaseBody> findAll();
}
