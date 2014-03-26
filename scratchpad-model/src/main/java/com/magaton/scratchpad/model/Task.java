package com.magaton.scratchpad.model;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "task")
@Configurable
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "task")
public class Task {

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("userId", "mortgageId").toString();
    }

	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

	@ManyToOne
    @JoinColumn(name = "mortgage_id", referencedColumnName = "id", nullable = false)
    private Mortgage mortgage;

	@Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
    private Date startDate;

	@Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
    private Date endDate;

	public User getUser() {
        return user;
    }

	public void setUser(User user) {
        this.user = user;
    }

	public Mortgage getMortgage() {
        return mortgage;
    }

	public void setMortgage(Mortgage mortgage) {
        this.mortgage = mortgage;
    }

	public Date getStartDate() {
        return startDate;
    }

	public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

	public Date getEndDate() {
        return endDate;
    }

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Task().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countTasks() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Task o", Long.class).getSingleResult();
    }

	public static List<Task> findAllTasks() {
        return entityManager().createQuery("SELECT o FROM Task o", Task.class).getResultList();
    }

	public static Task findTask(Integer id) {
        if (id == null) return null;
        return entityManager().find(Task.class, id);
    }

	public static List<Task> findTaskEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Task o", Task.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Task attached = Task.findTask(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Task merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Task merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
