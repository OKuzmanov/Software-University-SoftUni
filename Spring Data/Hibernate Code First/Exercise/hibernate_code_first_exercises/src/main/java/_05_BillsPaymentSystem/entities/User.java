package _05_BillsPaymentSystem.entities;

import javax.persistence.*;

@Entity
@Table(name = "_05_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "billing_detail_id", referencedColumnName = "id")
    private BillingDetail billingDetail;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, BillingDetail billingDetail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.billingDetail = billingDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BillingDetail getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
    }
}
