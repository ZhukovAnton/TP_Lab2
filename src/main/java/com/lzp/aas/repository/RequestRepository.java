package com.lzp.aas.repository;

import com.lzp.aas.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("from Request where isRequestOpen = true order by createdAt desc ")
    List<Request> findAllOpenRequests();

}
