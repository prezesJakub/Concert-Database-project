package com.example.concertmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VenueRequestDto {
    private String name;
    private String description;
    private String cityName;
    private String countryName;
    private List<SectorDto> sectors;

    @Getter
    @Setter
    public static class SectorDto {
        private String name;
        private int rowCount;
        private int seatsPerRow;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getRowCount() {
            return rowCount;
        }
        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }
        public int getSeatsPerRow() {
            return seatsPerRow;
        }
        public void setSeatsPerRow(int seatsPerRow) {
            this.seatsPerRow = seatsPerRow;
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public List<SectorDto> getSectors() {
        return sectors;
    }
}
