package guru.drako.examples.gildedrose

private val LEGENDARY_ITEMS = setOf("Sulfuras, Hand of Ragnaros")

private fun Item.isLegendary(): Boolean = name in LEGENDARY_ITEMS

private fun Item.isBackstagePass(): Boolean = name.startsWith(prefix = "backstage passes", ignoreCase = true)

private fun Item.isAged(): Boolean = name.startsWith(prefix = "aged", ignoreCase = true)

private fun Item.hasIncreasingValue(): Boolean = isBackstagePass() || isAged()

private fun Item.isConjured(): Boolean = name.startsWith(prefix = "conjured", ignoreCase = true)

private fun Item.baseQualityModifier(): Int = if (hasIncreasingValue()) 1 else -1

private inline fun <T> Sequence<T>.forEachWith(action: T.() -> Unit) = forEach { it.action() }

private fun Item.qualityFactor(): Int = when {
  isBackstagePass() -> when {
    sellIn <= 0 -> -quality
    sellIn <= 5 -> 3
    sellIn <= 10 -> 2
    else -> 1
  }
  isConjured() -> if (sellIn <= 0) 4 else 2
  else -> if (sellIn <= 0) 2 else 1
}

class Shop(val items: List<Item>) {
  fun updateQuality() = items.asSequence()
    .filterNot(Item::isLegendary)
    .forEachWith {
      quality = (quality + baseQualityModifier() * qualityFactor()).coerceIn(0, 50)
      --sellIn
    }
}
