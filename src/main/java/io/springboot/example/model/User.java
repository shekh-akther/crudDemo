package io.springboot.example.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author shekh akther
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{field.null}")
    private String name;

    @NotNull(message = "{field.null}")
    @Min(value = 18, message = "{age.min}")
    @Max(value = 150, message = "{age.max}")
    private Integer age;

}
