package com.example.concertmanager.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @Column(name = "regular_price")
    private Double regularPrice;

    @Column(name = "student_price")
    private Double studentPrice;

    @Column(name = "vip_price")
    private Double vipPrice;

    public Event() {}

    public Event(String title, String description, Venue venue,
                 Category category, LocalDateTime startDate,
                 LocalDateTime endDate, Organizer organizer,
                 Double regularPrice, Double studentPrice, Double vipPrice) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.regularPrice = regularPrice;
        this.studentPrice = studentPrice;
        this.vipPrice = vipPrice;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
    public Double getRegularPrice() {
        return regularPrice;
    }
    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }
    public Double getStudentPrice() {
        return studentPrice;
    }
    public void setStudentPrice(Double studentPrice) {
        this.studentPrice = studentPrice;
    }
    public Double getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }
}
