package com.athena.model.security;

import com.athena.model.borrow.Borrow;
import com.athena.model.listener.UserListener;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2017/3/20.
 */
@Entity
@EntityListeners({
        UserListener.class
})
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@ApiModel(value = "User", description = "user info")
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String wechatId;
    private String email;
    private List<String> identity;
    private String phoneNumber;
    private List<Borrow> borrows;
    private List<BlockRecord> blockRecords;

    public User() {
    }

    public User(NewUserVo newUser) {
        this.username = newUser.getUsername();
        this.password = newUser.getPassword();
        this.email = newUser.getEmail();
        this.wechatId = newUser.getWechatId();
        this.identity = newUser.getIdentity();
        this.phoneNumber = newUser.getPhoneNumber();
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 64)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "wechat_id", nullable = false, length = 64)
    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_identity", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "user_identity"))
    public List<String> getIdentity() {
        return identity;
    }

    @JsonSetter
    public void setIdentity(List<String> identity) {
        this.identity = identity;
    }

    @Transient
    public void setIdentity(String identity) {
        List<String> identities = new ArrayList<>(1);
        identities.add(identity);
        this.identity = identities;
    }

    @Basic
    @Column(name = "phone_number", nullable = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @OneToMany
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? !id.equals(user.id) : user.id != null;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @OneToMany(mappedBy = "blockedUser")
    @JsonIgnore
    public List<BlockRecord> getBlockRecords() {
        return blockRecords;
    }

    public void setBlockRecords(List<BlockRecord> blockRecords) {
        this.blockRecords = blockRecords;
    }

    @Transient
    @JsonGetter("isBlocked")
    public Boolean isBlocked() {
        return this.blockRecords != null && this.blockRecords.stream().anyMatch(BlockRecord::getEnabled);
    }
}

