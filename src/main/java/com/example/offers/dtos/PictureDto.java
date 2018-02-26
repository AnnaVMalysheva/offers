package com.example.offers.dtos;

import lombok.*;

import javax.xml.bind.annotation.*;

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
