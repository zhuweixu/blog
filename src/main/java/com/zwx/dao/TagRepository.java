package com.zwx.dao;

import com.zwx.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

     @Query("select t from Tag t")
     List<Tag> findTop(Pageable pageable);

     Tag findByname(String name);
}