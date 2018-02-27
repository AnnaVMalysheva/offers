package com.example.offers.dtos;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureDto {
    @XmlValue
    private String picture;
}
