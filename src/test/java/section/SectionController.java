package section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tiles.Tiles;
import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public Section getSectionById(@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    @PostMapping
    public Section addSection(@RequestBody Section section) {

        System.out.println("SectionController.addSection: " + section);
        return sectionService.addSection(section);
    }

    @PutMapping("/{id}")
    public Section updateSection(@PathVariable Long id, @RequestBody Section section) {
        return sectionService.updateSection(id, section);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
    }

    @PutMapping("/{id}/tiles")
    public Section addTilesToSection(@PathVariable Long id, @RequestBody Tiles tiles) {
        return sectionService.addTilesToSection(id, tiles);
    }
}
