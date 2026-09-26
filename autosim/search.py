"""Brute force: try every sensible AUTO plan and rank them by average score.

This is the baseline a neural network has to beat. If the list of possible
plans stays small, this search already finds the best one.

Usage:
    python3 -m autosim.search --start red_audience --partner idle
"""

import argparse
import statistics

from . import config as C
from .partners import PARTNERS
from .sim import run_match

MAX_ACTIONS = 7


def all_plans():
    """Every plan of launches, FLOWER visits and an optional final park."""
    plans = []

    def extend(plan, holding):
        if plan:
            plans.append(list(plan))
            plans.append(plan + [("park",)])
        if len(plan) >= MAX_ACTIONS:
            return
        if holding > 0:
            extend(plan + [("launch",)], 0)
        if holding < C.CARRY_LIMIT:
            for f in C.FLOWERS:
                # Visiting the same FLOWER twice in a row never helps.
                if plan and plan[-1] == ("collect", f):
                    continue
                extend(plan + [("collect", f)], C.CARRY_LIMIT)

    extend([], C.PRELOAD)
    plans.append([("park",)])
    return plans


def evaluate(plan, start, partner_start, partner_plan, runs):
    scores = [run_match(start, plan, partner_start, partner_plan, seed=i).score
              for i in range(runs)]
    return statistics.mean(scores), min(scores)


def main():
    p = argparse.ArgumentParser(description=__doc__,
                                formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--start", default="red_audience", choices=C.START_POSITIONS)
    p.add_argument("--partner", default="idle", choices=PARTNERS)
    p.add_argument("--runs", type=int, default=200, help="random matches per plan")
    p.add_argument("--top", type=int, default=5)
    args = p.parse_args()

    alliance, side = args.start.split("_")
    partner_start = f"{alliance}_{'far' if side == 'audience' else 'audience'}"
    partner_plan = PARTNERS[args.partner]

    plans = all_plans()
    # Quick filter with no randomness, then score the best ones properly.
    nominal = sorted(
        plans,
        key=lambda pl: run_match(args.start, pl, partner_start, partner_plan,
                                 noise=False).score,
        reverse=True,
    )[:50]
    ranked = sorted(
        ((evaluate(pl, args.start, partner_start, partner_plan, args.runs), pl)
         for pl in nominal),
        key=lambda x: x[0], reverse=True,
    )

    print(f"{len(plans)} plans checked | start {args.start} | partner {args.partner}")
    for (mean, worst), plan in ranked[:args.top]:
        steps = " -> ".join(a[0] if len(a) == 1 else f"{a[0]} {a[1]}" for a in plan)
        print(f"  avg {mean:5.1f}  worst {worst:3d}   {steps}")


if __name__ == "__main__":
    main()
