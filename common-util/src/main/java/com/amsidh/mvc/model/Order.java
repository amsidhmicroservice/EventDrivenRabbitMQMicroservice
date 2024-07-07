package com.amsidh.mvc.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Order implements Serializable {

    private Integer id;
    private List<String> items;
    private Boolean isStockConfirmed;
    private Boolean isEmailConfirmed;

}
