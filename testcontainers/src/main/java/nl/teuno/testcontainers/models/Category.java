package nl.teuno.testcontainers.models;

import javax.persistence.*;

@Entity
@Table(name = "tbl_category")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public Category setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return this;
    }
}

