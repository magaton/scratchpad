package com.magaton.scratchpad.model;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "mortgage")
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "mortgage")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "tasks" })
public class Mortgage {

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("tasks").toString();
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

	@OneToMany(mappedBy = "mortgage")
    private Set<Task> tasks;

	@Column(name = "mortgage_amount")
    @NotNull
    private Double mortgageAmount;

	@Column(name = "interest_rate")
    @NotNull
    private Double interestRate;

	@Column(name = "applicant_income")
    @NotNull
    private Double applicantIncome;

	@Column(name = "loan_to_value")
    @NotNull
    private Integer loanToValue;

	@Column(name = "condo")
    @NotNull
    private Short condo;

	@Column(name = "risk")
    @NotNull
    private Double risk;

	public Set<Task> getTasks() {
        return tasks;
    }

	public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

	public Double getMortgageAmount() {
        return mortgageAmount;
    }

	public void setMortgageAmount(Double mortgageAmount) {
        this.mortgageAmount = mortgageAmount;
    }

	public Double getInterestRate() {
        return interestRate;
    }

	public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

	public Double getApplicantIncome() {
        return applicantIncome;
    }

	public void setApplicantIncome(Double applicantIncome) {
        this.applicantIncome = applicantIncome;
    }

	public Integer getLoanToValue() {
        return loanToValue;
    }

	public void setLoanToValue(Integer loanToValue) {
        this.loanToValue = loanToValue;
    }

	public Short getCondo() {
        return condo;
    }

	public void setCondo(Short condo) {
        this.condo = condo;
    }

	public Double getRisk() {
        return risk;
    }

	public void setRisk(Double risk) {
        this.risk = risk;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Mortgage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countMortgages() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Mortgage o", Long.class).getSingleResult();
    }

	public static List<Mortgage> findAllMortgages() {
        return entityManager().createQuery("SELECT o FROM Mortgage o", Mortgage.class).getResultList();
    }

	public static Mortgage findMortgage(Integer id) {
        if (id == null) return null;
        return entityManager().find(Mortgage.class, id);
    }

	public static List<Mortgage> findMortgageEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Mortgage o", Mortgage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Mortgage attached = Mortgage.findMortgage(this.id);
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
    public Mortgage merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Mortgage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
