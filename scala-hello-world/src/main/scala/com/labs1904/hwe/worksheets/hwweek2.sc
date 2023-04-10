val states = Map(
  "IL" -> "Michael Jordan Statue",
  "OH" -> "Ohio State Fair",
  "IN" -> "Connor's Prairie",
  "MI" -> "Ford Museum",
  "MO" -> "The Arch",
  "AR" -> "Downtown Little Rock",
)

val places = List(
  "IL", "OH", "IN", "MI", "MO", "AR"
)

val specificPlaces = places.map(state => {states.getOrElse(state, "Unknown")})

specificPlaces