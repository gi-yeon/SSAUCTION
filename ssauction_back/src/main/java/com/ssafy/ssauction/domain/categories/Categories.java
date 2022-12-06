package com.ssafy.ssauction.domain.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctgr_no")
    private Long ctgrNo;

    @Column(name = "ctgr_name")
    private String ctgrName;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_no")
    private Items item;

    @Builder
    public Categories(String ctgrName, Items item) {
        this.ctgrName = ctgrName;
        this.item = item;
    }
}
