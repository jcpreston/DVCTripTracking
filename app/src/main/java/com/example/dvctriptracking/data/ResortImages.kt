package com.example.dvctriptracking.data

object ResortImages {
    private val images = mapOf(
        "Animal Kingdom Villas - Jambo House" to "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?q=80&w=1000",
        "Animal Kingdom Villas - Kidani Village" to "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?q=80&w=1000",
        "Aulani, Disney Vacation Club Villas" to "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1000",
        "Bay Lake Tower at Disney's Contemporary Resort" to "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?q=80&w=1000",
        "Beach Club Villas" to "https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=1000",
        "BoardWalk Villas" to "https://images.unsplash.com/photo-1519974719765-e6559eac2575?q=80&w=1000",
        "Boulder Ridge Villas at Disney's Wilderness Lodge" to "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=1000",
        "Copper Creek Villas & Cabins at Disney's Wilderness Lodge" to "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=1000",
        "Grand Floridian Resort & Spa" to "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=1000",
        "Hilton Head Island Resort" to "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1000",
        "Old Key West Resort" to "https://images.unsplash.com/photo-1540206395-68808572332f?q=80&w=1000",
        "Polynesian Villas & Bungalows" to "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1000",
        "Riviera Resort" to "https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=1000",
        "Saratoga Springs Resort & Spa" to "https://images.unsplash.com/photo-1533130061792-64b345e4a833?q=80&w=1000",
        "Vero Beach Resort" to "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1000",
        "Villas at Disney's Grand Californian Hotel & Spa" to "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?q=80&w=1000",
        "The Villas at Disneyland Hotel" to "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?q=80&w=1000",
        "Island Tower at Disney's Polynesian Villas & Bungalows" to "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1000"
    )

    fun getImageUrl(resortName: String): String {
        return images[resortName] ?: "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?q=80&w=1000"
    }
}
