package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void testSimpleProductSellInDecrease() {
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Apple", 2, 3))));
        app.updateQuality();
        assertEquals(1, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(0, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(-1, app.items.get(0).sellIn);
    }

    @Test
    void testSimpleProductQualityDecrease() {
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Apple", 3, 3))));
        app.updateQuality();
        assertEquals(2, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(1, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(0, app.items.get(0).quality);
    }

    @Test
    void testQualityNeverNegative() {
        GildedRose app = new GildedRose(new ArrayList<>(Arrays.asList(
                new Item("Apple", -1, 0),
                new Item("Backstage passes to a TAFKAL80ETC concert", -1, 0),
                new Item("Aged Brie", 2, 1))));
        
        app.updateQuality();
        assertEquals(0, app.items.get(0).quality);
        assertEquals(0, app.items.get(1).quality);
        app.updateQuality();
        assertEquals(0, app.items.get(0).quality);
        assertEquals(0, app.items.get(1).quality);
    }


    @Test
    void testSimpleProductQualityDecrease2TimesFater(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Apple", 1, 10))));
        app.updateQuality();
        assertEquals(9, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(7, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(5, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(3, app.items.get(0).quality);
    }

    @Test
    void testAgedBrieQualityIncreaseBeforeSellinExpiration(){
        GildedRose app = new GildedRose(new ArrayList<>(Arrays.asList(new Item("Aged Brie", 10, 10))));
        app.updateQuality();
        assertEquals(11, app.items.get(0).quality);
        assertEquals(9, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(12, app.items.get(0).quality);
        assertEquals(8, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(13, app.items.get(0).quality);
        assertEquals(7, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(14, app.items.get(0).quality);
        assertEquals(6, app.items.get(0).sellIn);
    }

    @Test
    void testAgedBrieQualityIncreaseAfterSellinExpiration(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Aged Brie", 0, 9))));
        app.updateQuality();
        assertEquals(11, app.items.get(0).quality);
        assertEquals(-1, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(13, app.items.get(0).quality);
        assertEquals(-2, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(15, app.items.get(0).quality);
        assertEquals(-3, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(17, app.items.get(0).quality);
        assertEquals(-4, app.items.get(0).sellIn);
    }

    @Test
    void testSulfurasQualityUnchange(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Sulfuras, Hand of Ragnaros", 0, 80))));
        assertEquals(80, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(80, app.items.get(0).quality);
    }

    @Test
    void testBackstageQualityIncreaseEveryDay(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Backstage passes to a TAFKAL80ETC concert", 20, 10))));
        app.updateQuality();
        assertEquals(11, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(12, app.items.get(0).quality);
    }

    @Test
    void testBackstageQualityIncrease10daysBeforeSellIn(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10))));
        app.updateQuality();
        assertEquals(12, app.items.get(0).quality);
        assertEquals(9, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(14, app.items.get(0).quality);
        assertEquals(8, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(16, app.items.get(0).quality);
        assertEquals(7, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(18, app.items.get(0).quality);
        assertEquals(6, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(20, app.items.get(0).quality);
        assertEquals(5, app.items.get(0).sellIn);
    }

    @Test
    void testBackstageQualityIncrease5daysBeforeSellIn(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10))));
        app.updateQuality();
        assertEquals(13, app.items.get(0).quality);
        assertEquals(4, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(16, app.items.get(0).quality);
        assertEquals(3, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(19, app.items.get(0).quality);
        assertEquals(2, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(22, app.items.get(0).quality);
        assertEquals(1, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(25, app.items.get(0).quality);
        assertEquals(0, app.items.get(0).sellIn);
    }

    @Test
    void testBackstageQualityDropAfterConcert() {
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Backstage passes to a TAFKAL80ETC concert", 2, 50))));
        app.updateQuality();
        assertEquals(50, app.items.get(0).quality);
        assertEquals(1, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(50, app.items.get(0).quality);
        assertEquals(0, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(0, app.items.get(0).quality);
        assertEquals(-1, app.items.get(0).sellIn);
        app.updateQuality();
        assertEquals(0, app.items.get(0).quality);
        assertEquals(-2, app.items.get(0).sellIn);
    }


    @Test
    void testQualityNeverIncreaseOver50(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Aged Brie", 0, 49))));
        app.updateQuality();
        assertEquals(50, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(50, app.items.get(0).quality);
    }

    @Test
    void testConjuredDecreaseBy2(){
        GildedRose app = new GildedRose(new ArrayList<>(Collections.singletonList(new Item("Conjured stuff", 3, 43))));
        app.updateQuality();
        assertEquals(41, app.items.get(0).quality);
        app.updateQuality();
        assertEquals(39, app.items.get(0).quality);
    }
}
