package co.za.rain.myapplication

enum class ConnectionType(val id: Int, val connectionName: String) {
    Unknown(0, "Unknown"),
    Wifi(1, "Wifi"),
    Mobile(2, "Mobile")
}