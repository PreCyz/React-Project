package react.pw.carly.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import react.pw.carly.models.CarImage;
import react.pw.carly.models.FullOrder;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CarImageRepository extends JpaRepository<CarImage, String> {
//    Optional<CarImage> findByCompanyId(long companyId);
//    void deleteByCompanyId(long companyId);


}
