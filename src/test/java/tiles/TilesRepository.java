package tiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TilesRepository extends JpaRepository<Tiles, Long> {
    // Add custom query methods if needed
}
