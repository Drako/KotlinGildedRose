package guru.drako.examples.gildedrose

private const val MIN_QUALITY = 0
private const val MAX_QUALITY = 50

private val LEGENDARY_ITEMS = setOf("Sulfuras, Hand of Ragnaros")

private fun Item.isLegendary(): Boolean = name in LEGENDARY_ITEMS

private fun Item.isBackstagePass(): Boolean = name.startsWith(prefix = "backstage passes", ignoreCase = true)

private fun Item.isAged(): Boolean = name.startsWith(prefix = "aged", ignoreCase = true)

private fun Item.isConjured(): Boolean = name.startsWith(prefix = "conjured", ignoreCase = true)

private inline fun <T> Sequence<T>.forEachWith(action: T.() -> Unit) = forEach { it.action() }

private fun Item.qualityModifier(): Int = when {
  isBackstagePass() -> when {
    sellIn <= 0 -> -MAX_QUALITY
    sellIn <= 5 -> 3
    sellIn <= 10 -> 2
    else -> 1
  }
  isAged() -> if (sellIn <= 0) 2 else 1
  isConjured() -> if (sellIn <= 0) -4 else -2
  else -> if (sellIn <= 0) -2 else -1
}

class Shop(val items: List<Item>) {
  fun updateQuality() = items.asSequence()
    .filterNot(Item::isLegendary)
    .forEachWith {
      quality = (quality + qualityModifier()).coerceIn(MIN_QUALITY, MAX_QUALITY)
      --sellIn
    }
}
