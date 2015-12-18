package net.iryndin.lunchapp.datastore.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * User action - we log all actions of users
 */
@Entity
@Table(name="user_action")
public class UserActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String entityName;
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private AppUserEntity user;
    @Column(name="create_date", nullable = false)
    private Date createDate;
    private String action;
    private String entityId;

    public UserActionEntity() {
    }

    public UserActionEntity(String entityName, AppUserEntity user, String action, String entityId) {
        this(entityName, user, new Date(), action, entityId);
    }

    public UserActionEntity(String entityName, AppUserEntity user, Date createDate, String action, String entityId) {
        this.entityName = entityName;
        this.user = user;
        this.createDate = createDate;
        this.action = action;
        this.entityId = entityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
