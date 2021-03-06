package net.iryndin.lunchapp.datastore.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Restaurant database entity
 */
@Entity
@Table(name="restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean deleted;
    @Column(name="create_date", nullable = false)
    private Date createDate;
    @Column(name="update_date", nullable = false)
    private Date updateDate;

    public RestaurantEntity() {
    }

    public RestaurantEntity(Long id, String name, boolean deleted, Date createDate, Date updateDate) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantEntity that = (RestaurantEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
