package auca.ac.rw.Online.quiz.management.repository;

import auca.ac.rw.Online.quiz.management.model.Location;
import auca.ac.rw.Online.quiz.management.model.User;
import auca.ac.rw.Online.quiz.management.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByLocationType(LocationType locationType);
    List<Location> findByParentId(Long parentId);
    
    /**
     * Assessment Requirement: Retrieve all users from a given location and all its sub-locations.
     * Logic: This query uses 'JOIN' to link Users to their Village, and then 'LEFT JOIN' to climb 
     * up the hierarchy (Village -> Cell -> Sector -> District -> Province).
     * It returns a list of users if their direct location OR any of its parents matches the given ID.
     */
    @Query("SELECT u FROM User u " +
           "JOIN u.location v " +
           "LEFT JOIN v.parent c " +
           "LEFT JOIN c.parent s " +
           "LEFT JOIN s.parent d " +
           "LEFT JOIN d.parent p " +
           "WHERE v.id = :locationId OR c.id = :locationId OR s.id = :locationId OR d.id = :locationId OR p.id = :locationId")
    List<User> findAllUsersInLocationHierarchy(@Param("locationId") Long locationId);
}


