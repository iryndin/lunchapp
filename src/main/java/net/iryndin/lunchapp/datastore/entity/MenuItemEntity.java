package net.iryndin.lunchapp.datastore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Menu item database entity
 */
@Entity
@Table(name="menu")
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double price;
    private boolean deleted;
    @ManyToOne(optional=false)
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant;
    @Column(name="create_date", nullable = false)
    private Date createDate;
    @Column(name="update_date", nullable = false)
    private Date updateDate;

    public MenuItemEntity() {
    }

    public MenuItemEntity(Long id, String name, double price, boolean deleted, RestaurantEntity restaurant, Date createDate, Date updateDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.deleted = deleted;
        this.restaurant = restaurant;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
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
}
