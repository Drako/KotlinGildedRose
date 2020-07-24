package guru.drako.examples.gildedrose

import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopFuzzTest {
  companion object {
    private const val ITERATIONS = 100
  }

  private val nonLegendaryItemNames = listOf(
    "+5 Dexterity Vest", // regular item
    "Aged Brie", // gets better with age
    "Backstage passes to a TAFKAL80ETC concert", // gets better, but drops to 0 after sellIn
    "dummy" // some non existing and thus regular item
  )

  private val legendaryItemNames = listOf(
    "Sulfuras, Hand of Ragnaros"
  )

  private fun Item.update(times: Int) = copy().also { item ->
    with(Shop(items = listOf(item))) {
      repeat(times) {
        updateQuality()
      }
    }
  }

  @TestFactory
  fun `updateQualities on legendary goods should not break constraints`() = iterator {
    repeat(times = ITERATIONS) { iteration ->
      val numberOfDays = Random.nextInt(from = 1, until = 101)
      val sellIn = Random.nextInt(from = 1, until = 31) // in production this is actually always 0
      val quality = Random.nextInt(from = 1, until = 51) // in production this is actually always 80
      val name = legendaryItemNames.random()

      val displayName =
        "#${iteration + 1}: numberOfDays=$numberOfDays, name=\"$name\", sellIn=$sellIn, quality=$quality"

      yield(
        dynamicTest(displayName) {
          val item = Item(name = name, sellIn = sellIn, quality = quality)
            .update(times = numberOfDays)

          // quality and sellIn stay unchanged for legendary items
          assertEquals(
            actual = item.quality,
            expected = quality
          )
          assertEquals(
            actual = item.sellIn,
            expected = sellIn
          )
        }
      )
    }
  }

  @TestFactory
  fun `updateQualities on non-legendary goods should not break constraints`() = iterator {
    repeat(times = ITERATIONS) { iteration ->
      val numberOfDays = Random.nextInt(from = 1, until = 101)
      val sellIn = Random.nextInt(from = 1, until = 31)
      val quality = Random.nextInt(from = 1, until = 51)
      val name = nonLegendaryItemNames.random()

      val displayName =
        "#${iteration + 1}: numberOfDays=$numberOfDays, name=\"$name\", sellIn=$sellIn, quality=$quality"

      yield(
        dynamicTest(displayName) {
          val item = Item(name = name, sellIn = sellIn, quality = quality)
            .update(times = numberOfDays)

          assertTrue(
            actual = item.quality in (0..50),
            message = "quality must be in the range 0 to 50, but was ${item.quality}"
          )
          assertEquals(
            actual = item.sellIn,
            expected = sellIn - numberOfDays
          )
        }
      )
    }
  }
}
