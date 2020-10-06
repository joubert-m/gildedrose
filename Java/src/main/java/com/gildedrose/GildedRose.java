package com.gildedrose;

import java.util.*;

class GildedRose {

    public final static String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public final static String AGED_BRIE = "Aged Brie";
    public final static String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    public final static String CONJURED_PREFIX = "Conjured";
    public final static Integer LEGENDARY_QUALITY = 80;
    public final static Integer MAX_QUALITY = 50;
    public final static Integer INCREASE_QUALITY_3 = 3;
    public final static Integer INCREASE_QUALITY_2 = 2;
    public final static Integer INCREASE_QUALITY_1 = 1;
    public final static Integer TEN_DAYS_BEFORE_BACKSTAGE = 10;
    public final static Integer FIVE_DAYS_BEFORE_BACKSTAGE = 5;

    public interface Rule {
        void apply(Item item);
    }

    Rule applyCommon = item -> {
        int dec = item.sellIn <= 0 ? INCREASE_QUALITY_2 : INCREASE_QUALITY_1;
        item.quality = item.quality > 0 ? item.quality - dec : 0;
        item.quality = Math.min(item.quality, MAX_QUALITY);
        item.sellIn--;
    };

    Rule applyAgedBrie = item -> {
        int dec = item.sellIn <= 0 ? INCREASE_QUALITY_2 : INCREASE_QUALITY_1;
        item.quality += dec;
        item.quality = Math.min(item.quality, MAX_QUALITY);
        item.sellIn--;
    };

    Rule applyBackstage = item -> {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else if (item.sellIn <= TEN_DAYS_BEFORE_BACKSTAGE) {
            item.quality = item.sellIn <= FIVE_DAYS_BEFORE_BACKSTAGE ? item.quality + INCREASE_QUALITY_3 : item.quality + INCREASE_QUALITY_2;
        } else {
            item.quality++;
        }
        item.quality = Math.min(item.quality, MAX_QUALITY);
        item.sellIn--;
    };

    Rule applyLegendary = item -> {
        item.quality = LEGENDARY_QUALITY;
        item.sellIn--;
    };

    Rule applyConjured = item -> {
        item.quality -= INCREASE_QUALITY_2;
        item.sellIn--;
    };

    ArrayList<Item> items;
    HashMap<String, Rule> rules = new HashMap<String, Rule>(){{
        put(AGED_BRIE, applyAgedBrie);
        put(SULFURAS, applyLegendary);
        put(BACKSTAGE, applyBackstage);
    }};

    public GildedRose(ArrayList<Item> items) {
        this.items = items;
    }

    public void updateQuality() {
        for(Item item: items){
            if(this.rules.containsKey(item.name)) {
                this.rules.get(item.name).apply(item);
            }
            else if(item.name.startsWith("Conjured")){
                applyConjured.apply(item);
            }
            else {
                applyCommon.apply(item);
            }

        }
    }

}