package com.nhn.jhipster.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PokerProfile.
 */
@Entity
@Table(name = "poker_profile")
public class PokerProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 30)
    @Column(name = "ongame_id", length = 30, nullable = false, unique = true)
    private String ongameId;

    @NotNull
    @Size(min = 4, max = 30)
    @Column(name = "nick_name", length = 30, nullable = false, unique = true)
    private String nickName;

    @NotNull
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    @NotNull
    @Column(name = "last_date", nullable = false)
    private LocalDate lastDate;

    @Lob
    @Column(name = "photo_path")
    private byte[] photoPath;

    @Column(name = "photo_path_content_type")
    private String photoPathContentType;

    @NotNull
    @Size(min = 4, max = 30)
    @Column(name = "ip", length = 30, nullable = false)
    private String ip;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOngameId() {
        return ongameId;
    }

    public PokerProfile ongameId(String ongameId) {
        this.ongameId = ongameId;
        return this;
    }

    public void setOngameId(String ongameId) {
        this.ongameId = ongameId;
    }

    public String getNickName() {
        return nickName;
    }

    public PokerProfile nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public PokerProfile regDate(LocalDate regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public PokerProfile lastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
        return this;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public byte[] getPhotoPath() {
        return photoPath;
    }

    public PokerProfile photoPath(byte[] photoPath) {
        this.photoPath = photoPath;
        return this;
    }

    public void setPhotoPath(byte[] photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoPathContentType() {
        return photoPathContentType;
    }

    public PokerProfile photoPathContentType(String photoPathContentType) {
        this.photoPathContentType = photoPathContentType;
        return this;
    }

    public void setPhotoPathContentType(String photoPathContentType) {
        this.photoPathContentType = photoPathContentType;
    }

    public String getIp() {
        return ip;
    }

    public PokerProfile ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PokerProfile)) {
            return false;
        }
        return id != null && id.equals(((PokerProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PokerProfile{" +
            "id=" + getId() +
            ", ongameId='" + getOngameId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", lastDate='" + getLastDate() + "'" +
            ", photoPath='" + getPhotoPath() + "'" +
            ", photoPathContentType='" + getPhotoPathContentType() + "'" +
            ", ip='" + getIp() + "'" +
            "}";
    }
}
