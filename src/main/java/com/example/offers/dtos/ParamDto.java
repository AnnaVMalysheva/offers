package com.example.offers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class ParamDto {
    @XmlValue
    private String value;
    @XmlAttribute(name = "name")
    private String name;
}
