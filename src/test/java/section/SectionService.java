package section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.example.exception.NotFoundException;
import tiles.Tiles;
@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Section getSectionById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Section not found with id: " + id));
    }

    public Section addSection(Section section) {
        System.out.println("SectionService.addSection: " + section);
        System.out.println("SectionService.addSection: " + section.getTiles());
        System.out.println("SectionService.title: " + section.getTitle());
        return sectionRepository.save(section);
    }

    public Section updateSection(Long id, Section section) {
        Section existingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Section not found with id: " + id));

        // Update the properties of the existingSection object using section

        return sectionRepository.save(existingSection);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }

    public Section addTilesToSection(Long id, Tiles tiles) {
        Section existingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Section not found with id: " + id));

        // Update the properties of the existingSection object using section
        existingSection.getTiles().add(tiles);
        return sectionRepository.save(existingSection);
    }
}
