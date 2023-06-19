package tiles;
import org.example.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TilesService {
    @Autowired
    private TilesRepository tilesRepository;

    public List<Tiles> getAllItems() {
        return tilesRepository.findAll();
    }

    public Tiles getItemById(Long id) {
        return tilesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));
    }

    public Tiles addItem(Tiles item) {
        System.out.println("TilesService.addItem: " + item);
        System.out.println("TilesService.addItem: " + item.getSection());

        return tilesRepository.save(item);
    }

    public Tiles updateItem(Long id, Tiles tile) {
        Tiles existingItem = tilesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        // Update the properties of the existingItem object using item

        return tilesRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        tilesRepository.deleteById(id);
    }
}
