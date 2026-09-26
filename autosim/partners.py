"""Simulated alliance partners. At an event the partner is a random team, so
our plan should score well with every one of these."""

PARTNERS = {
    # Robot that doesn't move in AUTO.
    "idle": [],
    # Drives off the start line and parks.
    "park_only": [("park",)],
    # Launches its preload into the HIVE, then parks.
    "preload_launcher": [("launch",), ("park",)],
    # A strong partner: preload, one FLOWER cycle, then park.
    "one_cycle": [("launch",), ("collect", "F3"), ("launch",), ("park",)],
}
