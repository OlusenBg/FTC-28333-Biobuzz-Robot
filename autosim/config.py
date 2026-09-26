"""All numbers the auto simulator depends on, in one place.

Every value tagged UNVERIFIED is a placeholder. Check it against the
BIOBUZZ Competition Manual (Section 10 Game Details) or measure it on the
real robot, then remove the tag.

Units: inches, seconds. Field origin is the field center (the HIVE),
+x points toward the blue ALLIANCE AREA, +y points away from the audience.
"""

# --- Match -----------------------------------------------------------------

AUTO_SECONDS = 30.0  # Manual Section 8: "first 30 seconds of the MATCH"

# --- Scoring (AUTO) ----------------------------------------------------------

POINTS_LEAVE = 3       # UNVERIFIED (web search summary of Section 10.5)
POINTS_PARK = 5        # UNVERIFIED (web search summary of Section 10.5)
POINTS_HIVE_TIP = 20   # UNVERIFIED (web search summary of Section 10.5)

# How many elements must land in the upward CELL to tip the HIVE.
TIP_THRESHOLD = 4      # UNVERIFIED (placeholder, not found yet)

# --- Robot rules -------------------------------------------------------------

PRELOAD = 3            # UNVERIFIED (elements the robot starts holding)
CARRY_LIMIT = 4        # UNVERIFIED (max elements a robot may control)

# --- Field layout (all UNVERIFIED placeholders, take from the field CAD) -----

START_POSITIONS = {
    "red_audience": (-60.0, -36.0),
    "red_far": (-60.0, 36.0),
    "blue_audience": (60.0, -36.0),
    "blue_far": (60.0, 36.0),
}

# Where the robot stops to LAUNCH into its alliance's HIVE.
LAUNCH_SPOTS = {
    "red": [(-36.0, -24.0), (-36.0, 24.0)],
    "blue": [(36.0, -24.0), (36.0, 24.0)],
}

# The 4 FLOWERS on the perimeter wall, and how much POLLEN each holds at the
# start of the MATCH.
FLOWERS = {
    "F1": (-24.0, -70.0),
    "F2": (24.0, -70.0),
    "F3": (-24.0, 70.0),
    "F4": (24.0, 70.0),
}
POLLEN_PER_FLOWER = 3  # UNVERIFIED

# Where a robot must end AUTO to earn PARK.
PARK_SPOTS = {
    "red": [(-60.0, 0.0)],
    "blue": [(60.0, 0.0)],
}

# --- Robot performance (MEASURE THESE on the real robot) ---------------------

MAX_SPEED = 50.0       # in/s, top speed along a Pedro path   (MEASURE)
MAX_ACCEL = 80.0       # in/s^2                               (MEASURE)
DRIVE_NOISE = 0.10     # std dev of drive time, as a fraction (MEASURE)

GRAB_SECONDS = 0.8     # time to pull one POLLEN from a FLOWER (MEASURE)
GRAB_MISS_CHANCE = 0.15  # chance a grab fails and must retry (MEASURE)

LAUNCH_SECONDS = 0.5   # time between two launches            (MEASURE)
LAUNCH_HIT_CHANCE = 0.85  # chance a launched element lands in the CELL (MEASURE)
