package guru.drako.examples.gildedrose

import kotlin.math.max
import kotlin.math.min

class Shop(val items: List<Item>) {
  companion object {
    private val LEGENDARY_ITEMS = setOf(
      "Sulfuras, Hand of Ragnaros"
    )

    private val ITEMS_WITH_INCREASING_VALUE = setOf(
      "Aged Brie",
      "Backstage passes to a TAFKAL80ETC concert"
    )

    private const val MIN_QUALITY = 0
    private const val MAX_QUALITY = 50

    private val Item.baseQualityModifier: Int
      get() = when (name) {
        in ITEMS_WITH_INCREASING_VALUE -> 1
        in LEGENDARY_ITEMS -> 0
        else -> -1
      }

    private val Item.qualityFactor: Int
      get() = when (name) {
        "Backstage passes to a TAFKAL80ETC concert" -> when {
          sellIn <= 0 -> -MAX_QUALITY
          sellIn <= 5 -> 3
          sellIn <= 10 -> 2
          else -> 1
        }
        else -> if (sellIn <= 0) 2 else 1
      }

    private inline fun Item.updateQuality(newValueGenerator: () -> Int) {
      if (name !in LEGENDARY_ITEMS) {
        quality = min(MAX_QUALITY, max(MIN_QUALITY, newValueGenerator()))
      }
    }

    private fun Item.updateSellIn() {
      if (name !in LEGENDARY_ITEMS) {
        --sellIn
      }
    }
  }

  fun updateQuality() {
    for (item in items) {
      val baseModifier = item.baseQualityModifier
      val factor = item.qualityFactor
      item.updateQuality { item.quality + baseModifier * factor }
      item.updateSellIn()
    }
  }
}
