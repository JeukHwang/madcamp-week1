package com.madcamp.week1.gallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GridItem(private val url: String, val title: String) : Parcelable {
  companion object {
    private const val baseUrl =
        "https://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/"

    private val dataset =
        arrayListOf<GridItem>(
            GridItem("flying_in_the_light.jpg", "Flying in the Light"),
            GridItem("caterpillar.jpg", "Caterpillar"),
            GridItem("look_me_in_the_eye.jpg", "Look Me in the Eye"),
            GridItem("flamingo.jpg", "Flamingo"),
            GridItem("rainbow.jpg", "Rainbow"),
            GridItem("over_there.jpg", "Over there"),
            GridItem("jelly_fish_2.jpg", "Jelly Fish 2"),
            GridItem("flying_in_the_light.jpg", "Flying in the Light"),
            GridItem("caterpillar.jpg", "Caterpillar"),
            GridItem("look_me_in_the_eye.jpg", "Look Me in the Eye"),
            GridItem("flamingo.jpg", "Flamingo"),
            GridItem("rainbow.jpg", "Rainbow"),
            GridItem("over_there.jpg", "Over there"),
            GridItem("jelly_fish_2.jpg", "Jelly Fish 2"),
            GridItem("flying_in_the_light.jpg", "Flying in the Light"),
            GridItem("caterpillar.jpg", "Caterpillar"),
            GridItem("look_me_in_the_eye.jpg", "Look Me in the Eye"),
            GridItem("flamingo.jpg", "Flamingo"),
            GridItem("rainbow.jpg", "Rainbow"),
            GridItem("over_there.jpg", "Over there"),
            GridItem("jelly_fish_2.jpg", "Jelly Fish 2"),
            GridItem("flying_in_the_light.jpg", "Flying in the Light"),
            GridItem("caterpillar.jpg", "Caterpillar"),
            GridItem("look_me_in_the_eye.jpg", "Look Me in the Eye"),
            GridItem("flamingo.jpg", "Flamingo"),
            GridItem("rainbow.jpg", "Rainbow"),
            GridItem("over_there.jpg", "Over there"),
            GridItem("jelly_fish_2.jpg", "Jelly Fish 2"),
            GridItem("lone_pine_sunset.jpg", "Lone Pine Sunset"))

    fun getData(position: Int): GridItem {
      return dataset[position]
    }

    val size: Int
      get() = dataset.size
  }

  val photoUrl: String
    get() = baseUrl + url
}
