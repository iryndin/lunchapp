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
 * User vote
 */
@Entity
@Table(name="user_vote")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional=false)
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant;
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private AppUserEntity user;
    @Column(name="create_date", nullable = false)
    private Date createDate;

    public VoteEntity() {
    }

    public VoteEntity(Long id, RestaurantEntity restaurant, AppUserEntity user, Date createDate) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public AppUserEntity getUser() {
        return user;
    }

    public void setUser(AppUserEntity user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
