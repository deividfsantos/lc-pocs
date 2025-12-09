package com.dsantos;

import com.dsantos.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();

        Guitar guitar1 = GuitarFactory.createGuitar(Builder.FENDER, Model.SG, Wood.ROSEWOOD, Wood.MAHOGANY, 6, BigDecimal.valueOf(16020.23));
        Guitar guitar2 = GuitarFactory.createGuitar(Builder.PRS, Model.LES_PAUL, Wood.ROSEWOOD, Wood.ROSEWOOD, 6, BigDecimal.valueOf(16020.23));
        Guitar guitar3 = GuitarFactory.createGuitar(Builder.GIBSON, Model.SG, Wood.SITKA, Wood.MAHOGANY, 6, BigDecimal.valueOf(16020.23));
        Guitar guitar4 = GuitarFactory.createGuitar(Builder.MARTIN, Model.STRATOCASTER, Wood.ROSEWOOD, Wood.MAHOGANY, 6, BigDecimal.valueOf(16020.23));

        inventory.addGuitar(guitar1);
        inventory.addGuitar(guitar2);
        inventory.addGuitar(guitar3);
        inventory.addGuitar(guitar4);
    }

    @Test
    public void testAddAndGetAllGuitars() {
        List<Guitar> all = inventory.getAllGuitars();
        assertNotNull(all, "getAllGuitars should not return null");
        assertEquals(4, all.size(), "There should be 4 guitars in the inventory");
    }

    @Test
    public void testSearchByModel() {
        GuitarSpec searchSpec = new GuitarSpec(null, Model.SG, null, null, 6);
        List<Guitar> results = inventory.search(searchSpec);

        assertNotNull(results);
        assertEquals(2, results.size(), "Search for Model.SG should return 2 guitars");
    }

    @Test
    public void testSearchByModelAndStrings() {
        GuitarSpec searchSpec = new GuitarSpec(null, Model.SG, null, null, 6);
        List<Guitar> results = inventory.search(searchSpec);

        assertNotNull(results);
        assertEquals(2, results.size(), "Search for Model.SG and 6 strings should return 2 guitars");
        for (Guitar g : results) {
            assertEquals(Model.SG, g.spec().model());
            assertEquals(6, g.spec().numStrings());
        }
    }

    @Test
    public void testSearchByBuilder() {
        GuitarSpec searchSpec = new GuitarSpec(Builder.FENDER, null, null, null, 6);
        List<Guitar> results = inventory.search(searchSpec);

        assertNotNull(results);
        assertEquals(1, results.size(), "Search for Builder.FENDER should return 1 guitar");
        assertEquals(Builder.FENDER, results.getFirst().spec().builder());
    }

    @Test
    public void testSearchNoMatch() {
        GuitarSpec searchSpec = new GuitarSpec(null, Model.LES_PAUL, null, null, 7);
        List<Guitar> results = inventory.search(searchSpec);
        assertNotNull(results);
        assertTrue(results.isEmpty(), "Search with unmatched spec should return empty list");
    }
}
