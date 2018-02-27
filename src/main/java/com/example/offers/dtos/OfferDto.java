package com.example.offers.dtos;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@XmlRootElement(name="offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferDto {
    @XmlAttribute(name = "id")
    private String id;

    private String name;

    private String currencyId;

    private String description;

    private Double price;

    private Integer categoryId;

    @XmlAttribute(name = "group_id")
    private String groupId;

    @XmlElement(name = "picture")
    private List<PictureDto> pictures = new ArrayList<>();
    @XmlElement(name = "param")
    private List<ParamDto> params = new ArrayList<>();
}
