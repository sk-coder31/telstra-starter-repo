package au.com.telstra.simcardactivator;

import au.com.telstra.simcardactivator.model.SimActivationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimActivationRepository extends JpaRepository<SimActivationModel,Long> {
}
