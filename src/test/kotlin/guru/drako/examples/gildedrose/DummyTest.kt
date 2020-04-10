package guru.drako.examples.gildedrose

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import kotlin.test.Test
import kotlin.test.assertTrue

@TestInstance(Lifecycle.PER_CLASS)
class DummyTest {
  @Test
  fun `test the truth`() {
    assertTrue(actual = true)
  }
}
