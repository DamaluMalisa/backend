package com.zahara.lms.faculty.repository;

import com.zahara.lms.faculty.model.City;
import com.zahara.lms.shared.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends BaseRepository<City, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.name like :search)")
    Page<City> findContaining(Pageable pageable, String search);
}
