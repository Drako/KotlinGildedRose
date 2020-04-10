package guru.drako.examples.gildedrose

data class Item(
  val name: String,
  var sellIn: Int,
  var quality: Int
) {
  override fun toString() = """{ name: "$name", sellIn: $sellIn, quality: $quality }"""
}
