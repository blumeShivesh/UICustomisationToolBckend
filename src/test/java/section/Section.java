package section;

import org.example.JsonStorage.JsonStorage;
import tiles.Tiles;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "json_storage")

public class Section extends JsonStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean isDragEnabled;
    private boolean isDropEnabledAllSections;
    @ManyToMany(mappedBy = "dropEnabledSections")
    private List<Section> itemsDropEnabledSections;

    @ManyToMany
    @JoinTable(
            name = "section_drop_enabled_sections",
            joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "drop_enabled_section_id")
    )
    private List<Section> dropEnabledSections;
    private boolean isDropEnable;
    private boolean isItemDragEnabled;
    private boolean isItemDropEnabled;
    private String className;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private List<Tiles> tilesList;
    // ... include other properties as needed

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDragEnabled() {
        return isDragEnabled;
    }

    public void setDragEnabled(boolean dragEnabled) {
        isDragEnabled = dragEnabled;
    }

    public boolean isDropEnabledAllSections() {
        return isDropEnabledAllSections;
    }

    public void setDropEnabledAllSections(boolean dropEnabledAllSections) {
        isDropEnabledAllSections = dropEnabledAllSections;
    }

    public List<Section> getDropEnabledSections() {
        return dropEnabledSections;
    }

    public void setDropEnabledSections(List<Section> dropEnabledSections) {
        this.dropEnabledSections = dropEnabledSections;
    }

    public List<Section> getItemsDropEnabledSections() {
        return itemsDropEnabledSections;
    }

    public void setItemsDropEnabledSections(List<Section> itemsDropEnabledSections) {
        this.itemsDropEnabledSections = itemsDropEnabledSections;
    }

    public boolean isDropEnable() {
        return isDropEnable;
    }

    public void setDropEnable(boolean dropEnable) {
        isDropEnable = dropEnable;
    }

    public boolean isItemDragEnabled() {
        return isItemDragEnabled;
    }

    public void setItemDragEnabled(boolean itemDragEnabled) {
        isItemDragEnabled = itemDragEnabled;
    }

    public boolean isItemDropEnabled() {
        return isItemDropEnabled;
    }

    public void setItemDropEnabled(boolean itemDropEnabled) {
        isItemDropEnabled = itemDropEnabled;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Tiles> getTilesList() {
        return tilesList;
    }

    public void setTilesList(List<Tiles> tilesList) {
        this.tilesList = tilesList;
    }
    public List<Tiles> getTiles() {
        return tilesList;
    }
    // Relationships with other entities (if any)
}
