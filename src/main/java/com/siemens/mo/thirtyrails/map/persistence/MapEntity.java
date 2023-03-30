package com.siemens.mo.thirtyrails.map.persistence;

import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.player.persistence.PlayerEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity(name = "map")
@NoArgsConstructor
@RepositoryRestResource
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(mappedBy = "map", fetch = LAZY)
    private PlayerEntity player;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "map")
    private List<MapItemEntity> mapItems;
}
