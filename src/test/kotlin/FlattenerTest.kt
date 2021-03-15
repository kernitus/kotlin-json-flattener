package test.kotlin

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import main.kotlin.flatten

class FlattenerTest : StringSpec({

    "Empty JSON" {
        val emptyJson = JsonObject()
        val outputJson = JsonObject()
        emptyJson.flatten(outputJson)
        outputJson shouldBe emptyJson
    }

    "Sample JSON" {
        val example = json {
            obj(
                "a" to 1,
                "b" to true,
                "c" to obj(
                    "d" to 3,
                    "e" to "test"
                )
            )
        }
        val expected = json {
            obj(
                "a" to 1,
                "b" to true,
                "c.d" to 3,
                "c.e" to "test"
            )
        }
        val output = JsonObject()
        example.flatten(output)
        output shouldBe expected
    }

    "Flat JSON" {
        val flattened = json {
            obj(
                "a" to 1,
                "b" to true,
                "c.d" to 3,
                "c.e" to "test"
            )
        }
        val output = JsonObject()
        flattened.flatten(output)
        output shouldBe flattened
    }

    "Duplicate keys JSON, second key overrides first" {
        val duplicate = json {
            obj(
                "a" to 1,
                "a" to 4,
                "b" to true,
                "c" to obj(
                    "d" to 3,
                    "e" to "test"
                )
            )
        }
        val expected = json {
            obj(
                "a" to 4,
                "b" to true,
                "c.d" to 3,
                "c.e" to "test"
            )
        }
        val output = JsonObject()
        duplicate.flatten(output)
        output shouldBe expected
    }

    "Deep JSON" {
        val deep = json {
            obj(
                "a" to 3,
                "b" to obj(
                    "f" to "hey",
                    "a" to obj(
                        "a" to 1,
                        "b" to true
                    )
                )
            )
        }
        val expected = json {
            obj(
                "a" to 3,
                "b.f" to "hey",
                "b.a.a" to 1,
                "b.a.b" to true
            )
        }
        val output = JsonObject()
        deep.flatten(output)
        output shouldBe expected
    }

    "Deep and nested JSON" {
        val deep = json {
            obj(
                "menu" to obj(
                    "revision" to 1.5,
                    "starters" to obj(
                        "sausage_roll" to obj(
                            "price" to 2.50,
                            "available" to 5
                        ),
                        "duck_salad" to obj(
                            "price" to 8.75,
                            "available" to 2
                        ),
                        "smoked_salmon" to obj(
                            "price" to 5.55,
                            "available" to 10
                        ),
                        "crab_cakes" to obj(
                            "price" to 7.00,
                            "available" to 3
                        )
                    ),
                    "desserts" to obj(
                        "treacle_tart" to obj(
                            "price" to 8.50,
                            "available" to 3
                        ),
                        "victoria_cake" to obj(
                            "price" to 7.25,
                            "available" to 5
                        ),
                        "panna_cotta" to obj(
                            "price" to 6.50,
                            "available" to 9
                        ),
                        "raspberry_brulee" to obj(
                            "price" to 6.75,
                            "available" to 4
                        ),
                        "cheesecake" to obj(
                            "price" to 5.00,
                            "available" to 7
                        )
                    )
                )
            )
        }
        val expected = json {
            obj(
                "menu.revision" to 1.5,
                "menu.starters.sausage_roll.price" to 2.50,
                "menu.starters.sausage_roll.available" to 5,
                "menu.starters.duck_salad.price" to 8.75,
                "menu.starters.duck_salad.available" to 2,
                "menu.starters.smoked_salmon.price" to 5.55,
                "menu.starters.smoked_salmon.available" to 10,
                "menu.starters.crab_cakes.price" to 7.00,
                "menu.starters.crab_cakes.available" to 3,
                "menu.desserts.treacle_tart.price" to 8.50,
                "menu.desserts.treacle_tart.available" to 3,
                "menu.desserts.victoria_cake.price" to 7.25,
                "menu.desserts.victoria_cake.available" to 5,
                "menu.desserts.panna_cotta.price" to 6.50,
                "menu.desserts.panna_cotta.available" to 9,
                "menu.desserts.raspberry_brulee.price" to 6.75,
                "menu.desserts.raspberry_brulee.available" to 4,
                "menu.desserts.cheesecake.price" to 5.00,
                "menu.desserts.cheesecake.available" to 7
            )
        }
        val output = JsonObject()
        deep.flatten(output)
        output shouldBe expected

    }
})
