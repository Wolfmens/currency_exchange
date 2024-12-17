package ru.skillbox.currency.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "create_sequence", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Comment("Наименование валюты")
    private String name;

    @Column(name = "nominal")
    @Comment("Номинал валюты")
    private Long nominal;

    @Column(name = "value")
    @Comment("Стоимость валюты в рублях")
    private Double value;

    @Column(name = "iso_num_code")
    @Comment("Числовой код ISO")
    private Long isoNumCode;

    @Column(name = "iso_letter_code",length = 50)
    @Comment("Буквенный код ISO")
    private String isoLetterCode;

}
