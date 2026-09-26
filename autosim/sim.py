"""Timeline simulator for one alliance's 30 second AUTO.

It does not simulate physics. Each robot runs a plan, a list of high level
actions, and each action costs time (plus randomness) and may score points:

    ("collect", "F1")  drive to FLOWER F1 and grab POLLEN until full
    ("launch",)        drive to the nearest launch spot and launch everything
    ("park",)          drive to the park spot and stay there

Opponents are not simulated yet.
"""

import math
import random
from dataclasses import dataclass, field

from . import config as C


def drive_seconds(a, b):
    """Time for a trapezoidal velocity profile from point a to point b."""
    d = math.dist(a, b)
    if d == 0:
        return 0.0
    ramp = C.MAX_SPEED ** 2 / C.MAX_ACCEL  # distance to speed up + slow down
    if d < ramp:
        return 2 * math.sqrt(d / C.MAX_ACCEL)
    return d / C.MAX_SPEED + C.MAX_SPEED / C.MAX_ACCEL


def nearest(pos, spots):
    return min(spots, key=lambda s: math.dist(pos, s))


@dataclass
class Robot:
    name: str
    alliance: str
    pos: tuple
    plan: list
    carrying: int = C.PRELOAD
    time: float = 0.0
    step: int = 0
    left_start: bool = False
    parked: bool = False


@dataclass
class Result:
    score: int = 0
    tips: int = 0
    log: list = field(default_factory=list)


class AllianceSim:
    def __init__(self, robots, seed=None, noise=True):
        self.robots = robots
        self.rng = random.Random(seed)
        self.noise = noise
        self.flower_pollen = {f: C.POLLEN_PER_FLOWER for f in C.FLOWERS}
        self.cell_count = 0  # elements in the HIVE's upward CELL
        self.result = Result()

    # --- randomness helpers --------------------------------------------------

    def _drive(self, a, b):
        t = drive_seconds(a, b)
        if self.noise:
            t *= max(0.5, self.rng.gauss(1.0, C.DRIVE_NOISE))
        return t

    def _chance(self, p):
        return self.rng.random() < p if self.noise else p >= 0.5

    def _log(self, robot, t, msg):
        self.result.log.append((round(t, 2), robot.name, msg))

    # --- actions ---------------------------------------------------------------

    def _move(self, robot, target):
        """Drive to target. Returns False if AUTO ends before arriving."""
        arrive = robot.time + self._drive(robot.pos, target)
        if arrive > C.AUTO_SECONDS:
            robot.time = C.AUTO_SECONDS
            return False
        if target != robot.pos:
            robot.left_start = True
        robot.pos, robot.time = target, arrive
        return True

    def _collect(self, robot, flower):
        robot.parked = False
        if not self._move(robot, C.FLOWERS[flower]):
            return
        while robot.carrying < C.CARRY_LIMIT and self.flower_pollen[flower] > 0:
            if robot.time + C.GRAB_SECONDS > C.AUTO_SECONDS:
                robot.time = C.AUTO_SECONDS
                return
            robot.time += C.GRAB_SECONDS
            if not self._chance(C.GRAB_MISS_CHANCE):
                robot.carrying += 1
                self.flower_pollen[flower] -= 1
        self._log(robot, robot.time, f"collected at {flower}, holding {robot.carrying}")

    def _launch(self, robot):
        robot.parked = False
        if not self._move(robot, nearest(robot.pos, C.LAUNCH_SPOTS[robot.alliance])):
            return
        while robot.carrying > 0:
            if robot.time + C.LAUNCH_SECONDS > C.AUTO_SECONDS:
                robot.time = C.AUTO_SECONDS
                return
            robot.time += C.LAUNCH_SECONDS
            robot.carrying -= 1
            if self._chance(C.LAUNCH_HIT_CHANCE):
                self.cell_count += 1
                if self.cell_count >= C.TIP_THRESHOLD:
                    self.cell_count = 0
                    self.result.tips += 1
                    self.result.score += C.POINTS_HIVE_TIP
                    self._log(robot, robot.time, "HIVE TIPPED")

    def _park(self, robot):
        if self._move(robot, nearest(robot.pos, C.PARK_SPOTS[robot.alliance])):
            robot.parked = True
            self._log(robot, robot.time, "parked")

    # --- main loop -------------------------------------------------------------

    def run(self):
        # Always advance the robot that is furthest behind in time, so the two
        # robots share the HIVE and FLOWERS in the right order.
        while True:
            active = [r for r in self.robots
                      if r.step < len(r.plan) and r.time < C.AUTO_SECONDS]
            if not active:
                break
            robot = min(active, key=lambda r: r.time)
            action = robot.plan[robot.step]
            robot.step += 1
            if action[0] == "collect":
                self._collect(robot, action[1])
            elif action[0] == "launch":
                self._launch(robot)
            elif action[0] == "park":
                self._park(robot)
            else:
                raise ValueError(f"unknown action {action}")

        for r in self.robots:
            if r.left_start:
                self.result.score += C.POINTS_LEAVE
            if r.parked:
                self.result.score += C.POINTS_PARK
        return self.result


def run_match(our_start, our_plan, partner_start, partner_plan, seed=None, noise=True):
    alliance = our_start.split("_")[0]
    robots = [
        Robot("us", alliance, C.START_POSITIONS[our_start], list(our_plan)),
        Robot("partner", alliance, C.START_POSITIONS[partner_start], list(partner_plan)),
    ]
    return AllianceSim(robots, seed=seed, noise=noise).run()
