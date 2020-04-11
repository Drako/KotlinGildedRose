package guru.drako.examples.gildedrose

class Shop(val items: List<Item>) {
  companion object {
    private val LEGENDARY_ITEM_NAMES = listOf(
      "Sulfuras, Hand of Ragnaros"
    )
  }

  private fun Item.updateSellIn() {
    if (name !in LEGENDARY_ITEM_NAMES) {
      --sellIn
    }
  }

  fun updateQuality() {
    for (item in items) {
      if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
        if (item.quality > 0) {
          if (item.name != "Sulfuras, Hand of Ragnaros") {
            --item.quality
          }
        }
      } else {
        if (item.quality < 50) {
          ++item.quality

          if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
            if (item.sellIn < 11) {
              if (item.quality < 50) {
                ++item.quality
              }
            }

            if (item.sellIn < 6) {
              if (item.quality < 50) {
                ++item.quality
              }
            }
          }
        }
      }

      item.updateSellIn()

      if (item.sellIn < 0) {
        if (item.name != "Aged Brie") {
          if (item.name != "Backstage passes to a TAFKAL80ETC concert") {
            if (item.quality > 0) {
              if (item.name != "Sulfuras, Hand of Ragnaros") {
                --item.quality
              }
            }
          } else {
            item.quality -= item.quality
          }
        } else {
          if (item.quality < 50) {
            ++item.quality
          }
        }
      }
    }
  }
}
