package tiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class TilesController {
    @Autowired
    private TilesService tilesService;

    @GetMapping
    public List<Tiles> getAllItems() {
        return tilesService.getAllItems();
    }

    @GetMapping("/{id}")
    public Tiles getItemById(@PathVariable Long id) {
        return tilesService.getItemById(id);
    }

    @PostMapping
    public Tiles addItem(@RequestBody Tiles item) {
        System.out.println("TilesController.addItem: " + item);
        return tilesService.addItem(item);
    }

    @PutMapping("/{id}")
    public Tiles updateItem(@PathVariable Long id, @RequestBody Tiles item) {
        return tilesService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        tilesService.deleteItem(id);
    }
}
