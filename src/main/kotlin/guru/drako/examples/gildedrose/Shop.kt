package guru.drako.examples.gildedrose

import kotlin.math.max
import kotlin.math.min

class Shop(val items: List<Item>) {
  companion object {
    private val LEGENDARY_ITEM_NAMES = listOf(
      "Sulfuras, Hand of Ragnaros"
    )

    private const val MIN_QUALITY = 0
    private const val MAX_QUALITY = 50

    private val Item.baseQualityModifier: Int
      get() = when (name) {
        "Aged Brie", "Backstage passes to a TAFKAL80ETC concert" -> 1
        in LEGENDARY_ITEM_NAMES -> 0
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

    private fun Item.updateQuality(newValue: Int) {
      if (name !in LEGENDARY_ITEM_NAMES) {
        quality = min(MAX_QUALITY, max(MIN_QUALITY, newValue))
      }
    }

    private fun Item.updateSellIn() {
      if (name !in LEGENDARY_ITEM_NAMES) {
        --sellIn
      }
    }
  }

  fun updateQuality() {
    for (item in items) {
      val baseModifier = item.baseQualityModifier
      val factor = item.qualityFactor
      item.updateQuality(newValue = item.quality + baseModifier * factor)
      item.updateSellIn()
    }
  }
}
