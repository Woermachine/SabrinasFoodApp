package com.stephenwoerner.sabrinafoodapp.data

import com.stephenwoerner.sabrinafoodapp.data.Tag.*


data class LatLng(val latitude: Double, val longitude: Double)


enum class Restaurant(
    val title: String,
    val tags: List<Tag>,
    val location: LatLng
) {
    LakeHouse(
        title = "Lake House Restaurant",
        tags = listOf(Burger, Italian, Salad, Wine, Beer, Brunch, Dessert),
        location = LatLng(39.678062, -79.858436),
    ),
    SuperRedBowl(
        title = "Super Red Bowls",
        tags = listOf(Japanese, Sushi, Asian, Delivery),
        location = LatLng(39.648386, -79.890896),
    ),
    LosMariachis(
        title = "Los Mariachis",
        tags = listOf(Mexican, Beer, Delivery),
        location = LatLng(39.640844, -79.995884),
    ),
    Wendys(
        title = "Wendys",
        tags = listOf(American, FastFood, Burger, IceCream, Dessert, Delivery),
        location = LatLng(39.648111, -79.898257),
    ),
    Wendys2(
        title = "Wendys",
        tags = listOf(American, FastFood, Burger, IceCream, Dessert, Delivery),
        location = LatLng(39.651668, -79.970205),
    ),
    Wendys3(
        title = "Wendys",
        tags = listOf(American, FastFood, Burger, IceCream, Dessert, Delivery),
        location = LatLng(39.641313, -79.999054),
    ),
    Wendys4(
        title = "Wendys",
        tags = listOf(American, FastFood, Burger, IceCream, Dessert, Delivery),
        location = LatLng(39.627133, -79.928933),
    ),
    IHOP(
        title = "IHOP",
        tags = listOf(American, Salad, Burger, Breakfast, Dessert, Delivery),
        location = LatLng(39.649514, -79.900317),
    ),
    TwoGBrothersPizza(
        title = "2G Brothers Pizza",
        tags = listOf(Pizza, Wings, Salad, Dessert, Delivery),
        location = LatLng(39.674928, -79.851089),
    ),
    AlmostHeaven(
        title = "Almost Heaven Desserts & Coffee",
        tags = listOf(Coffee, Salad, Dessert, Delivery, Delivery),
        location = LatLng(39.674928, -79.851089),
    ),
    BurgerKing(
        title = "Burger King",
        tags = listOf(American, FastFood, Burger, Delivery),
        location = LatLng(39.670765, -79.852453),
    ),
    Tropics(
        title = "Tropics",
        tags = listOf(Bar, Wine, Beer, Burger, Salad, Brunch, Dessert, Delivery),
        location = LatLng(39.671080, -79.855992),
    ),
    WalzzyHotdogs(
        title = "Walzzy's Hotdogs",
        tags = listOf(American, Burger, IceCream, Dessert, Delivery),
        location = LatLng(39.672723, -79.855294),
    ),
    Dockside(
        title = "Dockside",
        tags = listOf(American, Seafood, Burger, Brunch, IceCream, Dessert),
        location = LatLng(39.660819, -79.851011),
    ),
    AppleAnnies(
        title = "Apple Annie's",
        tags = listOf(American, Salad, IceCream, Dessert),
        location = LatLng(39.647980, -79.891054),
    ),
    Draft(
        title = "Draft",
        tags = listOf(American, Burger, Brunch, Bar, Dessert),
        location = LatLng(39.648433, -79.891553),
    ),
    OutbackSteakHouse(
        title = "Outback Steak House",
        tags = listOf(American, Beer, Wings, Steak, Burger, Dessert, Delivery),
        location = LatLng(39.648433, -79.891553),
    ),
    Fujiyama(
        title = "Fujiyama",
        tags = listOf(Japanese, Sushi, Beer, Wings, Hibachi, Delivery),
        location = LatLng(39.649680, -79.897005),
    ),
    Mcdonalds(
        title = "Mcdonalds",
        tags = listOf(American, FastFood, Burger, IceCream, Dessert, Delivery),
        location = LatLng(39.649697, -79.900520),
    ),
    TacoBell(
        title = "Taco Bell",
        tags = listOf(Mexican, FastFood, Delivery),
        location = LatLng(39.647837, -79.901362),
    ),
    TacoBell2(
        title = "Taco Bell",
        tags = listOf(Mexican, FastFood, Delivery),
        location = LatLng(39.651207, -79.969799),
    ),
    TacoBell3(
        title = "Taco Bell",
        tags = listOf(Mexican, FastFood, Delivery),
        location = LatLng(39.630137, -79.985019),
    ),
    TipsyTeeze(
        title = "Tipsy Teeze",
        tags = listOf(American, Bar, Seafood, Beer, Delivery),
        location = LatLng(39.648131, -79.902051),
    ),
    ChinaOne(
        title = "China One",
        tags = listOf(Asian, Chinese),
        location = LatLng(39.651183, -79.921354),
    ),
    PrimantiBros(
        title = "Primanti Bros",
        tags = listOf(Burger, Bar, Beer, Dessert, Delivery),
        location = LatLng(39.653151, -79.941393),
    ),
    CrabShackCaribba(
        title = "Crab Shack Caribba",
        tags = listOf(Seafood, Delivery),
        location = LatLng(39.652527, -79.941763),
    ),
    BaconBourbonBeer(
        title = "Bacon Bourbon & Beer",
        tags = listOf(Burger, Wine, Beer),
        location = LatLng(39.652527, -79.941763),
    ),
    NonnoCarlo(
        title = "Nonno Carlo",
        tags = listOf(Italian, Salad, Wine, Pizza, Delivery),
        location = LatLng(39.651901, -79.942306),
    ),
    PiesAndPints(
        title = "Pies & Pints",
        tags = listOf(Pizza, Beer, Salad, Wine, Delivery),
        location = LatLng(39.651901, -79.942306),
    ),
    FiveGuys(
        title = "Five Guys",
        tags = listOf(American, FastFood, Burger, Delivery),
        location = LatLng(39.651901, -79.942306),
    ),
    Stefanos(
        title = "Stefanos",
        tags = listOf(Italian, Salad, Dessert, IceCream, Wine),
        location = LatLng(39.658192, -79.960618),
    ),
    Keglers(
        title = "Keglers",
        tags = listOf(American, Wings, Salad, Beer, Delivery),
        location = LatLng(39.658192, -79.960618),
    ),
    MountainMamas(
        title = "Mountain Mamas",
        tags = listOf(American, Beer, Wings),
        location = LatLng(39.657084, -79.964324),
    ),
    BlackBearBurritos(
        title = "Black Bear Burritos",
        tags = listOf(Mexican, Beer),
        location = LatLng(39.654267, -79.971417),
    ),
    MariosFishbowl(
        title = "Marios Fishbowl",
        tags = listOf(Burger, Sandwich),
        location = LatLng(39.632382, -79.974618),
    ),
    BostonBeanery(
        title = "Boston Beanery",
        tags = listOf(American, Salad, Burger, Beer, Delivery),
        location = LatLng(39.651804, -79.967555),
    ),
    EatNPark(
        title = "Eat N Park",
        tags = listOf(American, Salad, Burger, Buffet, Delivery),
        location = LatLng(39.651354, -79.969187),
    ),
    ChickFilA(
        title = "Chick-Fil-A",
        tags = listOf(American, FastFood, IceCream, Dessert),
        location = LatLng(39.650151, -79.971320),
    ),
    Ogawa(
        title = "Ogawa",
        tags = listOf(Japanese, Sushi, Beer, Wine, Dessert),
        location = LatLng(39.649120, -79.963559),
    ),
    Crockets(
        title = "Crockets",
        tags = listOf(Bar, Beer, Wings, Burger, Wine),
        location = LatLng(39.657489, -79.9829),
    ),
    SteakAndShake(
        title = "Steak and Shake",
        tags = listOf(American, FastFood, Burger, Delivery),
        location = LatLng(39.656313, -79.987980),
    ),
    MountainState(
        title = "Mountain State Brewing",
        tags = listOf(Pizza, Beer, Wings, Wine, Dessert),
        location = LatLng(39.655650, -79.987834),
    ),
    Kome(
        title = "Kome",
        tags = listOf(Chinese, Japanese, Buffet, Sushi, IceCream, Dessert, Delivery),
        location = LatLng(39.656283, -79.990037),
    ),
    OliveGarden(
        title = "Olive Garden",
        tags = listOf(Italian, Salad, Wine, Beer, Dessert),
        location = LatLng(39.654971, -80.004684),
    ),
    LongHornSteakhouse(
        title = "Long Horn Steakhouse",
        tags = listOf(American, Steak, Burger, Beer, Wings, Wine),
        location = LatLng(39.654436, -80.004783),
    ),
    Cheddars(
        title = "Cheddars",
        tags = listOf(American, Salad, Burger, Salad, Dessert, Beer, Wine),
        location = LatLng(39.654971, -80.004684),
    ),
    Chilis(
        title = "Chilis",
        tags = listOf(American, FastFood, Burger, Salad, Dessert),
        location = LatLng(39.654593, -80.003789),
    ),
    RedLobster(
        title = "Red Lobster",
        tags = listOf(Seafood, FastFood, Burger, Salad, Dessert, Beer, Wine),
        location = LatLng(39.654971, -80.004684),
    ),
    Fusion(
        title = "Fusion",
        tags = listOf(Hibachi, Japanese, Chinese, Sushi, Wine, Beer, Dessert),
        location = LatLng(39.641016, -79.995777),
    ),
    PandaExpress(
        title = "Panda Express",
        tags = listOf(Chinese, FastFood, Delivery),
        location = LatLng(39.640877, -79.998537),
    ),
    JerseyMikes(
        title = "Jersey Mike's",
        tags = listOf(American, FastFood, Sandwich, Delivery),
        location = LatLng(39.640094, -79.999332),
    ),
    BlazePizza(
        title = "Blaze Pizza",
        tags = listOf(Pizza, FastFood),
        location = LatLng(39.640094, -79.999332),
    ),
    TexasRoadhouse(
        title = "Texas Roadhouse",
        tags = listOf(American, Steak, Salad, Burger, Beer, Wine, Dessert),
        location = LatLng(39.638782, -80.004072),
    ),
    Arbys(
        title = "Arbys",
        tags = listOf(American, FastFood, Burger, Delivery),
        location = LatLng(39.619244, -79.923097),
    ),
    Arbys2(
        title = "Arbys",
        tags = listOf(American, FastFood, Burger, Delivery),
        location = LatLng(39.650556, -79.972103),
    ),
    Arbys3(
        title = "Arbys",
        tags = listOf(American, FastFood, Burger, Delivery),
        location = LatLng(39.630115, -79.986960),
    ),
    PopShop(
        title = "Pop Shop",
        tags = listOf(Milkshakes, IceCream, Dessert, Delivery),
        location = LatLng(39.631483, -79.983999),
    ),
    Oliverios(
        title = "Oliverio's",
        tags = listOf(Italian, Salad, Pizza, Wine, Dessert, Beer, Delivery),
        location = LatLng(39.627314, -79.961205),
    ),
    CasaDAmici(
        title = "Casa D'Amici",
        tags = listOf(Italian, Salad, Pizza, Dessert, Delivery),
        location = LatLng(39.631182, -79.954802),
    ),
    CasaDAmici2(
        title = "Casa D'Amici",
        tags = listOf(Italian, Salad, Pizza, Dessert, Delivery),
        location = LatLng(39.648422, -79.891844),
    )
}

